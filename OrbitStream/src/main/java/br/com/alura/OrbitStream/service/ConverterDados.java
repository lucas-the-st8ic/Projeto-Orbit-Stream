package br.com.alura.OrbitStream.service;

import br.com.alura.OrbitStream.model.DadosSerie;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

public class ConverterDados implements IConverteDados {
    private ObjectMapper mapper = new ObjectMapper();


    @Override
    public <T> T obterDados(String json, Class<T> classe) {


        return mapper.readValue(json, classe);
    }
}


