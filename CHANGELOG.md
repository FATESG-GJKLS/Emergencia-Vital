# Changelog
# Índices:
- [v1.0.0](#v100-2026-06-14)
- [v0.3.0](#v030-2026-06-13)
- [v0.2.0](#v020-2026-06-13)
- [v0.1.0](#v010-2026-06-06)

## v1.0.0 (2026-06-14)
### Padrões de Projeto Implementados
- Implementação dos Padrões de Projeto (Adapter, Singleton, Factory, Iterator, Decorator, Template Method)
- Correção de bug no Logger que afetava os testes
- Melhorias na experiência do usuário: telas de login, gestor, atendente e profissional
- Adição de filtros funcionais na tela de profissional
- Ajustes de textos e nomes da empresa nas interfaces
- Integração frontend-backend com serviços de autenticação e CRUD
- Adição do PrimeNG ao frontend

### Commits desta versão
- **Merge pull request (#17) Implementação de Padrões de Projeto no Backend**
  - **Autor:** Karina
  - **Data:** 14/06/2026 22:45:19
  - **Commit:** f86d449

- **fix(backend): Logger nulo crashando os testes**
  - **Autor:** GuiSaiUwU
  - **Data:** 14/06/2026 22:40:30
  - **Commit:** e2586b1

- **MERGE (#16): Qualidade de vida do usuário funcionario, Mudanças de textos e Filtros funcionais na tela de profissional**
  - **Autor:** Guilherme
  - **Data:** 14/06/2026 22:29:16
  - **Commit:** 8db0e3d

- **feat(backend): Padrão Templater e Rework do Iterator**
  - **Autor:** GuiSaiUwU
  - **Data:** 14/06/2026 22:24:21
  - **Commit:** 096c64b

- **feat(backend): Padrão de projeto decorator**
  - **Autor:** GuiSaiUwU
  - **Data:** 14/06/2026 21:23:52
  - **Commit:** 68d414b

- **feat(backend): Padrão Iterator**
  - **Autor:** GuiSaiUwU
  - **Data:** 14/06/2026 20:48:04
  - **Commit:** c69a044

- **feat(frontend): Tela de login - Nome da empresa mudado na tela de login**
  - **Autor:** Just-Karina
  - **Data:** 14/06/2026 20:46:05
  - **Commit:** ad7b06b

- **feat(backend): Padrão de projeto Adapter & Factory & Singleton**
  - **Autor:** GuiSaiUwU
  - **Data:** 14/06/2026 20:30:56
  - **Commit:** c141f47

- **feat(frontend): Tela de profissional - Adicionado funcionalidade nos filtros, Texto no botão de deslogar e Texto mostrando o tipo de sistema na sidebar**
  - **Autor:** Just-Karina
  - **Data:** 14/06/2026 19:04:23
  - **Commit:** aaee206

- **feat(frontend): Tela de Gestor - Trocado o nome da empresa, Texto no botão de deslogar e funcionarios sem equipe não aparece mais o gestor/atendente**
  - **Autor:** Just-Karina
  - **Data:** 14/06/2026 19:01:12
  - **Commit:** 5a38f37

- **feat(frontend): Melhorado a experiencia de usuario na tela de atendente com algumas verificações**
  - **Autor:** Just-Karina
  - **Data:** 14/06/2026 18:59:07
  - **Commit:** c50adff

- **feat(frontend): adicionado o primeNG**
  - **Autor:** Just-Karina
  - **Data:** 14/06/2026 18:57:18
  - **Commit:** b362f8a

- **Merge pull request #15 from FATESG-GJKLS/feature/backend-crud**
  - **Autor:** LoriIsDead
  - **Data:** 14/06/2026 09:49:19
  - **Commit:** 34e55c6

- **MERGE: #6 (FATESG-GJKLS/feature/frontend-angular)**
  - **Autor:** Guilherme
  - **Data:** 14/06/2026 04:49:18
  - **Commit:** ae74d02

- **feat(frontend): Tela do profissional da saúde com service (#14)**
  - **Autor:** GuiSaiUwU
  - **Data:** 14/06/2026 04:47:31
  - **Commit:** 3a06f0e

- **feat(frontend): Tela do atendente com service (#13)**
  - **Autor:** GuiSaiUwU
  - **Data:** 14/06/2026 04:46:55
  - **Commit:** ce3e537

- **feat(frontend): Tela do Gestor configurada com informações do service (#12)**
  - **Autor:** GuiSaiUwU
  - **Data:** 14/06/2026 04:46:21
  - **Commit:** 9b485ef

- **feat(frontend): Tela de Login com autenticação (#7)**
  - **Autor:** GuiSaiUwU
  - **Data:** 14/06/2026 04:45:34
  - **Commit:** 9ddd66b

- **feat(frontend): Funções para chamar o backend (Autenticação e Service para o Gestor/Atendente/Profissional)**
  - **Autor:** GuiSaiUwU
  - **Data:** 14/06/2026 04:43:47
  - **Commit:** dfb8037

- **feat(frontend): Adição de novas telas**
  - **Autor:** LoriIsDead
  - **Data:** 10/06/2026 20:15:29
  - **Commit:** b77e318

- **feat: inicia estrutura base do frontend Angular**
  - **Autor:** O
  - **Data:** 08/06/2026 08:23:23
  - **Commit:** efecc5a

---
## v0.3.0 (2026-06-13)
### Baseline 03
- CRUD completo de Gestores
- CRUD completo de Atendentes
- CRUD completo de Profissionais de Saúde
- Modelagem de Ocorrências
- Modelagem de Equipes e Ambulâncias

### Commits desta versão
- **feat(backend): Arrumado CORS (dnv) | Arrumado alguns endpoints (#14 #13 #12)**
  - **Autor:** GuiSaiUwU
  - **Data:** 14/06/2026 04:22:58
  - **Commit:** fefb38f

- **feat(backend): Eu odeio CORS**
  - **Autor:** GuiSaiUwU
  - **Data:** 14/06/2026 04:21:07
  - **Commit:** e958884

- **feat(backend): Permitindo Cross nos eendpoints**
  - **Autor:** GuiSaiUwU
  - **Data:** 14/06/2026 00:12:11
  - **Commit:** 284f297

- **ISS(12): Funções do Profissional de Saúde (#14)**
  - **Autor:** GuiSaiUwU
  - **Data:** 14/06/2026 00:10:29
  - **Commit:** fa5d2b5

- **feat(backend): Funções do Atendente (#13)**
  - **Autor:** GuiSaiUwU
  - **Data:** 13/06/2026 21:02:53
  - **Commit:** 9454dcc

- **feat(backend): Modelagem de Despacho e implementado checks em campos necessários (CPF, Telefone, Placa) com @Pattern**
  - **Autor:** GuiSaiUwU
  - **Data:** 13/06/2026 21:01:30
  - **Commit:** 53155b0

- **feat(backend): CRUD Gestor (#12)**
  - **Autor:** GuiSaiUwU
  - **Data:** 13/06/2026 02:53:47
  - **Commit:** bedabb2

- **feat(backend): Modelagem Equipe (#9)**
  - **Autor:** GuiSaiUwU
  - **Data:** 13/06/2026 02:40:23
  - **Commit:** 510f79a

- **feat(backend): Modelagem das ambulancias (#11)**
  - **Autor:** GuiSaiUwU
  - **Data:** 13/06/2026 02:36:34
  - **Commit:** aed4a20

- **feat(backend): Ocorrencia modelagem de dados inicial (#8)**
  - **Autor:** GuiSaiUwU
  - **Data:** 12/06/2026 01:41:11
  - **Commit:** f3bb528

---
## v0.2.0 (2026-06-13)
### Baseline 02
- Implementado login com JWT
- Autorização baseada em roles (Gestor/Atendente/Profissional)
- Modelagem de funcionários

### Commits desta versão
- **feat(backend): Implementado login e camadas de autorização com JWT (#7)**
  - **Autor:** GuiSaiUwU
  - **Data:** 12/06/2026 00:22:18
  - **Commit:** ca0f745

- **feat(backend): modelagem de dados do funcionario**
  - **Autor:** GuiSaiUwU
  - **Data:** 12/06/2026 00:18:03
  - **Commit:** 72cdcb4

- **feat(backend): Adicionado dependencias para login (#7) e configurações iniciais do DB**
  - **Autor:** GuiSaiUwU
  - **Data:** 11/06/2026 23:58:47
  - **Commit:** 6f64ba6

- **feat(backend): ignorando o banco de dados no git**
  - **Autor:** GuiSaiUwU
  - **Data:** 11/06/2026 23:57:05
  - **Commit:** fed974b

- **feat(CI): Criado o CI #3**
  - **Autor:** GuiSaiUwU
  - **Data:** 06/06/2026 13:04:25
  - **Commit:** 37bac99
---

## v0.1.0 (2026-06-06)
### Baseline 01:
- Estrutura inicial do projeto Spring Boot
- Estrutura inicial do projeto Angular
- Configuração de CI/CD
- Configuração inicial do banco de dados

### Commits desta versão
- **feat(frontend): Inicializado projeto Angular #1**
  - **Autor:** GuiSaiUwU
  - **Data:** 06/06/2026 13:04:04
  - **Commit:** f432164

- **feat(backend): Inicializado projeto spring #2**
  - **Autor:** GuiSaiUwU
  - **Data:** 06/06/2026 13:03:20
  - **Commit:** 5a36180

- **Initial commit**
  - **Autor:** Guilherme
  - **Data:** 06/06/2026 12:59:10
  - **Commit:** 13a1e05