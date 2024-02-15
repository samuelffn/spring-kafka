package com.samuel.strproducer.services;

import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * Esta classe é um serviço que ficará responsável por enviar nossas mensagens.
 * Vamos receber a mensagem via endpoint, o qual chamará nosso StringProducerService
 * para enviar a mensagem recebida no endpoint.
 * */
@Log4j2
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
        log.info("Send message {}", message); // Vamos logar informando que enviamos a mensagem
        kafkaTemplate.send("str-topic", message);

        // Usando callback e utilizando logs com informações de Cluster ID, partition, Offset...
        /*CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send("str-topic", message);
        future.whenComplete((result, ex) -> {
            if (ex != null) {
                log.error("Error sending message: {}", ex.getMessage());
                return;
            }
            log.info("Message sent successfully: {}", result.getProducerRecord().value());
            log.info(
                    "Partition {}, Offset {}",
                    result.getRecordMetadata().partition(),
                    result.getRecordMetadata().offset()
            );
        });*/
    }

}
