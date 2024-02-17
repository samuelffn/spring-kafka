package com.samuel.strconsumer.listeners;

import com.samuel.strconsumer.custom.StrConsumerCustomListener;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

/**
 * Esta classe conterá o método listener, que ficará escutando as mensagens.
 * */

@Log4j2
@Component
public class StrConsumerListener {

    /**
     * *Obs.1: Antes de enviar a primeira mensagem. Ao olhar os logs, podemos notar o seguinte:
     * Como nós temos apenas 1 grupo de consumidor, note que ele se registrou nas partições 0 e 1 (Teremos este log:
     * group-1: partitions assigned: [str-topic-0, str-topic-1]), ou seja, ele está consumindo mensagens das duas partições.
     *
     * * Enviando as mensagens:
     * - Agora, abra o Insomnia/Postman e faça o envio de uma mensagem (POST - localhost:8000/producer)
     * - Olhe os logs nos dois microsserviços, Producer e Consumer
     * - Observe que um mostra que a mensagem foi enviada enquanto que o outro mostra que foi recebida
     * - Agora abra o kafdrop (Abre o navegador e digita: localhost:19000) e observe nossas mensagens nele.
     * - O Kafka quem decide em qual partição a mensagem vai cair.
     * */

    /**
     * *Obs.2: Sobre conseito de grupos de consumo.
     * - Quando criamos o nosso tópico (str-topico) no Producer, nós definimos que ele teria 2 partições (0 e 1). Quando criamos um tópico,
     * por padrão ele de ter no mínimo 1 partição. Se não definirmos a quantidade de partições, o Kafka criará 1 partição.
     * - Aqui nessa classe, quando tínhamos apenas o método listener, ele tinha se registrado nos dois grupos (lembre do log:
     * group-1: partitions assigned: [str-topic-0, str-topic-1]), ou seja, ele está consumindo mensagens das duas partições)
     * - Para conseguirmos explicar, vamos renomear ele para create
     * - Quando tivermos o método create e o log, cada um deles pegará uma partição, ou seja, o log ficará assim:
     * group-1: partitions assigned: [str-topic-0] e group-1: partitions assigned: [str-topic-1]
     * - Quando tivermos o método history, observe que os outros dois pegarão uma partição cada e o history ficará sem partição,
     * observe o log: group-1: partitions assigned: []
     * - Cuidado ao criar mais Listeners do que a quantidade de grupos existentes.
     * - Agora mudo mude o grupo do método log para group-2
     * - Olhe os logs:
     *  - Obseve que nenhum método ficará sem partição
     *  - As mensagens chagarão nos dois grupos
     * - Agora mudo mude o grupo do método history para group-3
     *  * - Olhe os logs:
     *  *  - Obseve que nenhum método ficará sem partição
     *  *  - As mensagens chegarão nos três grupos
     * */

    /**
     * Dentro do @KafkaListener, precisamos definir algumas coisas:
     * GroupID: definir um grupo, pode ser group-1.
     * Topics: definir em qual tópico vamos estar fazendo a letura dos nossos registros que é o str-topic.
     * containerFactory = definir onde faremos a leitura das nossas mensagens, que será o "strContainerFactory",
     * método que foi criado em StringConsumerConfig.
     * */
    /*
    @KafkaListener(groupId = "group-1", topics = "str-topic", containerFactory = "strContainerFactory")
    public void create(String message) {
        log.info("CREATE ::: Receive message {}", message); // Vamos logar informando que recebemos a mensagem
    }

    @KafkaListener(groupId = "group-2", topics = "str-topic", containerFactory = "strContainerFactory")
    public void log(String message) {
        log.info("LOG ::: Receive message {}", message); // Vamos logar informando que recebemos a mensagem
    }
    */

    /**
     * Obs.3: Escilhendo uma partição para receber as mensagens
     * - Se não for definida a partição que a mensagem chegará, o kafka se encarrega de escolher a partição
     * - Podemos usar a propriedade topicPartitions para definir em qual partição as mensagens chegarão para o meu listener
     * - Não podemos definir mais partições do que a quantidade que temos
     * - Para conseguir testar, vamos usar o mesmo grupo
     * - Observe nos logs que ele vai mostrar que os tópicos ficaram registrados nas suas partições.
     * Ex.:
     * Resetting oofset for partition str-topic-0
     * Resetting oofset for partition str-topic-1
     * - Abra o Insomnia e faça um teste
     * - Abra o Kafdrop e veja em qual partição a mensagem chegou.
     *  - Se chegar na 0, então deveremos ver os logs do create (que foi onde definimos que seria a partição 0)
     * */

    /*
    @KafkaListener(
            groupId = "group-1",
            topicPartitions = {
                    @TopicPartition(topic = "str-topic", partitions = {"0"}) // Defindo em qual partição vai ficar. POderia receber um array aqui
            },
            containerFactory = "strContainerFactory")
    public void create(String message) {
        log.info("CREATE ::: Receive message {}", message); // Vamos logar informando que recebemos a mensagem
    }

    @KafkaListener(
            groupId = "group-1",
            topicPartitions = {
                    @TopicPartition(topic = "str-topic", partitions = {"1"}) // Defindo em qual partição vai ficar. POderia receber um array aqui
            },
            containerFactory = "strContainerFactory")
    public void log(String message) {
        log.info("LOG ::: Receive message {}", message); // Vamos logar informando que recebemos a mensagem
    }

    @KafkaListener(groupId = "group-2", topics = "str-topic", containerFactory = "strContainerFactory")
    public void history(String message) {
        log.info("HISTORY ::: Receive message {}", message); // Vamos logar informando que recebemos a mensagem
    }
    */

    /**
     * Obs.4: Criando um listener customizado para evitar repetição de códigos.
     * Em nossa anotação customizada definimos o nosso tópico str-topic e o containerFactory, então só precisamos
     * passar o groupId.
     * */
    @StrConsumerCustomListener(groupId = "group-1")
    public void create(String message) {
        log.info("CREATE ::: Receive message {}", message);
    }

    @StrConsumerCustomListener(groupId = "group-1")
    public void log(String message) {
        log.info("LOG ::: Receive message {}", message);
    }

    /*
    @StrConsumerCustomListener(groupId = "group-2")
    public void history(String message) {
        log.info("HISTORY ::: Receive message {}", message);
    }
    */

    /**
     * Vamos usar esse litener para exemplificar o uso de interceptor nas mensagens recebidas
     * - Primeiro vamos voltar a anotaao do @KafkaListener para modificarmos o containerFactory
     * - Vamos informar o novo container factory
     * - Observe os logs após enviar uma mensagem:
     *  - Use Teste (com o E) na mensagem (mostrará que foi interceptada)
     *  - Use Test (sem o E) na mensagem (não mostrará que foi interceptada)
     * */
    @KafkaListener(groupId = "group-2", topics = "str-topic", containerFactory = "validMessageContainerFactory")
    public void history(String message) {
        log.info("HISTORY ::: Receive message {}", message);
    }

}
