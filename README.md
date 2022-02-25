## API TODO-LIST API

RESTFUL API simples em Java que armazena e atualiza tarefas (TODO-LIST API) em Spring Framework
# Requisitos

Build, Execution, Deployment

- [JDK 11](https://www.oracle.com/br/java/technologies/javase/jdk11-archive-downloads.html)
- [Maven 3.8.1](https://maven.apache.org)

# Algumas das tecnologias utilizadas nessa API
- [Spring Framework 2.5.7](https://spring.io/)
- [Lombok 1.18.22](https://projectlombok.org/)
- [H2 Database 2.0.202](https://www.h2database.com/)
- [Flayway](https://flywaydb.org/)
- [Swagger 2.9.2](https://swagger.io/)
- [Jacoco 0.8.6](https://www.jacoco.org/)
- [Sonarqube 3.9.0.2155](https://www.sonarqube.org/)

# Rodando a aplicação localmente

Para execução local você pode estar utilizando a sua IDE de preferência executando a classe Main do projeto:

 `br.com.gels.seletivo.itau.ControleTarefaApiApplication`

Ou usar o [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html):

```shell
mvn spring-boot:run
```

# Deployment em Containers

A API foi desenvolvida com Spring Boot que por sua vez embute no build um Servlet Container que a deixa pronta para ser "deployada" em um Docker Container.
Na sua pasta principal do projeto estão disponíveis os arquivos Dockerfile e docker-compose.yml, mas antes de executa-los, certifique-se que ter na máquina o Docker em execução e execute no terminal o seguinte comando:

```shell
docker-compose up -d --build
```

# Usuários

Ao rodar a aplicaçõa será criado 2 usuários no banco, são eles:

- admin@gels.com.br - Super usuário
- user.comum@gels.com.br

Todos têm a mesma senha:

- ``selecao@2022!`` 

# Documentação

Swagger da API:

```shell 
http://localhost:8080/swagger-ui.html#/
```

Infomações da API:

```shell
http://localhost:8080/actuator/info
```

Monitoramento da Saúde dos componentes da API:

```shell
http://localhost:8080/actuator/health
```

Indicadores de performance da API

```shell
http://localhost:8080/actuator/metrics

http://localhost:8080/actuator/logfile 

http://localhost:8080/actuator/flyway

http://localhost:8080/actuator/loggers

http://localhost:8080/actuator/beans

http://localhost:8080/actuator/conditions

http://localhost:8080/actuator/configprops
```

**AUTH TOKEN**
Todas as chamadas a API são autenticadas, então para começar a utiliza-las será necessário inicialmente realizar a autenticação passando usuario e senha e assim receber o token que deverá ser informados nas demais requisições:

**Request**

```shell
curl --location --request POST 'http://localhost:8080/oauth/token?grant_type=password&username=admin@gels.com.br&password=selecao@2022!' 
--header 'Authorization: Basic c2VsZWNAbyZ0QHU6MjgwMSNJdEB1IUAkU2VjcmV0UGFzcw=='
--header 'Accept: application/json;charset=UTF-8'
```

**Response**

```shell
{
    "access_token": "token_recebido",
    "token_type": "bearer",
    "expires_in": 1799,
    "scope": "read write",
    "jti": "ed51e5fa-7f06-476c-bd69-fedb6a43f67b"
}
```

**TAREFA - INCLUIR**

Para incluir uma nova tarefa, pegue o token devolvido na requisição de autenticação e passe no header como um Bearer Token juntamente com os dados que deseja incluir no body da requisição.

```shell
curl --location 
--request POST 'http://localhost:8080/todo-list' 
--header 'Authorization: Bearer token_recebido' 
--header 'Content-Type: application/json' 
--data-raw '{
    "descricao": "teste 1",
    "resumo": "teste 1",    
    "status": "peding",
    "dataCadastro": null,
    "dataAtualizacao": null
}'
```

**Response**

```shell
{
    "id": 1,
    "usuario": {
        "id": 1,
        "login": "admin@gels.com.br"
    },
    "resumo": "teste",
    "descricao": "teste",
    "status": "peding",
    "dataCadastro": "2022-02-24T20:14:13",
    "dataAtualizacao": null
}
```

**TAREFA - ATUALIZAR**

Para alterar/atualizar uma tarefa existente, pegue o token devolvido na requisição de autenticação e passe no header como um Bearer Token juntamente com todos os dados da tarefa que deseja alterar mesmo aqueles que não sofrerão alterações no body da requisição além de passar o id da tarefa como parâmetro da requisição.

```shell
curl --location 
--request PUT 'http://localhost:8080/todo-list/1' 
--header 'Authorization: Bearer token_recebido' 
--header 'Content-Type: application/json' 
--data-raw '{
    "descricao": "teste alterando",
    "resumo": "teste alterando",    
    "status": "complete",
    "dataCadastro": "2021-12-05T16:39:43",
    "dataAtualizacao": null
}'
```

**Response**

```shell
{
    "id": 1,
    "usuario": {
        "id": 1,
        "email": "admin@gels.com.br"
    },
    "resumo": "teste alteração",
    "descricao": "teste alteração",
    "status": "complete",
    "dataCadastro": "2022-02-24T20:14:13",
    "dataAtualizacao": "2022-02-24T20:36:26"
}
```

**TAREFA - Excluir**

Para excluir uma tarefa existente, pegue o token devolvido na requisição de autenticação e passe no header como um Bearer Token juntamente o id da tarefa como parâmetro da requisição.

```shell
curl --location 
--request DELETE 'http://localhost:8080/todo-list/1' 
--header 'Authorization: Bearer token_recebido'
```

**Response**

```shell
204 No Content
```

**TAREFA - CONSULTAR**

Para consultar/listar as tarefas existentes, pegue o token devolvido na requisição de autenticação e passe no header como um Bearer Token juntamente com o status desejado, se o status for informado vazio retornará todo. Caso o usuário informado na requisição seja um Super-Usuário será retonar uma lista geral de todas as tarefas independente de usuário ordenada pelas tarefas com o status 'pendding' primeiro. Caso seja informado um usuário comum, somente serão listados suas respectivas tarefas também ordenadas pelo status 'pendding' primeiro.

```shell
curl --location 
--request GET 'http://localhost:8080/todo-list?status=pending' 
--header 'Authorization: Bearer token_recebido'
```

**Response**

```shell
[
    {
        "id": 2,
        "usuario": {
            "id": 1,
            "email": "admin@gels.com.br"
        },
        "resumo": "Eu criando outra tarefa",
        "descricao": "Eu criando outra tarefa",
        "status": "peding",
        "dataCadastro": "2022-02-24T21:10:02",
        "dataAtualizacao": null
    },
    {
        "id": 4,
        "usuario": {
            "id": 2,
            "email": "user.comum@gels.com.br"
        },
        "resumo": "Uma vez mais",
        "descricao": "Uma vez mais",
        "status": "peding",
        "dataCadastro": "2022-02-24T21:13:32",
        "dataAtualizacao": null
    }
]
```

# Outras informações

A aplicação usa um banco em memória H2 e o Flyway para controle do versionamento dos scripts e criação do banco de dados além de fazer as inserções iniciais.

```shell
http://localhost:8080/h2-console
```

- Usuário: ``itau``
- Senha: ``todo``

**POSTMAN**

Está disponível na pasta raiz do projeto um arquivo collection do postman que pode ser importado no mesmo a fim de ajudar na execução da API.

```shell
TODO_LIST.postman_collection.json
```

# Code Coverage e Testes unitários (JUnit)

A API está pronta para ser analisada através de um code review com o Sonarqube usando a ferramenta de cobertura Jacoco.
Porém não foi implementado nenhum teste unitário e nem atingido uma cobertura de 80% como solicitado.
	
