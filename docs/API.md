# Contrato da API - BichoFull

**Base URL:** `http://localhost:8080/api/v1`
**Formato:** JSON
**Autenticação:** JWT (Bearer Token)

---

## AUTENTICAÇÃO

### **POST /auth/register**
Registra um novo usuário.

**Request Body:**
```json
{
  "name": "Exemplo",
  "email": "exemplo@email.com",
  "password": "123456"
}
```

**Response (201 Created):**
```json
{
  "id": 1,
  "name": "Exemplo",
  "email": "exemplo@email.com",
  "balance": 1000.00,
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkV4ZW1wbG8iLCJpYXQiOjE1MTYyMzkwMjJ9.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
}
```

**Response (400 Bad Request):**
```json
{
  "status": 400,
  "message": "E-mail já cadastrado",
  "timestamp": "2024-01-01T12:00:00"
}
```

---

### **POST /auth/login**
Autentica um usuário existente.

**Request Body:**
```json
{
  "email": "exemplo@email.com",
  "password": "123456"
}
```

**Response (200 OK):**
```json
{
  "id": 1,
  "name": "Exemplo",
  "email": "exemplo@email.com",
  "balance": 1000.00,
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkV4ZW1wbG8iLCJpYXQiOjE1MTYyMzkwMjJ9.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
}
```

**Response (401 Unauthorized):**
```json
{
  "status": 401,
  "message": "Credenciais inválidas",
  "timestamp": "2024-01-01T12:00:00"
}
```

---

## USUÁRIO

### **GET /users/balance**
Retorna o saldo atual do usuário.

**Headers:**
```
Authorization: Bearer {token}
```

**Response (200 OK):**
```json
{
  "balance": 950.00,
  "userId": 1
}
```

---

## APOSTAS

### **POST /bets**
Registra uma nova aposta.

**Headers:**
```
Authorization: Bearer {token}
Content-Type: application/json
```

**Request Body:**

*Para aposta em GRUPO:*
```json
{
  "betType": "GROUP",
  "numbers": "01",
  "amount": 10.00
}
```

*Para aposta em DEZENA:*
```json
{
  "betType": "TEN",
  "numbers": "23",
  "amount": 5.00
}
```

*Para aposta em MILHAR:*
```json
{
  "betType": "THOUSAND",
  "numbers": "1234",
  "amount": 2.00
}
```

**Response (201 Created):**
```json
{
  "id": 42,
  "status": "PENDING",
  "message": "Aposta registrada com sucesso",
  "newBalance": 940.00
}
```

**Response (400 Bad Request):**
```json
{
  "status": 400,
  "message": "Saldo insuficiente",
  "currentBalance": 5.00,
  "requiredAmount": 10.00
}
```

---

### **GET /bets/history**
Retorna histórico de apostas do usuário.

**Headers:**
```
Authorization: Bearer {token}
```

**Query Params (opcionais):**
- `page=0` (número da página)
- `size=10` (itens por página)
- `status=PENDING` (filtrar por status)

**Response (200 OK):**
```json
{
  "content": [
    {
      "id": 42,
      "date": "2024-01-01T10:30:00",
      "betType": "GROUP",
      "numbers": "01",
      "amount": 10.00,
      "status": "LOST",
      "prize": 0.00,
      "drawDate": "2024-01-01T12:00:00"
    },
    {
      "id": 41,
      "date": "2024-01-01T09:15:00",
      "betType": "THOUSAND",
      "numbers": "1234",
      "amount": 2.00,
      "status": "WON",
      "prize": 8000.00,
      "drawDate": "2024-01-01T12:00:00"
    }
  ],
  "totalPages": 1,
  "totalElements": 2,
  "page": 0,
  "size": 10
}
```

---

## SORTEIOS

### **GET /draws/latest**
Retorna o último sorteio realizado.

**Response (200 OK):**
```json
{
  "id": 5,
  "drawDate": "2024-01-01T12:00:00",
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

### **POST /draws/simulate** (Admin apenas)
Gera um novo sorteio manualmente.

**Headers:**
```
Authorization: Bearer {token} (ADMIN)
```

**Response (201 Created):**
```json
{
  "id": 6,
  "drawDate": "2024-01-01T15:00:00",
  "results": [
    "4321",
    "8765",
    "2109",
    "6543",
    "0987"
  ],
  "winners": 3,
  "totalPaid": 24000.00
}
```

---

## ANIMAIS

### **GET /animals**
Retorna a tabela completa de animais.

**Response (200 OK):**
```json
[
  {
    "id": 1,
    "name": "Avestruz",
    "group": 1,
    "numbers": ["01", "02", "03", "04"]
  },
  {
    "id": 2,
    "name": "Águia",
    "group": 2,
    "numbers": ["05", "06", "07", "08"]
  },
  {
    "id": 3,
    "name": "Burro",
    "group": 3,
    "numbers": ["09", "10", "11", "12"]
  },
  {
    "id": 4,
    "name": "Borboleta",
    "group": 4,
    "numbers": ["13", "14", "15", "16"]
  },
  {
    "id": 5,
    "name": "Cachorro",
    "group": 5,
    "numbers": ["17", "18", "19", "20"]
  },
  {
    "id": 6,
    "name": "Cabra",
    "group": 6,
    "numbers": ["21", "22", "23", "24"]
  },
  {
    "id": 7,
    "name": "Carneiro",
    "group": 7,
    "numbers": ["25", "26", "27", "28"]
  },
  {
    "id": 8,
    "name": "Camelo",
    "group": 8,
    "numbers": ["29", "30", "31", "32"]
  },
  {
    "id": 9,
    "name": "Cobra",
    "group": 9,
    "numbers": ["33", "34", "35", "36"]
  },
  {
    "id": 10,
    "name": "Coelho",
    "group": 10,
    "numbers": ["37", "38", "39", "40"]
  },
  {
    "id": 11,
    "name": "Cavalo",
    "group": 11,
    "numbers": ["41", "42", "43", "44"]
  },
  {
    "id": 12,
    "name": "Elefante",
    "group": 12,
    "numbers": ["45", "46", "47", "48"]
  },
  {
    "id": 13,
    "name": "Galo",
    "group": 13,
    "numbers": ["49", "50", "51", "52"]
  },
  {
    "id": 14,
    "name": "Gato",
    "group": 14,
    "numbers": ["53", "54", "55", "56"]
  },
  {
    "id": 15,
    "name": "Jacaré",
    "group": 15,
    "numbers": ["57", "58", "59", "60"]
  },
  {
    "id": 16,
    "name": "Leão",
    "group": 16,
    "numbers": ["61", "62", "63", "64"]
  },
  {
    "id": 17,
    "name": "Macaco",
    "group": 17,
    "numbers": ["65", "66", "67", "68"]
  },
  {
    "id": 18,
    "name": "Porco",
    "group": 18,
    "numbers": ["69", "70", "71", "72"]
  },
  {
    "id": 19,
    "name": "Pavão",
    "group": 19,
    "numbers": ["73", "74", "75", "76"]
  },
  {
    "id": 20,
    "name": "Peru",
    "group": 20,
    "numbers": ["77", "78", "79", "80"]
  },
  {
    "id": 21,
    "name": "Touro",
    "group": 21,
    "numbers": ["81", "82", "83", "84"]
  },
  {
    "id": 22,
    "name": "Tigre",
    "group": 22,
    "numbers": ["85", "86", "87", "88"]
  },
  {
    "id": 23,
    "name": "Urso",
    "group": 23,
    "numbers": ["89", "90", "91", "92"]
  },
  {
    "id": 24,
    "name": "Veado",
    "group": 24,
    "numbers": ["93", "94", "95", "96"]
  },
  {
    "id": 25,
    "name": "Vaca",
    "group": 25,
    "numbers": ["97", "98", "99", "00"]
  }
]
```

---

## CÓDIGOS DE STATUS

| Código | Descrição |
|--------|-----------|
| 200 | OK - Requisição bem-sucedida |
| 201 | Created - Recurso criado |
| 400 | Bad Request - Erro na requisição |
| 401 | Unauthorized - Não autenticado |
| 403 | Forbidden - Sem permissão |
| 404 | Not Found - Recurso não encontrado |
| 422 | Unprocessable Entity - Regra de negócio violada |
| 500 | Internal Server Error - Erro no servidor |

---

## EXEMPLO DE USO (cURL)

### Login:
```bash
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"exemplo@email.com","password":"123456"}'
```

### Apostar:
```bash
curl -X POST http://localhost:8080/api/v1/bets \
  -H "Authorization: Bearer SEU_TOKEN_AQUI" \
  -H "Content-Type: application/json" \
  -d '{"betType":"GROUP","numbers":"01","amount":10.00}'
```

### Ver histórico:
```bash
curl -X GET http://localhost:8080/api/v1/bets/history \
  -H "Authorization: Bearer SEU_TOKEN_AQUI"
```
