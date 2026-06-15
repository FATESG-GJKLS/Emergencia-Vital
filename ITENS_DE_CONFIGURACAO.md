# Itens de Configuração (IC) – Sistema Emergência Vital

## Objetivo
Este documento relaciona os principais Itens de Configuração (ICs) identificados na aplicação, considerando apenas os artefatos que possuem impacto direto na arquitetura, configuração, segurança e regras de negócio.

## IC-01 – application.properties
**Localização:** `src/main/resources/application.properties`

Responsável pelas configurações gerais da aplicação, incluindo:
- Conexão com banco de dados;
- Configurações do Spring Boot;
- Configurações de segurança;
- Parâmetros de execução do sistema.

---

## IC-02 – ApiApplication.java
**Localização:** `src/main/java/com/gjkls/emergencia/vital/api/ApiApplication.java`

Classe principal da aplicação Spring Boot.
Responsável pela inicialização do sistema e carregamento do contexto da aplicação.

---

## IC-03 – GestorInicial.java
**Localização:** `configs/GestorInicial.java`

Componente responsável pela criação/inicialização do gestor padrão do sistema durante a inicialização da aplicação.

---

## IC-04 – CorsConfig.java
**Localização:** `configs/CorsConfig.java`

Configuração das políticas CORS utilizadas para permitir a comunicação entre frontend e backend.

---

## IC-05 – GlobalExceptionHandler.java
**Localização:** `configs/GlobalExceptionHandler.java`

Centraliza o tratamento de exceções da aplicação, padronizando as respostas de erro da API.

---

## IC-06 – TarefasDiarias.java
**Localização:** `configs/TarefasDiarias.java`

Responsável pelo agendamento e execução de tarefas automáticas periódicas do sistema.

---

## IC-07 – ConfiguracoesDeSeguranca.java
**Localização:** `configs/security/ConfiguracoesDeSeguranca.java`

Define as regras de segurança da aplicação:
- Rotas protegidas;
- Autenticação;
- Autorização;
- Filtros de segurança.

---

## IC-08 – FiltroDeSeguranca.java
**Localização:** `configs/security/FiltroDeSeguranca.java`

Filtro responsável pela validação dos tokens recebidos nas requisições.

---

## IC-09 – TokenService.java
**Localização:** `configs/security/TokenService.java`

Serviço responsável pela geração, validação e gerenciamento de tokens JWT.

---

## IC-10 – AutorizacaoService.java
**Localização:** `services/AutorizacaoService.java`

Implementa a lógica de autenticação dos usuários do sistema.

---

## Resumo dos ICs

| ID | Item de Configuração |
|----|---------------------|
| IC-01 | application.properties |
| IC-02 | ApiApplication.java |
| IC-03 | GestorInicial.java |
| IC-04 | CorsConfig.java |
| IC-05 | GlobalExceptionHandler.java |
| IC-06 | TarefasDiarias.java |
| IC-07 | ConfiguracoesDeSeguranca.java |
| IC-08 | FiltroDeSeguranca.java |
| IC-09 | TokenService.java |
| IC-10 | AutorizacaoService.java |