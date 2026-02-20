
##🎲 **BichoFull**

Simulação educacional do Jogo do Bicho. Aposte com dinheiro virtual e acompanhe sorteios!


🎯 **Introdução**

Projeto full stack para fins educacionais onde usuários criam conta, recebem saldo fictício (R$ 1.000,00) e realizam apostas simuladas no Jogo do Bicho.


📋 **Regras de Negócio**

- **25 animais** (grupos de 01 a 25), cada um com 4 dezenas
- **Tipos de aposta:** Grupo, Dezena, Centena, Milhar
- **Premiação:** Grupo (18x), Milhar (4000x) - apenas 1º prêmio
- **Saldo inicial:** R$ 1.000,00 (não pode ficar negativo)
- **Sorteio:** 5 milhares aleatórias (manual via admin)


⚡ **Principais Funcionalidades**

| Categoria | Funcionalidade | Descrição |
|-----------|----------------|-----------|
| 👤 **Usuário** | Cadastro | Criação de conta com nome, e-mail e senha |
| 👤 **Usuário** | Login | Autenticação segura via JWT |
| 👤 **Usuário** | Saldo inicial | R$ 1.000,00 fictícios para começar a apostar |
| 👤 **Usuário** | Carteira virtual | Saldo atualizado em tempo real |
| | | |
| 🎲 **Apostas** | Tabela de animais | Interface com os 25 grupos do jogo do bicho |
| 🎲 **Apostas** | Aposta por Grupo | Escolha um animal (1 a 25) |
| 🎲 **Apostas** | Aposta por Dezena | Escolha dois números (00 a 99) |
| 🎲 **Apostas** | Aposta por Milhar | Escolha quatro números (0000 a 9999) |
| 🎲 **Apostas** | Validação de saldo | Impede apostas com saldo insuficiente |
| | | |
| 🏆 **Sorteios** | Sorteio automático | Geração aleatória de 5 milhares |
| 🏆 **Sorteios** | Sorteio manual | Admin pode simular sorteios |
| 🏆 **Sorteios** | Cálculo de prêmios | Grupo: 18x | Milhar: 4000x (1º prêmio) |
| | | |
| 📊 **Histórico** | Histórico de apostas | Visualização de apostas realizadas |
| 📊 **Histórico** | Resultados | Ganhos/perdas por aposta |

  

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
