package com.samuel.strproducer.services;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Esta classe é um serviço que ficará responsável por enviar nossas mensagens.
 * Vamos receber a mensagem via endpoint, o qual chamará nosso StringProducerService
 * para enviar a mensagem recebida no endpoint.
 * */
@RequiredArgsConstructor
@Service
public class StringProducerService {

    // Declaramos o nosso kafkaTemplate. Já temos um cara desse criado em StringProducerFactoryConfig
    // que vamos usar para retornar um producerFactory com suas propriedade que definimos lá.
    private final KafkaTemplate<String, String> kafkaTemplate;

    /**
     * Criamos um método que fará o envio da mensagem recebida pro Kafka.
     * */
    public void sendMessage(String message) {
        // No método send precisamos informar:
        // * O tópico (que será aquele tópico que criamos, o str-topic)
        // * A mensagem, que nesse caso recebemos como parâmetro
        kafkaTemplate.send("str-topic", message);
    }

}
