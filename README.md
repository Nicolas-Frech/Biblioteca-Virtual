# 📚 Software para Biblioteca Virtual

## 📌 Sobre o Projeto

Sistema Web para o gerenciamento de Biblioteca Virtual, permitindo o cadastro e a administração de **Livros** e **Autores**. Também é necessário fazer login e cadastro de **Usuário**, com dois perfis diferentes: **USER** e **ADMIN**. Este software segue caracteristicas dos principios **SOLID** e **Clean Architecture**, em processo de produção.

A aplicação é composta por:

- **Frontend:** HTML/CSS/JAVASCRIPT e Bootstrap 5
- **Backend:** Java com Spring Boot 3
- **Banco de Dados:** MySQL
- **Containerização:** Docker e Docker Compose

## 🚀 Tecnologias Utilizadas

Este software foi desenvolvido com as seguintes tecnologias:

- **Frontend:**

  - HTML/CSS/JAVASCRIPT
  - Bootstrap 5

- **Backend:**

  - Java
  - Spring Boot 3
  - Maven
  - JPA
  - Hibernate


- **Banco de Dados:**

  - MySQL

- **Outras Ferramentas:**

  - Git e GitHub
  - Imsomnia
  - Lombok
  - JWT
  - Spring Security

## 🔧 Funcionalidades

### 📖 Funcionalidades de Livro

- **Listagem de Livros** 📋

  - Exibe uma lista com todos os livros cadastrados.
  - Paginação para melhor visualização.

- **Buscar Livro por Título** 🔎

  - Permite pesquisar livros pelo Título.
  - Retorna detalhes como data de lançamento, sinopse e autor.
 
- **Buscar Livro por Gênero** 🔎

  - Permite pesquisar livros pelo Gênero.
  - Retorna detalhes como data de lançamento, sinopse e autor.

- **Cadastrar Livro** ➕ **(ADMIN)**

  - Cadastro de novos livros com informações detalhadas.
 
- **Reservar Livro** 📌

  - Permite reservar um livro.

- **Excluir Livro** 🗑️ **(ADMIN)**

  - Remoção de um livro do sistema.


### 👨‍💼  Funcionalidades de Autor

- **Cadastrar Autor** ➕ **(ADMIN)**

  - Cadastro de novos autores no sistema.

- **Excluir Autor** 🗑️ **(ADMIN)**

  - Remoção de um autor do sistema.
 

### 👨‍💼  Funcionalidades de Usuário

- **Cadastrar Usuário** ➕

  - Cadastro de novos usuários no sistema.

- **Login de Usuário** 🔑

  - Login de um usuário no sistema.
 
- **Mudar o perfil de um usuário** 🔄 **(ADMIN)**

  - Mudar o perfil de um usuário para ADMIN ou USER.

## 🤝 Contribuição

Contribuições são sempre bem-vindas! Para contribuir:

1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b minha-feature`)
3. Commit suas mudanças (`git commit -m 'Adiciona nova feature'`)
4. Faça um push para a branch (`git push origin minha-feature`)
5. Abra um Pull Request
