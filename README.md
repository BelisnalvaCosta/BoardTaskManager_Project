# Board Task Manager

Este é um projeto de **Gerenciador de Boards de Tarefas** desenvolvido em Java, utilizando SQLite como banco de dados. O objetivo é permitir a criação e gerenciamento de quadros (boards) com colunas e cartões (cards),
facilitando a organização de tarefas de forma visual e simples. [^1]

<img width="890" height="466" alt="image" src="https://github.com/user-attachments/assets/efeaddb9-fd0f-4ade-a3a1-fdfa8d8a5eba" />


## Funcionalidades

- Criar múltiplos boards (quadros) para diferentes projetos ou times.
- Adicionar colunas personalizadas em cada board (ex: Inicial, Pendente, Final, Cancelado).
- Criar, mover, bloquear, desbloquear e cancelar cards (tarefas) dentro das colunas.
- Visualizar detalhes de boards, colunas e cards.
- Interface de linha de comando (CLI) para interação.
- Interface web simples (HTML/CSS/JS) para visualização visual do board.

## Estrutura do Projeto

```
board.db                # Banco de dados SQLite
schema.sql              # Script SQL para criação das tabelas
pom.xml                 # Configuração do Maven
src/
  main/
    java/
      br/com/dio/board/
        config/         # Configurações de conexão e inicialização do banco
        dto/            # Objetos de transferência de dados
        entity/         # Entidades do domínio (Board, Card, etc)
        service/        # Lógica de negócio e acesso ao banco
        ui/             # Interface de linha de comando (menus)
        web/            # Interface web (HTML, CSS, JS)
```

## Como Executar

1. **Pré-requisitos:**  
   - Java 17 ou superior  
   - Maven

2. **Instale as dependências:**
   ```sh
   mvn clean install
   ```

3. **Execute o projeto:**
   ```sh
   mvn exec:java -Dexec.mainClass="br.com.dio.board.ui.MainMenu"
   ```
   Ou rode a classe `MainMenu` pela sua IDE.

4. **Banco de Dados:**  
   O banco será criado automaticamente na raiz do projeto (`board.db`). O script `schema.sql` define as tabelas necessárias.

5. **Interface Web:**  
   Abra o arquivo [`src/main/java/br/com/dio/board/web/index.html`](src/main/java/br/com/dio/board/web/index.html) no navegador para visualizar um board de exemplo (não conectado ao banco, apenas visual).

## Tecnologias Utilizadas

- **Java 17**
- **SQLite** (via JDBC)
- **Maven**
- **HTML, CSS, JavaScript** (para visualização web)

## Para quem é este projeto?

- **Iniciantes:**  
  Ótimo para aprender sobre CRUD, organização de código em camadas, JDBC e uso de banco de dados relacional.

- **Profissionais:**  
  Estrutura modular, fácil de expandir para REST API, integração com frameworks ou front-ends mais avançados.

## Contribuição

Sinta-se à vontade para abrir issues, sugerir melhorias ou enviar pull requests!

---

Feito com 💻 carinho por Belisnalva Costa

[^1]: Bootcamp Santander Back-end Java - professor José Luiz C. Junior (Dio).
