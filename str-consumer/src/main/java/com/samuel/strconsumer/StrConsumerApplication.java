package com.samuel.strconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

/**
 * Nesta classe, que é a classe principal do projeto, vamos habilitar o kafka. Para isso utilizamos a
 * annotation @EnableKafka.
 * */

@EnableKafka
@SpringBootApplication
public class StrConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(StrConsumerApplication.class, args);
	}

}
