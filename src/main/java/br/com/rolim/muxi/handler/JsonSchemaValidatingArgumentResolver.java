package br.com.rolim.muxi.handler;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;

import br.com.rolim.muxi.entity.BaseConverter;
import br.com.rolim.muxi.exception.JsonSchemaValidationException;
import br.com.rolim.muxi.exception.JsonValidationFailedException;
import br.com.rolim.muxi.validation.SchemaLocations;
import br.com.rolim.muxi.validation.ValidJson;

public class JsonSchemaValidatingArgumentResolver implements HandlerMethodArgumentResolver {
	
	private final ObjectMapper objectMapper;
    private final ResourcePatternResolver resourcePatternResolver;
    private final Map<String, JsonSchema> schemaCache;
    
    public JsonSchemaValidatingArgumentResolver(ObjectMapper objectMapper, ResourcePatternResolver resourcePatternResolver) {
        this.objectMapper = objectMapper;
        this.resourcePatternResolver = resourcePatternResolver;
        this.schemaCache = new ConcurrentHashMap<>();
        
        this.objectMapper.setSerializationInclusion(Include.NON_NULL);
    }

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterAnnotation(ValidJson.class) != null;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
	
	    SchemaLocations schemaPath = parameter.getParameterAnnotation(ValidJson.class).value();
	 
	    JsonSchema schema = getJsonSchema(schemaPath.getSchema());
	 
	    JsonNode json = objectMapper.readTree(getJsonPayload(webRequest, schemaPath.getConverter()));
	 
	    Set<ValidationMessage> validationResult = schema.validate(json);
	 
	    if (validationResult.isEmpty()) {
	        return objectMapper.treeToValue(json, parameter.getParameterType());
	    }
	 
	    throw new JsonValidationFailedException(validationResult);
	}
	
	private String getJsonPayload(NativeWebRequest nativeWebRequest, BaseConverter<?> converter) throws Exception {
	    HttpServletRequest httpServletRequest = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
	    String body = StreamUtils.copyToString(httpServletRequest.getInputStream(), StandardCharsets.UTF_8);
	    return buildJson(body, converter);
	}
	
	private String buildJson(String body, BaseConverter<?> converter) throws Exception {
		Object object = converter.convert(body.split(";"));
		return new String(objectMapper.writeValueAsBytes(object));
	}
	
	private JsonSchema getJsonSchema(String schemaPath) {
	    return schemaCache.computeIfAbsent(schemaPath, path -> {
	        Resource resource = resourcePatternResolver.getResource(path);
	        if (!resource.exists()) {
	            throw new JsonSchemaValidationException("Schema file does not exist, path: " + path);
	        }
	        JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V201909);
	        try (InputStream schemaStream = resource.getInputStream()) {
	            return schemaFactory.getSchema(schemaStream);
	        } catch (Exception e) {
	            throw new JsonSchemaValidationException("An error occurred while loading JSON Schema, path: " + path, e);
	        }
	    });
	}

}
