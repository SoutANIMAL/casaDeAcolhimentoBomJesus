package com.fabriciuss.repositcasadeacolhimento.service;

import com.fabriciuss.repositcasadeacolhimento.service.DTO.EnderecoTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class ViaCepCtrlService {
    private final Logger log = LoggerFactory.getLogger(ViaCepCtrlService.class);

    public EnderecoTO findOne(String cep){
        //
        /**
         * Criando e instanciando um objeto do tipo RestTemplate, este objeto possui métodos que
         * irá permitir a comunicação com o webservice
         */
        RestTemplate restTemplate = new RestTemplate();


        /**
         * Criação de uma String com nome de uri, irá armazenar a uri (endereço) a ser consumido, observe que o cep é passado como parametro e o retorno é json.
         */
        String uri = "http://viacep.com.br/ws/{cep}/json/";

        /**
         * É possível passar mais de um parametro, entretanto só iremos utilizar o cep
         * observe que foi utilizado um Map para permiter obter o valor pela chave
         */
        Map<String, String> params = new HashMap<String, String>();
        params.put("cep", cep);

        /**
         * restTemplate.getForObject(uri, CepTO.class, params);
         * 1 (URI) - Endereço do webservice que será consumido
         * 2 (EnderecoTO.class) - Classe que representa os dados do endereço e que será mapeada no retorno da requisição com base no Json.
         * 3 (Params) - Parametros que serão utilizados na requisição, os mesmos serão includos na uri. Exemplo: {cep} será substituido pelo cep informado
         *
         * Após a requisição ser concluida, o retorno será armazenado no enderecoTO, com todos os dados já mapeados.
         */
        EnderecoTO enderecoTO = restTemplate.getForObject(uri, EnderecoTO.class, params);

        return enderecoTO;
    }
}
