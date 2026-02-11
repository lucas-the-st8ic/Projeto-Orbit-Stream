package br.com.alura.OrbitStream.service;

public interface IConverteDados {

    <T> T obterDados (String json, Class<T> classe);
}
