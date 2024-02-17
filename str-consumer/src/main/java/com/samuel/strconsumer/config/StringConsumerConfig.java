package com.samuel.strconsumer.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.RecordInterceptor;

import java.util.HashMap;

/**
 * Como é uma classe de configuração, então vamos usar a annotation Configuration.
 * Aqui vamos criar os nossos beans com as configurações para desserializar as nossas
 * mensagens (nossos registros).
 * */

@Log4j2
@RequiredArgsConstructor
@Configuration
public class StringConsumerConfig {

    private final KafkaProperties properties;

    /**
     * Primeiro vamos definir o nosso Condumer Factory para consumir os registros do nosso producer.
     * */
    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        var configs = new HashMap<String, Object>();
        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers());

        // Definindo a classe de desserialização da key e do value: StringDeserializer.class, que é do proprio Kafka
        configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(configs);
    }

    /**
     * Agora vamos definir uma container factory, que é o nosso Kafka Listener que é quem vai ficar
     * ouvindo o nosso tópico (str-topico).
     * */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> strContainerFactory (
            ConsumerFactory<String, String> consumerFactory
    ) {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, String>();

        // Vamos deixar como reponsabilidade do Spring para injetar um consumerFactory (que recebemos como parâmetro)
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

    /**
     * Agora vamos definir outro container factory, nesse caso ele ficará validando as mensagens recebidas.
     * Ele é um interceptor.
     * Antes da mensagem ser consumida pelo listener, poderemos fazer alguma coisa.
     * */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> validMessageContainerFactory(
            ConsumerFactory<String, String> consumerFactory
    ) {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, String>();
        factory.setConsumerFactory(consumerFactory);
        // Aqui teremos a linha do interceptor, que será a nova funcionalidade
        factory.setRecordInterceptor(validMessage()); // o setRecordInterceptor é o método que vai interceptar
        return factory;
    }


    /**
     * Antes da mensagem ser consumida pelo listener vamos validar se ela possui a palavra Teste.
     * Poderíamos implementar qualquer outra regra de negócio aqui
     * */
    private RecordInterceptor<String, String> validMessage() {
        return (record, value) -> { // São os valores das duas String do RecordInterceptor. Vamos usar só o record
            if(record.value().contains("Teste")) {
                log.info("Possui a palavra Teste");
                return record;
            }
            return record;
        };
    }
}
