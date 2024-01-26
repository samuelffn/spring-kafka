package com.samuel.strproducer.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;

@RequiredArgsConstructor
@Configuration
public class KafkaAdminConfig {

    public final KafkaProperties properties;

    /**
     * Com este método aqui teremos um kafka admin configurado para fazer a cominicação
     * com o kafka cluster (o que está rodando via docker: localhost:19000).
     * */
    @Bean //O @Bean para que esse método seja executado todas as vezes que a aplicação for executada
    public KafkaAdmin kafkaAdmin() {
        var configs = new HashMap<String, Object>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers());
        return new KafkaAdmin(configs);
    }


    /**
     * Com este método aqui todas as vezes que o projeto foi iniciado ele irá criar o tópico, caso
     * ele ainda nao exista.
     * */
    @Bean
    public KafkaAdmin.NewTopics topics() { // Com esse NewTopics eu posso gerar vários tópicos (logo abaixo)
        return new KafkaAdmin.NewTopics(
                // Aqui é onde criamos os nossos tópicos. Primeiro tópico criado será o str-topic.
                // Se não informamos as partições ele criar só 1. Criamos 2 só para exemplo.
                // Nesse nosso primeiro tópico criamos 2 partições com apenas 1 réplica.
                TopicBuilder.name("str-topic").partitions(2).replicas(1).build()
        );
    }
}
