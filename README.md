## Microservice de envio de email

Este projeto foi criado com intuito de aplicar e aperfeiçoar meu conhecimento no ecossistema do Spring Framework, bem como estudar ainda mais sobre, e adentrar-me no mundo da arquitetura de microservices. Ademais utilizar esta API Rest para qualquer projeto que necessite de envio de email.

## 🚀 Começando

### 📋 Pré-requisitos

* [JDK 17.0.4 LTS](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) - Kit de
  Desenvolvimento Java
* [Maven](https://maven.apache.org/) - Gerenciador de Dependências
* [Postgres](https://www.postgresql.org/) - SGDB utilizado
* [Intellij IDEA (Opcional)](https://www.jetbrains.com/pt-br/idea/) - Ambiente de Desenvolvimento Integrado (IDE)

### ⚙️ Executando a aplicação

Como mencionado acima, o postgres é o SGDB que está sendo utilizado. Desta forma no arquivo de configuração do spring `application.yml`, altere as variaveis `url`, `username` e `password` de acordo com preferencia.

````yaml
spring:
  datasource:
    url: ${DATABASE_URL}
    username: ${DATABASE_USER}
    password: ${DATABASE_PASSWORD}
    driver-class-name: org.postgresql.Driver
````

### Configurando Java Mail Sender

Ainda no arquivo de configuração `application.yml`, mas agora nas configuração do Java Mail Sender, substitua as chaves `username` e `password` pelos valores do email que será o remetente as mensagens.

````yaml
spring:
  mail:
    host: smtp.gmail.com
    port: 587
    username: ${MAIL_USERNAME}
    password: ${MAIL_PASSWORD}
    properties.mail.smtp:
      auth: true
      starttls.enable: true
      ssl.trust: smtp.gmail.com
````

⚠️**Atenção**: a chave `password` não deve ser preenchida com a senha real do seu email ou o que será utilizado. Você deve gerar uma senha de app. [Veja este artigo](https://atendimento.movidesk.com/kb/article/280320/configurar-senha-app-google-movidesk) para gerar uma.

#### Depois de ter configurado o projeto de acordo com seu ambiente ou suas necessidades, execute um dos comandos abaixo de acordo com seu sistema operacional

Windows
```` shell
mvn spring-boot:run
````

Linux ou macOS
```` shell
./mvnw spring-boot:run
````

## 🔎 Consultando os endpoints com o Swagger

Acesse <https://ms-microservice.herokuapp.com/swagger-ui/index.html> para conferir os endpoints. 

### Enviando um email:

Através de um cliente HTTP envie um json neste formato:

``` json
{
  "ownerRef": "Luan Coêlho",
  "emailTo": "destinatario@dominio.com",
  "subject": "Testando Microservice",
  "text": "Aqui você coloca o texto do email",
  "fileUrl": "https://dev.java/assets/images/java-logo-vert-blk.png" ou null,
  "emailType": "DEFAULT"
}
```

no campo 
```` json
{
  "fileUrl": "link-do-arquivo-que-será-enviado-por-anexo"
}
````

Ao preencher este campo na requisição, a API tentará fazer download do arquivo que está no link e anexará no email. Caso não deseje anexo, apenas deixe o campo como nulo. 








