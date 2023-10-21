# Desenvolvimento de API Restful - Ecommerce


Este projeto foi idealizado para um dos Trabalhos do Grupo 2 da disciplina de Desenvolvimento de API Restful, Serratec - 2023.2 - Petrópolis, turma 12.

Neste trabalho, um projeto de API deve ser desenvolvido apresentar, ao menos:

- O modelo físico deverá ser gerado a partir das entidades da API (e não criado direta ente no SGBD) e será composto pelas seguintes entidades:

Categoria:
----------
- id_categoria (bigserial);
- descricao (varchar);
- nome (varchar);

Cliente:
--------
- id_cliente(bigserial);
- cpf (varchar);
- data_nascimento (date);
- email (varchar);
- nome_completo (varchar);
- telefone (varchar);
- id_endereco (int8);

Endereço:
-----
- numero (int4);
- id_endereco (bigserial);
- bairro (varchar);
- cep (varchar);
- cidade (varchar);
- complemento (varchar);
- rua (varchar);
- uf (varchar);

Item_pedido:
-----
- id_item_pedido (bigserial);
- percentual_desconto (numeric(38,2);
- preco_venda (numeric(38,2);
- quantidade (int4);
- valor_bruto (numeric(38,2);
- valor_liquido (numeric(38,2);;
- id_pedido (int8);
- id_produto (int8);

Pedido:
-----
- id_pedido (bigserial);
- data_entrega (timestamp(6));
- data_envio (timestamp(6));
- data_pedido (timestamp(6));
- status (varchar(255));
- valor_total (numeric(38,2));
- id_cliente (int8);

Produto:
-----
- id_produto (bigserial);
- data_cadastro (date);
- descricao (varchar(255));
- imagem (varchar(255));
- nome (varchar(255));
- qtd_estoque (int4);
- valor_unitario (int4);
- id_categoria (int8);


A respeito das relações entre as entidades, temos: Cada cliente só pode ter um endereço, o Cliente pode fazer um ou varios pedidos, cada pedido pode conter um ou varios itens de pedido, cada item de pedido é composto por um produto e em cada categoria pode conter um ou varios produtos.

## Funcionalidades

O projeto foi estruturado em pacotes de Entidades, Respositórios, Serviços e Controles a fim de permitir que as classes sejam acessadas da maneira correta e que as operações CRUD possam funcionar adequadamente.

Na criação do projeto foram utilizadas as configurações recomendadas, com 
- Versão 3.1.4 do Spring Boot; 
- Empacotamento do tipo Jar para o banco de dados; 
- Versão 17 do Java;
- Gerenciador de Dependências Maven; 
Cujas Dependências são:
- Spring Web;
- Spring Boot Dev Tools;
- Spring Data JPA;
- Postgres SQL Driver.
## Ferramentas Utilizadas

- [SpringToolSuite4](https://spring.io/);
- [GitHub](https://www.github.com/);
- [Trello](https://trello.com/);
- [Insomnia 8.0](https://insomnia.rest/);
- [DBeaver 23.2.2](https://dbeaver.io/);
- Java (17).
## Autores

- [GitHub - @Beatriz Barcelos](https://github.com/beabarcel);
- [GitHub - @Bruno Lima](https://www.github.com/brunolimaptr);
- [GitHub - @Emanuel Cardoso](https://www.github.com/ecard58);
- [GitHub - @Felipe Sutter](https://www.github.com/FelipeSutter);
- [GitHub - @Ícaro Nascimento](https://www.github.com/ikaro460);
- [GitHub - @Lucas Latsch](https://www.github.com/lucaslatsch).


