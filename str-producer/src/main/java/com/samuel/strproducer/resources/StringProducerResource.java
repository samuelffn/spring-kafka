package com.samuel.strproducer.resources;

import com.samuel.strproducer.services.StringProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Nesta classe vamos criar o nosso endpoint, o qual receberá uma mensagem como parâmetro
 * e vamos enviar esta mensagem para o nosso Service e o nosso serviço à enviará para o
 * nosso Producer Factory.
 * */
@RequiredArgsConstructor
@RestController
@RequestMapping(value="/producer")
public class StringProducerResource {

    // Declarando o nosso service para podermos enviar a mensagem recebida no endpoint
    private final StringProducerService producerService;

    @PostMapping
    public ResponseEntity<?> sendMessage(@RequestBody String message) { // Usamos um ? porque não saabemos ao certo o que será retornado
        producerService.sendMessage(message);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
