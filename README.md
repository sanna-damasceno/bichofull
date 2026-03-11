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
| 👤 **Usuário**   | Cadastro             | Criação de conta com nome, e-mail e senha   |               |
| 👤 **Usuário**   | Login                | Autenticação segura via JWT                 |               |
| 👤 **Usuário**   | Saldo inicial        | R$ 1.000,00 fictícios para começar          |               |
| 👤 **Usuário**   | Carteira virtual     | Saldo atualizado em tempo real              |               |
|                  |                      |                                             |               |
| 🎲 **Apostas**   | Tabela de animais    | Interface com os 25 grupos do jogo do bicho |               |
| 🎲 **Apostas**   | Aposta por Grupo     | Escolha um animal (1 a 25)                  |               |
| 🎲 **Apostas**   | Aposta por Dezena    | Escolha dois números (00 a 99)              |               |
| 🎲 **Apostas**   | Aposta por Milhar    | Escolha quatro números (0000 a 9999)        |               |
| 🎲 **Apostas**   | Validação de saldo   | Impede apostas com saldo insuficiente       |               |
|                  |                      |                                             |               |
| 🏆 **Sorteios**  | Sorteio automático   | Geração aleatória de 5 milhares             |               |
| 🏆 **Sorteios**  | Sorteio manual       | Admin pode simular sorteios                 |               |
| 🏆 **Sorteios**  | Cálculo de prêmios   | Grupo: 18x                                  | Milhar: 4000x |
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

1️⃣ Usuário faz login
2️⃣ Backend gera um **JWT**
3️⃣ Frontend envia o token no header:

```
Authorization: Bearer <token>
```

4️⃣ O filtro JWT valida o token antes de acessar rotas protegidas

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

```
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
- Cadastro de usuário com sucesso
- Cadastro com e-mail duplicado
- Login com sucesso
- Login com senha inválida

**BetService**
- Criação de aposta com sucesso
- Validação de saldo insuficiente
- Recuperação do histórico de apostas do usuário

**BetProcessorService**
- Processamento de apostas vencedoras
- Processamento de apostas perdedoras
- Cálculo de prêmio para apostas vencedoras

**BetChecker**
- Verificação de vitória para apostas de:
  - Grupo
  - Dezena
  - Milhar
- Verificação de derrota para os mesmos tipos de aposta

**PrizeCalculator**
- Cálculo de prêmio para:
  - Grupo
  - Dezena
  - Milhar

**UserService**
- Consulta de saldo do usuário

**UserRepository**
- Persistência de usuários no banco de dados (teste de integração)

### Execução dos testes

Para rodar os testes localmente:

```bash
./mvnw test

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

# 🏗️ Arquitetura

![Diagrama de Arquitetura - BichoFull](modelagem.png)

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
