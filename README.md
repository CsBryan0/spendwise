
# Sistema de Controle de Gastos Mensais

Um sistema para controle de gastos mensais que permite registrar despesas, visualizar saldo, e gerar relatórios de gastos.

## Visão Geral

O projeto visa criar um sistema para controle financeiro pessoal, permitindo aos usuários registrar suas despesas, categorizá-las e gerar relatórios sobre os gastos realizados ao longo do mês.

## Instalação

### Requisitos

- Java 8 ou superior
- Maven
- Banco de dados (H2 para desenvolvimento)

### Instalação e Configuração

1. Clone este repositório:
   ```bash
	   git clone https://github.com/CsBryan0/spendwise.git
2. Navegue até o diretório do projeto:
	 ```bash
		cd nome-do-projeto
3. Execute a aplicação usando Maven:
	```bash
		mvn spring-boot:run
4. Configure o banco de dados no application.properties.
5. Veja o console console do h2-database para ter acesso as tabelas do banco de dados.
Acesso o console em: `http://localhost:8080/h2-console`
7. Fique a vontade para testar as rotas utilizando o software da sua preferência, como o Insomnia, o Postman ou outros.

## Uso
Após iniciar a aplicação, acesse `http://localhost:8080` para interagir com o sistema. 
