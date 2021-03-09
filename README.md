# Mercado-Sallas

Sistema em Spring Boot MVC que possibilita realizar pedidos de produtos para um determinado cliente.

[![Spring version](https://img.shields.io/badge/spring-2.3-green?logo=spring)](https://spring.io)

## Overview clientes

Um cliente deve ter um email e um cpf únicos e ao menos um telefone para realizar o cadastro.

Url: http://localhost:8080

### CRUD Cliente (/clientes)

#### POST:

Descrição: Cadastra um novo cliente.

Uri: /clientes

Request Body:

```json
{
  "nome": "João",
  "sobrenome": "Sallas",
  "cpf": "35051288057",
  "data_nascimento": "01/01/1995",
  "email": "email@ms.com",
  "telefones": [
    {
      "ddd": "11",
      "numero": "999999999",
      "tipo": "Celular"
    }
  ]
}
```

Response:
201 Created.

```json
{
  "id": "8039af69-8093-4ac4-b60a-aafbad485385",
  "nome": "João",
  "sobrenome": "Sallas",
  "cpf": "35051288057",
  "email": "email@ms.com",
  "data_nascimento": "01/01/1995",
  "data_cadastro": "21/02/2021",
  "telefones": [
    {
      "id": 7,
      "ddd": "11",
      "numero": "999999999",
      "tipo": "Celular"
    }
  ]
}
```

#### GET:

Uri: /clientes

Descrição: Lista todos os clientes cadastrados.

Request Body: Vazio

Response: 200 OK.

```json
[
  {
    "id": "e7734ead-0ca1-4f2c-9278-c86d7c86325a",
    "nome": "João",
    "sobrenome": "Sallas",
    "cpf": "35051288057",
    "email": "email@ms.com",
    "data_nascimento": "01/01/1995",
    "data_cadastro": "21/02/2021",
    "telefones": [
      {
        "id": 1,
        "ddd": "11",
        "numero": "999999999",
        "tipo": "Celular"
      }
    ]
  }
]
```

#### GET:

Uri: /clientes/{id}

Descrição: Obtem cliente cadastrado pelo id.

Request Body: Vazio

Response: 200 OK.

```json
{
  "id": "8039af69-8093-4ac4-b60a-aafbad485385",
  "nome": "João",
  "sobrenome": "Sallas",
  "cpf": "35051288057",
  "email": "email@ms.com",
  "data_nascimento": "01/01/1995",
  "data_cadastro": "21/02/2021",
  "telefones": [
    {
      "id": 7,
      "ddd": "11",
      "numero": "999999999",
      "tipo": "Celular"
    }
  ]
}
```

#### GET:

Descrição: Obtem cliente cadastrado pelo cpf.

Uri: /clientes/cpf/{cpf}

Request Body: Vazio

Response: 200 OK.

```json
{
  "id": "8039af69-8093-4ac4-b60a-aafbad485385",
  "nome": "João",
  "sobrenome": "Sallas",
  "cpf": "35051288057",
  "email": "email@ms.com",
  "data_nascimento": "01/01/1995",
  "data_cadastro": "21/02/2021",
  "telefones": [
    {
      "id": 7,
      "ddd": "11",
      "numero": "999999999",
      "tipo": "Celular"
    }
  ]
}
```

#### PUT:

Uri: /clientes/{id}

Descrição: Altera dados do cliente cadastrado.

Request Body:

```json
{
  "nome": "João",
  "sobrenome": "Sallas",
  "data_nascimento": "02/02/1996"
}
```

Response: 200 OK.

```json
{
  "id": "e7734ead-0ca1-4f2c-9278-c86d7c86325a",
  "nome": "João",
  "sobrenome": "Sallas",
  "cpf": "35051288057",
  "email": "email@ms.com",
  "data_nascimento": "02/02/1996",
  "data_cadastro": "21/02/2021",
  "telefones": [
    {
      "id": 1,
      "ddd": "11",
      "numero": "999999999",
      "tipo": "Celular"
    }
  ]
}
```

#### DELETE:

Uri: /clientes/{id}

Descrição: Deleta o cliente do sistema pelo id.

Request Body: Vazio

Response: 204 No Content.


### CRUD Telefone (/clientes/{idCliente}/telefones)
Cada cliente pode ter no minímo um telefone cadastrado e no máximo cinco telefones.

#### POST:

Descrição: Cadastra um novo telefone para o cliente.

Uri: /clientes/{idCliente}/telefones

Request Body:

```json
{
  "ddd": "11",
  "numero": "999999999",
  "tipo": "Trabalho"
}
```

Response:
201 Created.

```json
{
  "id": 2,
  "ddd": "11",
  "numero": "999999999",
  "tipo": "Trabalho"
}
```

#### GET:

Uri: /clientes/{idCliente}/telefones

Descrição: Lista todos os telefones cadastrados do cliente.

Request Body: Vazio

Response: 200 OK.

```json
[
  {
    "id": 1,
    "ddd": "11",
    "numero": "999999999",
    "tipo": "Celular"
  },
  {
    "id": 2,
    "ddd": "11",
    "numero": "999999999",
    "tipo": "Celular"
  }
]
```
#### GET:

Uri: /clientes/{idCliente}/telefones/{idTelefone}

Descrição: Obtem o telefone do cliente pelo id do telefone.

Request Body: Vazio

Response: 200 OK.

```json
{
  "id": 2,
  "ddd": "11",
  "numero": "999999999",
  "tipo": "Celular"
}
```

#### PUT:

Uri: /clientes/{idCliente}/telefones/{idTelefone}

Descrição: Altera o telefone do cliente pelo id do telefone.

Request Body: 

```json
{
  "ddd": "11",
  "numero": "222222222",
  "tipo": "Residencial"
}
```

Response: 200 OK.

```json
{
  "id": 2,
  "ddd": "11",
  "numero": "222222222",
  "tipo": "Residencial"
}
```

#### DELETE:

Uri: /clientes/{idCliente}/telefones/{idTelefone}

Descrição: Deleta o telefone do cliente pelo id do telefone.

Request Body: Vazio

Response: 204 No Content.
