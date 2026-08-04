package br.com.lucas.lukzseries.service;

public interface IConverteDados {
    <T> T obterDados(String json, Class<T> classe);

}


