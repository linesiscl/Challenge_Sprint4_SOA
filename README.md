# Sprint 4 - SOA e Web Services

## Integrantes

- Aline Fernandes Zeppelini - RM97966
- Camilly Breitbach Ishida - RM551474
- Julia Leite Galvão - RM550201
- Jessica Costacurta - RM99068

---

## Descrição do Projeto

O **Investments API** é uma aplicação desenvolvida em Java com Spring Boot que gerencia usuários e investimentos.  
A API oferece endpoints para registro, autenticação de usuários e operações relacionadas a investimentos, garantindo segurança por meio de JWT (JSON Web Tokens).

---

## Tecnologias utilizadas

Java 17

Spring Boot 3

Spring Security (JWT)

Spring Data JPA

H2 Database (banco em memória)

JUnit 5 e Mockito (testes)

Jacoco (cobertura de testes)

Gradle (gerenciamento de dependências)

---

## Instruções de execução

1. Clonar o repositório
```bash
git clone https://github.com/linesiscl/Challenge_Sprint4_SOA.git
```

2. Abrir o projeto em uma IDE de sua escolha e abrir o terminal
3. Compile e execute a aplicação:
```
./gradlew bootRun
```

4. A API estará disponível em `http://localhost:8080`
5. Acesse os endpoints pelo Postman

---

## Instruções para rodar os testes

Para rodar os testes e gerar relatórios de cobertura:
```
./gradlew test jacocoTestReport
```

Os relatórios ficam nas pastas `build\reports\jacoco\test\html\index.html` e `build\reports\tests\test\index.html`

---

## Principais Endpoints

### Autenticação

#### **1. Registrar novo usuário**
**Endpoint:** `POST /auth/register`

**Descrição:**  
Cria um novo usuário no sistema.

**Exemplo de requisição:**
```json
{
  "username": "camilly",
  "password": "123456"
}
```

#### **2. Login do usuário**
**Endpoint:** `POST /auth/login`

**Descrição:**  
Autentica o usuário e retorna um token JWT para acessar os endpoints protegidos.

**Exemplo de requisição:**
```json
{
  "username": "camilly",
  "password": "123456"
}
```

### Investimentos

#### **1. Criar um novo investimento**
**Endpoint:** `POST POST /investments`

**Descrição:**  
Cadastra um novo investimento no sistema.
É necessário enviar o token JWT no cabeçalho da requisição.

**Header**
```
Authorization: Bearer <seu-token-jwt>
```

**Exemplo de requisição:**
```json
{
  "assetName": "Tesouro Direto",
  "amount": 1500.00
}
```

#### **2. Listar todos os investimentos**
**Endpoint:** `GET /investments`

**Descrição:**  
Retorna uma lista com todos os investimentos cadastrados pelo usuário logado.

**Header**
```
Authorization: Bearer <seu-token-jwt>
```

#### **3. Deletar um investimento**
**Endpoint:** `DELETE /investments/{id}`

**Descrição:**  
Remove um investimento pelo seu ID.

**Header**
```
Authorization: Bearer <seu-token-jwt>
```


