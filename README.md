# spring-kafka
Projeto para estudo do Kafka utilizando Spring boot e docker-compose. Curso da Udemy.  
Estaremos trabalhando em dois projetos (microsserviços): **str-producer** e **str-consumer**.
  
## Docker Compose  
Necessário ter o docker desktop instalado e rodando na máquina  
Comando para subir: 'docker-compose up' ou 'docker compose up' (sem o hífem "-", vai depender da versão instalada na máquina)  
Para parar basta usar um: Ctrl + c  
Para executar e deixar o terminal liberado: 'docker-compose up -d' ou 'docker compose up -d'  
Para parar usar o comando: 'docker-compose down' ou 'docker compose down'  
  
## Testando o Kafdrop  
Abre o navegador e digita: localhost:19000  
  
## Aplicação Spring  
1- Após abrir o projeto no InteliJ  
2- Navega até a classe: **/main/java/com/samuel/strproducer/StrProducerApplication.java**  
3- Clica com o botão direto sob StrProducerApplication  
4- Escolhe a opção: **Run StrProducerApplication**  
5- A partir de agora poderá executar apenas clicando no botão Run (setinha verde) do Intelij  
  
## Enviando mensagens via endpoint
Após configuração para o envio de mensagem, faça o teste de envio de mensagem:  
- Utilize o endpoint **POST - localhost:8000/producer**  
- No corpo da requisição envie a mensagem, ex.: **{"message": "Test send message 1"}**  
- Abra o Kafdrop  
- Clique no nosso tópico: **str-topic**  
- Clique em **View Messages**  
- Escolha uma das partições (criamos 2 partições)  
- Clique no botão **View Messages** ao lado ou logo abaixo  
- Obseve que conseguiremos visualizar nossa mensagem que foi enviada no endpoint.  
