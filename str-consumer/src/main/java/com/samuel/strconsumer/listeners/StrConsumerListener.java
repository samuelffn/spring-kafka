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

/**
 * *Obs.: Antes de enviar a primeira mensagem. Ao olhar os logs, podemos notar o seguinte:
 * Como nós temos apenas 1 grupo de consumidor, note que ele se registrou nas partições 0 e 1 (Teremos este log:
 * group-1: partitions assigned: [str-topic-0, str-topic-1]), ou seja, ele está consumindo mensagens das duas partições.
 *
 * * Enviando as mensagens:
 * - Agora, abra o Insomnia/Postman e faça o envio de uma mensagem (POST - localhost:8000/producer)
 * - Olhe os logs nos dois microsserviços, Producer e Consumer
 * - Observe que um mostra que a mensagem foi enviada enquanto que o outro mostra que foi recebida
 * - Agora abra o kafdrop (Abre o navegador e digita: localhost:19000) e observe nossas mensagens nele.
 * */
