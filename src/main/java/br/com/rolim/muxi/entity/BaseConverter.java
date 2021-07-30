package br.com.rolim.muxi.entity;

public interface BaseConverter<T> {
	
	T convert(String[] fields);
}
