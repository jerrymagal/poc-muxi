package br.com.rolim.muxi.validation;

import lombok.Getter;
import br.com.rolim.muxi.entity.BaseConverter;
import br.com.rolim.muxi.entity.Terminal;

@Getter
public enum SchemaLocations {
	
	TERMINAL("classpath:schema/terminal-schema.json", new Terminal());
	
	private String schema;
	private BaseConverter<?> converter;
	
	private SchemaLocations(String schema, BaseConverter<?> converter) {
		this.schema = schema;
		this.converter = converter;
	}	
}
