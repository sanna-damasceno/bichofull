
---

# 🎲 BichoFull

Simulação educacional do **Jogo do Bicho**, com autenticação segura, arquitetura REST e testes automatizados.

Projeto **Full Stack** onde usuários criam contas, recebem saldo fictício (R$ 1.000,00) e realizam apostas simuladas.

---

# 🎯 Objetivo do Projeto

Desenvolver uma aplicação full stack aplicando:

* Arquitetura REST
* Autenticação stateless com JWT
* Separação entre entidade e DTO
* Testes automatizados
* Integração Contínua (CI)
* Boas práticas de backend com Spring Boot
* Documentação de API com OpenAPI (Swagger)

---

# 📋 Regras de Negócio

* **25 animais** (grupos de 01 a 25), cada um com 4 dezenas
* **Tipos de aposta:** Grupo, Dezena, Centena, Milhar
* **Premiação:**
  * Grupo → **18x o valor apostado**
  * Milhar → **4000x o valor apostado** (1º prêmio)
* **Saldo inicial:** R$ 1.000,00 fictícios
* **Saldo nunca pode ficar negativo**
* **Sorteio:** 5 milhares aleatórias

---

# ⚡ Principais Funcionalidades

| Categoria        | Funcionalidade       | Descrição                                   |               |
| ---------------- | -------------------- | ------------------------------------------- | ------------- |
| 👤 **Usuário** | Cadastro             | Criação de conta com nome, e-mail e senha   |               |
| 👤 **Usuário** | Login                | Autenticação segura via JWT                 |               |
| 👤 **Usuário** | Saldo inicial        | R$ 1.000,00 fictícios para começar          |               |
| 👤 **Usuário** | Carteira virtual     | Saldo atualizado em tempo real              |               |
|                  |                      |                                             |               |
| 🎲 **Apostas** | Tabela de animais    | Interface com os 25 grupos do jogo do bicho |               |
| 🎲 **Apostas** | Aposta por Grupo     | Escolha um animal (1 a 25)                  |               |
| 🎲 **Apostas** | Aposta por Dezena    | Escolha dois números (00 a 99)              |               |
| 🎲 **Apostas** | Aposta por Milhar    | Escolha quatro números (0000 a 9999)        |               |
| 🎲 **Apostas** | Validação de saldo   | Impede apostas com saldo insuficiente       |               |
|                  |                      |                                             |               |
| 🏆 **Sorteios** | Sorteio automático   | Geração aleatória de 5 milhares             |               |
| 🏆 **Sorteios** | Sorteio manual       | Admin pode simular sorteios                 |               |
| 🏆 **Sorteios** | Cálculo de prêmios   | Grupo: 18x                                  | Milhar: 4000x |
|                  |                      |                                             |               |
| 📊 **Histórico** | Histórico de apostas | Visualização das apostas realizadas         |               |
| 📊 **Histórico** | Resultados           | Ganhos e perdas por aposta                  |               |

---

# ⚡ Status Atual

* ✅ Cadastro de usuário
* ✅ Login com JWT
* ✅ Saldo inicial automático (R$ 1.000,00)
* ✅ Registro de apostas
* ✅ Histórico de apostas
* ✅ Motor de sorteio
* ✅ Cálculo automático de prêmios
* ✅ Atualização automática de saldo
* ✅ Segurança stateless com JWT
* ✅ Filtro JWT customizado
* ✅ Testes unitários
* ✅ Banco rodando via Docker
* ✅ H2 configurado para testes
* ✅ CI com GitHub Actions
* ✅ API documentada com Swagger (OpenAPI)

---

# 🔐 Autenticação

A aplicação utiliza:

* **JWT (Bearer Token)**
* **Spring Security**
* **BCrypt Password Encoder**
* **Filtro customizado `JwtAuthenticationFilter`**
* Segurança **stateless**

### Fluxo de autenticação

1. Usuário faz login
2. Backend gera um **JWT**
3. Frontend envia o token no header:



Authorization: Bearer \<token\>



4. O filtro JWT valida o token antes de acessar rotas protegidas

---

# 📡 Endpoints da API

## 🔹 Auth

| Método | Endpoint             | Descrição              |
| ------ | -------------------- | ---------------------- |
| POST   | `/api/auth/register` | Cadastro de usuário    |
| POST   | `/api/auth/login`    | Login e geração de JWT |

---

## 🔹 Usuário

| Método | Endpoint             | Descrição                    |
| ------ | -------------------- | ---------------------------- |
| GET    | `/api/users/me`      | Dados do usuário autenticado |
| GET    | `/api/users/balance` | Saldo do usuário             |

---

## 🔹 Apostas

| Método | Endpoint            | Descrição                       |
| ------ | ------------------- | ------------------------------- |
| POST   | `/api/bets`         | Registrar uma aposta            |
| GET    | `/api/bets/my-bets` | Histórico de apostas do usuário |

---

## 🔹 Sorteios

| Método | Endpoint         | Descrição                            |
| ------ | ---------------- | ------------------------------------ |
| POST   | `/api/draws`     | Criar sorteio manual                 |
| POST   | `/api/draws/run` | Executar sorteio e processar apostas |

---

# 📚 Documentação da API

A API está documentada utilizando **Swagger (OpenAPI)**.

Após iniciar o backend, acesse:

```text
http://localhost:8080/swagger-ui/index.html
````

A interface permite:

  * visualizar todos os endpoints
  * testar requisições diretamente
  * autenticar utilizando JWT
  * visualizar modelos de request/response

-----

# 🧪 Testes Automatizados

O projeto possui testes unitários e de integração cobrindo os principais serviços da aplicação.

### Serviços testados

**AuthService**

  * Cadastro de usuário com sucesso
  * Cadastro com e-mail duplicado
  * Login com sucesso
  * Login com senha inválida

**BetService**

  * Criação de aposta com sucesso
  * Validação de saldo insuficiente
  * Recuperação do histórico de apostas do usuário

**BetProcessorService**

  * Processamento de apostas vencedoras
  * Processamento de apostas perdedoras
  * Cálculo de prêmio para apostas vencedoras

**BetChecker**

  * Verificação de vitória para apostas de:
      * Grupo
      * Dezena
      * Milhar
  * Verificação de derrota para os mesmos tipos de aposta

**PrizeCalculator**

  * Cálculo de prêmio para:
      * Grupo
      * Dezena
      * Milhar

**UserService**

  * Consulta de saldo do usuário

**UserRepository**

  * Persistência de usuários no banco de dados (teste de integração)

### Execução dos testes

Para rodar os testes localmente:

```bash
./mvnw test
```

-----

# 🔄 Integração Contínua (CI)

GitHub Actions configurado para:

  * Build automático
  * Execução de testes
  * Validação a cada **push** ou **pull request**

Se algum teste falhar, o build é interrompido.

-----

# 🛠️ Stack

| Camada            | Tecnologia                   |
| ----------------- | ---------------------------- |
| Backend           | Spring Boot 3                |
| Segurança         | Spring Security + JWT        |
| Banco             | MySQL (Docker)               |
| Banco para testes | H2                           |
| Build             | Maven                        |
| CI/CD             | GitHub Actions               |
| API Docs          | Swagger / OpenAPI            |
| Frontend          | Angular (em desenvolvimento) |

-----

# 🚀 Como Executar o Projeto

Siga os passos abaixo para configurar o ambiente e executar a aplicação localmente.

### 📋 Pré-requisitos

Certifique-se de ter as seguintes ferramentas instaladas em sua máquina:

  * **[Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)** ou superior
  * **[Maven](https://maven.apache.org/download.cgi)** (opcional, pois o projeto inclui o Maven Wrapper `mvnw`)
  * **[Docker](https://www.docker.com/products/docker-desktop/)** e **Docker Compose** (para rodar o banco de dados MySQL)
  * **[Node.js](https://nodejs.org/)** e **Angular CLI** (`npm install -g @angular/cli`) (para o frontend)
  * **[Git](https://git-scm.com/)**

-----

### 1️⃣ Clone o Repositório

No seu terminal, clone o projeto e acesse o diretório principal:

```bash
git clone [https://github.com/SEU_USUARIO/bichofull.git](https://github.com/SEU_USUARIO/bichofull.git)
cd bichofull
```

-----

### 2️⃣ Configurando o Banco de Dados (Docker)

A aplicação utiliza o MySQL. A forma mais fácil de subir o banco é utilizando o Docker Compose fornecido na pasta do backend.

1.  Navegue até a pasta do backend:
    ```bash
    cd backend
    ```
2.  Inicie o banco de dados em segundo plano:
    ```bash
    docker-compose up -d
    ```

> **Nota:** O Docker Compose já está configurado para criar o banco de dados `bichofull_db` na porta padrão `3306` com as credenciais especificadas no arquivo `docker-compose.yml`.

-----

### 3️⃣ Configuração de Ambiente do Backend

O projeto já possui um arquivo `application.properties` (ou `.yml`) configurado para desenvolvimento. No entanto, é recomendável verificar as credenciais ou configurar variáveis de ambiente de segurança.

Crie variáveis de ambiente no seu sistema ou ajuste diretamente no `src/main/resources/application.properties`:

```properties
# Credenciais do Banco
spring.datasource.url=jdbc:mysql://localhost:3306/bichofull_db?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=root

# Configuração de Segurança (Modifique em produção!)
jwt.secret=bichofull-secret-key-bichofull-secret-key-minimum-size
admin.default.password=admin123
```

-----

### 4️⃣ Executando o Backend (Spring Boot)

Com o banco de dados rodando, você pode iniciar o servidor backend.

Ainda dentro da pasta `backend`, execute:

**No Linux/macOS:**

```bash
./mvnw spring-boot:run
```

**No Windows:**

```cmd
mvnw.cmd spring-boot:run
```

O servidor iniciará na porta **`8080`**.
Para testar, acesse o Swagger no seu navegador: `http://localhost:8080/swagger-ui/index.html`

> **Dica:** O sistema roda a classe `AdminInitializer` ao iniciar, criando automaticamente um usuário administrador com o e-mail `admin@bichofull.com` e a senha definida no properties.

-----

### 5️⃣ Executando o Frontend (Angular)

Abra uma nova aba no seu terminal para rodar o frontend.

1.  Volte para a raiz do projeto e acesse a pasta do frontend:
    ```bash
    cd ../frontend
    ```
2.  Instale as dependências do Node:
    ```bash
    npm install
    ```
3.  Inicie o servidor de desenvolvimento do Angular:
    ```bash
    ng serve
    ```

A aplicação frontend estará disponível em: **`http://localhost:4200`**

-----

### 🧪 Rodando os Testes

Para garantir que tudo está funcionando perfeitamente (o projeto utiliza um banco em memória H2 para os testes):

1.  Vá para a pasta do backend:
    ```bash
    cd backend
    ```
2.  Execute o comando de testes:
    ```bash
    ./mvnw test
    ```

-----

# 🏗️ Arquitetura

Fluxo da aplicação:

```
Frontend → Backend → Banco de Dados
```

A aplicação segue:

  * Arquitetura em camadas
  * Separação de responsabilidades
  * DTO para exposição de dados
  * Entidades isoladas da API

-----

# 👤 Autora

**Sanna Damasceno**

Projeto educacional – Full Stack Application

-----

```
```