# 🧠 FórumHub

FórumHub é uma API REST desenvolvida com Java e Spring Boot com o objetivo de simular um fórum de discussão. Ela permite o **cadastro, autenticação e gerenciamento de tópicos**, utilizando autenticação com **JWT (JSON Web Token)** e persistência em banco de dados relacional via **Spring Data JPA**.

---

## 🚀 Tecnologias utilizadas

- Java 17
- Spring Boot 3
- Spring Security
- JWT (com TokenService)
- Spring Data JPA
- Hibernate Validator
- PostgreSQL
- Lombok
- Maven

---

## 🔐 Autenticação

A autenticação é feita via token JWT. Após o login, o cliente deve usar o token retornado no cabeçalho `Authorization` das requisições subsequentes.

### 🔑 Endpoint de autenticação

- **POST** `/login`

**Body (JSON):**
```json
{
  "email": "usuario@teste.com",
  "senha": "123456"
}
```

**Resposta:**
```json
{
  "token": "Bearer eyJhbGciOiJIUzI1NiIsInR..."
}
```

---

## 📌 Endpoints protegidos (exigem token)

Todos os endpoints relacionados a tópicos exigem um token JWT válido no cabeçalho:

```http
Authorization: Bearer <seu-token-aqui>
```

### 🔁 Endpoints principais:

| Método | Rota              | Descrição                    |
|--------|-------------------|------------------------------|
| GET    | `/topicos`        | Listar todos os tópicos      |
| POST   | `/topicos`        | Criar um novo tópico         |
| PUT    | `/topicos/{id}`   | Atualizar um tópico existente|
| DELETE | `/topicos/{id}`   | Deletar um tópico            |
| GET    | `/topicos/{id}`   | Detalhar um tópico específico|

---

## 🧪 Testes da API

Você pode testar a API usando o [Postman](https://www.postman.com/) ou [Insomnia](https://insomnia.rest/):

1. Envie uma requisição `POST` para `/login` com email e senha válidos.
2. Copie o token retornado.
3. Nas demais requisições, adicione o header:

```
Authorization: Bearer <token>
```

---

## 📂 Estrutura do projeto

```
src/
├── config/
├── controller/
├── domain/
│   ├── curso/
│   ├── topico/
│   └── usuario/
├── repository/
├── infra/
│   └── exeption/
│   └── security/
├── ForumHubApplication.java
```

---

## 🛠️ Como rodar o projeto localmente

### Pré-requisitos

- Java 17
- Maven
- PostgreSQL

### Passos

1. Clone o repositório:
```bash
git clone https://github.com/seuusuario/forumhub.git
cd forumhub
```

2. Configure o `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/forumhub
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

3. Rode a aplicação:

```bash
./mvnw spring-boot:run
```

---

## 📃 Licença

Este projeto está sob a licença MIT.

---

## 👨‍💻 Autor

Desenvolvido por [Ayran Vieira](https://www.linkedin.com/in/ayran-vieira-dev/)

Entre em contato:

- Email: ayrandeveloper@gmail.com
- Instagram: [@ayran.code](https://www.instagram.com/ayran.code)
