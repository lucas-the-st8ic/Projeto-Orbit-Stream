package br.com.alura.OrbitStream.service;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ConverterDados implements IConverteDados {
    private ObjectMapper mapper = new ObjectMapper();


    @Override
    public <T> T obterDados(String json, Class<T> classe) {

        return mapper.readValue(json, classe);
    }
}


