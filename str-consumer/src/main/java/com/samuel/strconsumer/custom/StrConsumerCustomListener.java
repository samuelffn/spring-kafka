package com.samuel.strconsumer.custom;

import org.springframework.core.annotation.AliasFor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Esta interface é a definição de um listener customizado. Criamos como uma classe, mas mudamos para uma interface anotada.
 * Criamos esse listener para evitar repetições desncessárias nas classes.
 * Por exemplo, essa anotação personalizada será utilizada na classe StrConsumerListener.
 * */

@Retention(RetentionPolicy.RUNTIME) // Aqui estamos informando ao compilador do Java que essa anotação deverá esta disponível em tempo de execução
@Target(ElementType.METHOD) // Estamos definindo o ElementType como alvo um método. Basicamente isso significa que podemos definir nossas próprias anotações personalizadas
@KafkaListener // Para habilitar o Kafka Listener
public @interface StrConsumerCustomListener {

    /**
     * O AliasFor é usado para decorar atributos dentro de uma anotação.
     * */
    @AliasFor(
            annotation = KafkaListener.class, // Aqui definimos qual á a anotação que vamos usar, que no caso é o KafkaListener
            attribute = "topics" // Aqui informamos qual atributo vamos customizar, ou seja ficará fixo aqui
    )
    String[] topics() default "str-topic"; // Como topics é um array de aString, então definimos assim: String[] topics(). Em seguida informamos o valor que será o default.

    @AliasFor(annotation = KafkaListener.class, attribute = "containerFactory")
    String containerFactory() default "strContainerFactory";

    @AliasFor(annotation = KafkaListener.class, attribute = "groupId")
    String groupId() default ""; // Como precisaremos informar o groupId então deixamos como vazio

}
