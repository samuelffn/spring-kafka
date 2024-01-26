package com.samuel.strproducer.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;

/**
 * Nesta classe vamos criar o nosso kafka template para podemos enviar
 * a nossa mensagem para o tópico que criamos na classe KafkaAdminCondig, o str-topic.
 * */
@RequiredArgsConstructor
@Configuration
public class StringProducerFactoryConfig {

    private final KafkaProperties properties;

    /**
     * Vamos definir o nosso Produce Factory, que é a nossa fábrica de produção de mensagens.
     * Precisamos definir um Serializer, ou seja, a classe que vai serializar o objeto do nosso producer.
     * */
    @Bean //O @Bean para que esse método seja executado todas as vezes que a aplicação for executada
    public ProducerFactory<String, String> producerFactory() {
        var configs = new HashMap<String, Object>();
        // Configurando o bootstrap: pegando o valor que definimos nas propriedades (localhost:29092) pra poder fazer a comunicação com o Kafka.
        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers());
        // Aqui vamos definir qual objeto fará a serialização da chave, nesse caso foi o StringSerializer do próprio Kafka. O Kafka possui alguns serializadores e des-serializadores pré-construídos.
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        // Aqui vamos definir qual objeto fará a serialização do valor, e também vamos usar o StringSerializer do próprio Kafka.
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        // Vamos retornar um DefaultKafkaProducerFactory passando como parâmetro as configurações que definimos acima.
        return new DefaultKafkaProducerFactory<>(configs);
    }

    /**
     * Agora precisamos definir o Kafka template, pois ele irá utilizar desse método producerFactory() que criamos acima pra gente poder
     * enviar as mensagens pro nosso tópico str-topic, que criamos na classe KafkaAdminCondig.
     * Criamos o método abaixo, chamado kafkaTemplate(), que fará o envio das mensagens para o nosso tópico.
     * */
    @Bean
    public KafkaTemplate<String, String> kafkaTemplate(ProducerFactory<String, String> producerFactory) {
        // Aqui apenas retorno um Kafkatemple passando como parâmetro o nosso método acima producerFactory().
        // Como estamos chamando o método acima e ele é um @Bean, então podemos informar que quem ficará responsável por injetar um atributo
        // do tipo ProducerFactory pra mim, vai ser o Spring, logo, eu não preciso chamar o método, posso utilizar o parâmetro que vai vir.
        // O Spring ficará responsável por instanciar aqui um producerFactory com base no que implementamos no método acima e passando como
        // parãmetro para o nosso KafkaTemplate.
        return new KafkaTemplate<>(producerFactory);
    }

}
