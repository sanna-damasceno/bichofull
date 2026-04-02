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
* **Tipos de aposta:** Grupo, Dezena, Milhar
* **Premiação:**
  * Grupo → **18x o valor apostado**
  * Dezena → **60x o valor apostado**
  * Milhar → **4000x o valor apostado** (1º prêmio)
* **Saldo inicial:** R$ 1.000,00 fictícios
* **Saldo nunca pode ficar negativo**
* **Sorteio:** 5 milhares aleatórias

---

# ⚡ Principais Funcionalidades

| Categoria        | Funcionalidade       | Descrição                                   |
| ---------------- | -------------------- | ------------------------------------------- |
| 👤 **Usuário** | Cadastro             | Criação de conta com nome, e-mail e senha   |
| 👤 **Usuário** | Login                | Autenticação segura via JWT                 |
| 👤 **Usuário** | Saldo inicial        | R$ 1.000,00 fictícios para começar          |
| 👤 **Usuário** | Carteira virtual     | Saldo atualizado em tempo real              |
|                  |                      |                                             |
| 🎲 **Apostas** | Tabela de animais    | Interface com os 25 grupos do jogo do bicho |
| 🎲 **Apostas** | Aposta por Grupo     | Escolha um animal (1 a 25)                  |
| 🎲 **Apostas** | Aposta por Dezena    | Escolha dois números (00 a 99)              |
| 🎲 **Apostas** | Aposta por Milhar    | Escolha quatro números (0000 a 9999)        |
| 🎲 **Apostas** | Validação de saldo   | Impede apostas com saldo insuficiente       |
|                  |                      |                                             |
| 🏆 **Sorteios** | Sorteio automático   | Geração aleatória de 5 milhares             |
| 🏆 **Sorteios** | Sorteio manual       | Admin pode simular sorteios                 |
| 🏆 **Sorteios** | Cálculo de prêmios   | Grupo: 18x / Dezena: 60x / Milhar: 4000x    |
|                  |                      |                                             |
| 📊 **Histórico** | Histórico de apostas | Visualização das apostas realizadas         |
| 📊 **Histórico** | Resultados           | Ganhos e perdas por aposta                  |

---

# ⚡ Status

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

```http
Authorization: Bearer <token>
```

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
```

A interface permite:

* visualizar todos os endpoints
* testar requisições diretamente
* autenticar utilizando JWT
* visualizar modelos de request/response

---

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

---

# 🔄 Integração Contínua (CI)

GitHub Actions configurado para:

* Build automático
* Execução de testes
* Validação a cada **push** ou **pull request**

Se algum teste falhar, o build é interrompido.

---

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

---

# 🚀 Como Executar o Projeto

Siga os passos abaixo para configurar o ambiente e executar a aplicação localmente. Você pode rodar de forma completa (via Docker) ou em modo desenvolvimento.

### 📋 Pré-requisitos

Certifique-se de ter as seguintes ferramentas instaladas em sua máquina:
* **[Docker](https://www.docker.com/products/docker-desktop/)** e **Docker Compose**
* **[Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)** ou superior
* **[Node.js](https://nodejs.org/)** e **Angular CLI** (`npm install -g @angular/cli`)
* **[Git](https://git-scm.com/)**

---

### 1️⃣ Clone o Repositório

No seu terminal, clone o projeto e acesse o diretório principal:

```bash
git clone [https://github.com/SEU_USUARIO/bichofull.git](https://github.com/SEU_USUARIO/bichofull.git)
cd bichofull
```

---

### ▶️ OPÇÃO 1: Execução Completa via Docker

Esta é a maneira mais rápida de ver a aplicação rodando. O Docker irá subir o Banco de Dados, a API Spring Boot e o site Angular simultaneamente.

Na raiz do projeto (onde está o arquivo `docker-compose.yml`), execute:

```bash
docker-compose up -d --build
```

**Acesse a aplicação:**
* **Frontend (Site):** http://localhost (Porta 80)
* **Backend (Swagger):** http://localhost:8080/swagger-ui/index.html


> **Dica:** O sistema roda a classe `AdminInitializer` ao iniciar, criando automaticamente um usuário administrador com o e-mail `admin@bichofull.com` e a senha definida no properties.

---

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

---

# 👤 Autora

**Sanna Damasceno**

Projeto educacional – Full Stack Application

---
