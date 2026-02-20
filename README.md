
🎲 **BichoFull**

Simulação educacional do Jogo do Bicho. Aposte com dinheiro virtual e acompanhe sorteios!


🎯 **Introdução**

Projeto full stack para fins educacionais onde usuários criam conta, recebem saldo fictício (R$ 1.000,00) e realizam apostas simuladas no Jogo do Bicho.


📋 **Regras de Negócio**

- **25 animais** (grupos de 01 a 25), cada um com 4 dezenas
- **Tipos de aposta:** Grupo, Dezena, Centena, Milhar
- **Premiação:** Grupo (18x), Milhar (4000x) - apenas 1º prêmio
- **Saldo inicial:** R$ 1.000,00 (não pode ficar negativo)
- **Sorteio:** 5 milhares aleatórias (manual via admin)
  

🛠️ **Stack**

| Camada | Tecnologia |
|--------|------------|
| Backend | Spring Boot |
| Frontend | Angular |
| Banco | MySQL |
| Auth | JWT |
| Doc | Swagger |
| CI/CD | GitHub Actions |


🏗️ **Arquitetura do Sistema**

![Diagrama de Arquitetura - BichoFull](modelagem.png)

| Camada | Tecnologia | Função |
|--------|------------|--------|
| **Frontend** | Angular | Interface do usuário (web/mobile) |
| **Backend** | Spring Boot | API, regras de negócio, segurança |
| **Banco** | MySQL | Persistência de dados |

**Fluxo:** Frontend → Backend → Banco

👤 **Autora**

**sanna-damasceno**

🎓 Projeto educacional
