package br.com.rolim.muxi.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.rolim.muxi.handler.JsonSchemaValidatingArgumentResolver;

@Configuration
public class JsonValidationConfiguration implements WebMvcConfigurer {
	
	@Autowired
    private ObjectMapper objectMapper;
 
    @Autowired
    private ResourcePatternResolver resourcePatternResolver;
    
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
    	resolvers.add(new JsonSchemaValidatingArgumentResolver(objectMapper, resourcePatternResolver));
    }
}
