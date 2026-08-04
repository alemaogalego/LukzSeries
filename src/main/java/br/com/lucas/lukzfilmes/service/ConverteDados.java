package br.com.lucas.lukzseries.service;

import br.com.lucas.lukzseries.model.DadosSeries;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConverteDados implements IConverteDados {
    private ObjectMapper mapper = new ObjectMapper();


    @Override
    public <T> T obterDados(String json, Class<T> classe) {
        try {
            return (T) mapper.readValue(json, classe);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
