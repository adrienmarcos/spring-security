# 🚀 Social Media API - Study Project

Esta é uma API RESTful desenvolvida para fins de estudo, simulando as funcionalidades principais de uma rede social (estilo Twitter). O foco principal deste projeto foi a implementação de segurança com **Spring Security**, controle de permissões por **Roles** e a orquestração do ambiente via **Docker**.

## 🛠️ Tecnologias Utilizadas

* **Java 17**
* **Spring Boot 3**
* **Spring Security** (Autenticação e Autorização)
* **Spring Data JPA**
* **MySQL** (Banco de dados relacional)
* **Docker & Docker Compose** (Containerização)

## 📌 Funcionalidades

* **Gestão de Usuários:** Cadastro e autenticação.
* **Tweets:** Criação de conteúdos vinculados ao usuário.
* **Feed:** Listagem de tweets postados na plataforma.
* **Segurança Avançada:** * Bloqueio de rotas sensíveis.
    * Diferenciação de permissões por funções (ex: `ROLE_USER`, `ROLE_ADMIN`).

---

## 🔒 Segurança (Spring Security)

O projeto demonstra como proteger endpoints baseando-se no perfil do usuário:
- **Público:** Cadastro e Login.
- **Privado (USER):** Criar tweets e visualizar o feed.
- **Administrativo (ADMIN):** Acesso a rotas de gerenciamento e moderação.

---

## 🚀 Como Executar o Projeto

### Pré-requisitos
* Docker e Docker Compose instalados.

### Passos
1. Clone este repositório:
   ```bash
   git clone [https://github.com/seu-usuario/nome-do-seu-repositorio.git](https://github.com/seu-usuario/nome-do-seu-repositorio.git)
