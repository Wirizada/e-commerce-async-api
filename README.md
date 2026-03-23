````markdown
# 🛒 E-Commerce Async API

![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.0.3-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![RabbitMQ](https://img.shields.io/badge/RabbitMQ-FF6600?style=for-the-badge&logo=rabbitmq&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)

> **Status do Projeto:** Concluído ✔️ | Pipeline CI/CD: 🟢 Passing

## 📖 Sobre o Projeto

Esta é uma API RESTful desenvolvida para simular o ecossistema de checkout de um E-commerce. O foco principal do projeto é a **alta disponibilidade e o processamento assíncrono**. 

Em vez de processar o pedido no momento da requisição (o que poderia causar lentidão ou queda do sistema em dias de Black Friday), a API recebe o pedido, salva rapidamente e o envia para uma fila de mensagens (**RabbitMQ**). Um *Worker* consome essa fila no seu próprio ritmo, validando regras de negócio e atualizando o status do pedido no banco de dados (**PostgreSQL**).

## 🚀 Padrões de Produção Aplicados

Este projeto não é apenas funcional, ele implementa práticas exigidas pelo mercado corporativo:

* **Arquitetura Assíncrona (Event-Driven):** Desacoplamento entre o recebimento do pedido e o processamento usando RabbitMQ.
* **Database Migrations (Flyway):** Controle de versão do esquema do banco de dados, garantindo que o banco de produção seja recriável e seguro.
* **Tratamento Global de Erros (`@ControllerAdvice`):** Interceptação de exceções (como JSON mal formatado ou produto não encontrado) para retornar respostas padronizadas e amigáveis ao cliente.
* **Documentação Viva (Swagger/OpenAPI):** Interface interativa para exploração e teste dos endpoints.
* **Integração Contínua (CI):** Pipeline configurado com GitHub Actions para rodar testes unitários automaticamente a cada *push*.
* **Testes Unitários:** Validação de regras de negócio isoladas utilizando JUnit 5 e Mockito.

## 🛠️ Tecnologias Utilizadas

* **Java 21**
* **Spring Boot 4** (Web, Data JPA, AMQP, Validation)
* **RabbitMQ** (Message Broker)
* **PostgreSQL** (Banco de Dados Relacional)
* **Docker & Docker Compose** (Orquestração de Infraestrutura)
* **Flyway** (Migrações de Banco de Dados)
* **Springdoc OpenAPI / Swagger** (Documentação)
* **JUnit 5 & Mockito** (Testes)
* **GitHub Actions** (CI/CD)

## ⚙️ Como Executar na Sua Máquina

### Pré-requisitos
* Java 21 instalado.
* Docker e Docker Compose instalados.
* Maven instalado.

### Passos

1. **Clone o repositório:**
   ```bash
   git clone [https://github.com/SEU_USUARIO/ecommerce-async-api.git](https://github.com/SEU_USUARIO/ecommerce-async-api.git)
   cd ecommerce-async-api
````

2.  **Suba a infraestrutura (Banco de Dados e Mensageria):**

    ```bash
    docker-compose up -d
    ```

3.  **Inicie a aplicação Spring Boot:**

    ```bash
    mvn spring-boot:run
    ```

    *Nota: O Flyway criará as tabelas automaticamente ao iniciar a aplicação.*

## 📚 Documentação da API (Swagger)

Com a aplicação rodando, acesse a documentação interativa da API através do link:

👉 **[http://localhost:8080/swagger-ui.html](https://www.google.com/search?q=http://localhost:8080/swagger-ui.html)**

 *(Dica: Suba aquele print que você me mandou para a pasta do repositório e coloque o link da imagem aqui\!)*

## 🧪 Como rodar os Testes

Para executar a suíte de testes unitários da regra de negócio:

```bash
mvn test
```

-----

*Desenvolvido para aprendizado em mensagerias.*

