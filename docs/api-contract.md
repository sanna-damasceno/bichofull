# 📄 API Contract – BichoFull

## 1. Visão Geral

O sistema **BichoFull** adotará arquitetura RESTful, utilizando:

- Protocolo: HTTP
- Formato de dados: JSON
- Autenticação: JWT (Bearer Token)
- URL Base: http://localhost:8080/api

O backend (Spring Boot) será responsável pela lógica de negócio e persistência dos dados.  
O frontend (Angular) consumirá os endpoints expostos pela API.

---

## 2. Autenticação

### POST /auth/register

Criação de novo usuário.

#### Request
```json
{
  "name": "Sanna",
  "email": "sanna@email.com",
  "password": "123456"
}
```

#### Response (201 Created)
```json
{
  "id": 1,
  "name": "Sanna",
  "email": "sanna@email.com",
  "balance": 1000.00
}
```

---

### POST /auth/login

Autenticação do usuário.

#### Request
```json
{
  "email": "sanna@email.com",
  "password": "123456"
}
```

#### Response (200 OK)
```json
{
  "token": "jwt-token-aqui",
  "type": "Bearer"
}
```

---

## 3. Usuário

### GET /users/me

Retorna informações do usuário autenticado.

Requer Header:
Authorization: Bearer <token>

#### Response
```json
{
  "id": 1,
  "name": "Sanna",
  "email": "sanna@email.com",
  "balance": 850.00
}
```

---

## 4. Apostas

### POST /bets

Registrar nova aposta.

Requer autenticação.

#### Request
```json
{
  "type": "GRUPO",
  "number": "01",
  "amount": 50.00
}
```

#### Response
```json
{
  "id": 10,
  "type": "GRUPO",
  "number": "01",
  "amount": 50.00,
  "status": "PENDING"
}
```

---

### GET /bets/history

Retorna histórico de apostas do usuário.

Requer autenticação.

#### Response
```json
[
  {
    "id": 10,
    "type": "GRUPO",
    "number": "01",
    "amount": 50.00,
    "result": "WIN",
    "prize": 900.00
  }
]
```

---

## 5. Sorteio

### POST /draw

Realiza simulação de sorteio (admin).

#### Response
```json
{
  "drawNumber": 15,
  "results": [
    "1234",
    "5678",
    "9012",
    "3456",
    "7890"
  ]
}
```

---

## 6. Regras Gerais

- Todas as requisições utilizam JSON.
- Senhas serão armazenadas com hash no backend.
- O saldo do usuário nunca poderá ficar negativo.
- Endpoints protegidos exigem autenticação JWT.
- O frontend deve armazenar o token JWT e enviá-lo no header Authorization.

---

## 7. Status Codes Utilizados

- 200 OK – Operação bem-sucedida
- 201 Created – Recurso criado
- 400 Bad Request – Dados inválidos
- 401 Unauthorized – Não autenticado
- 403 Forbidden – Acesso negado
- 500 Internal Server Error – Erro interno

---

## 8. Observação Final

Este documento representa o contrato inicial da API, definindo como frontend e backend irão se comunicar antes da implementação completa do sistema.