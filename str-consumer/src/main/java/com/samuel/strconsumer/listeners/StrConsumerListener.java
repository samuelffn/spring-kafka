package com.samuel.strconsumer.listeners;

import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Esta classe conterá o método listener, que ficará escutando as mensagens.
 * */

@Log4j2
@Component
public class StrConsumerListener {

    /**
     * Dentro do @KafkaListener, precisamos definir algumas coisas:
     * GroupID: definir um grupo, pode ser group-1.
     * Topics: definir em qual tópico vamos estar fazendo a letura dos nossos registros que é o str-topic.
     * containerFactory = definir onde faremos a leitura das nossas mensagens, que será o "strContainerFactory",
     * método que foi criado em StringConsumerConfig.
     * */
    @KafkaListener(groupId = "group-1", topics = "str-topic", containerFactory = "strContainerFactory")
    public void listener(String message) {
        log.info("Receive message {}", message); // Vamos logar informando que recebemos a mensagem
    }
}
