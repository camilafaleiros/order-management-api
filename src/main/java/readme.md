# API Pedidos (Spring Boot • In-Memory)

API REST para gerenciar **Clientes**, **Produtos** e **Pedidos** (com **Itens**).
> **Sem banco de dados**: os dados ficam em **memória** enquanto a aplicação está rodando.

---

## Sumário
- [O que a aplicação faz](#o-que-a-aplicação-faz)
- [Como tudo se conecta](#como-tudo-se-conecta)
- [Requisitos](#requisitos)
- [Como executar](#como-executar)
- [Como testar com Insomnia](#como-testar-com-insomnia)
- [Endpoints](#endpoints)
    - [Health](#health)
    - [Clientes](#clientes)
    - [Produtos](#produtos)
    - [Pedidos](#pedidos)
    - [Itens do Pedido](#itens-do-pedido)
- [Erros (padrão de resposta)](#erros-padrão-de-resposta)
- [Estrutura do projeto](#estrutura-do-projeto)
- [Notas de desenvolvimento](#notas-de-desenvolvimento)

---

## O que a aplicação faz
- **Clientes**: cria, lista (com filtros), detalha, atualiza, apaga.
- **Produtos**: cria, lista (com filtros), detalha, atualiza, ajusta estoque, apaga.
- **Pedidos**: cria, lista (com filtros), detalha, atualiza, altera status, apaga.
- **Itens do Pedido**: lista, adiciona e remove.
- **Paginação** nas listagens: o **body** retorna **apenas a lista**; metadados vêm nos **headers**:
    - `X-Total-Count`, `X-Total-Pages`, `X-Page-Number`, `X-Page-Size`.

> Os IDs são gerados automaticamente. Reiniciar a aplicação **apaga os dados** (por ser in-memory).

---

## Como tudo se conecta
```
Cliente HTTP (Insomnia) → Controller → Service → Repository → Memória (Map)
                                 ↑             ↕
                               DTOs          Modelos
```
- **Controller**: recebe a requisição HTTP e devolve a resposta.
- **Service**: regras de negócio (ex.: calcular total do pedido, validar status).
- **Repository (in-memory)**: guarda/busca dados em `Map`/listas.
- **DTOs**: objetos usados para **entrada/saída** da API.
- **Modelos**: objetos do **domínio** (Cliente, Produto, Pedido, Item).

---

## Requisitos
- **Java 17+**
- **Maven 3.9+**
- (Opcional) **Insomnia** para testes

---

## Como executar
```bash
mvn clean compile
mvn spring-boot:run
```
Aplicação: **http://localhost:8080**

> JDK 21+: para silenciar aviso do Tomcat (opcional):  
> `mvn spring-boot:run -Dspring-boot.run.jvmArguments="--enable-native-access=ALL-UNNAMED"`

---

## Como testar com Insomnia

1. Abra o **Insomnia** → **Import/Export** → **Import Data** → **From File**.
2. Importe a coleção **clean** (URLs curtas + Query configurada):  
   **insomnia-api-pedidos-clean.json**
3. Em **Environment**, confirme:
   ```json
   { "base_url": "http://localhost:8080" }
   ```
4. **Importante**: nas listagens, use a aba **Query** (não cole parâmetros na URL).
5. As listagens retornam **só a lista** no **body**; a paginação está nos **headers**.

> Erros comuns:
> - Colocar o **nome** da request dentro da URL. A URL deve conter **apenas** o endereço (ex.: `http://localhost:8080/clientes`).
> - Achar que filtros são obrigatórios. **Não são**: preencha apenas se desejar.

---

## Endpoints
> Use `Content-Type: application/json` em **POST/PUT/PATCH**.

### Health
- **GET /** — checagem simples.
- **GET /ping** — retorna `"pong"`.

---

### Clientes
- **GET /clientes** — **Query** (opcional): `page`, `size`, `nome`, `email`, `cpfCnpj`  
  **Body (resposta)**: `[]` | `[ { ...ClienteDTO... } ]`  
  **Headers**: `X-Total-Count`, `X-Total-Pages`, `X-Page-Number`, `X-Page-Size`
- **POST /clientes**
  ```json
  { "nome": "Ana Silva", "email": "ana@example.com", "cpfCnpj": "12345678901" }
  ```
- **GET /clientes/{id}**
- **PUT /clientes/{id}**
  ```json
  { "nome": "Ana Silva Atualizada", "email": "ana.silva@example.com", "cpfCnpj": "12345678901" }
  ```
- **DELETE /clientes/{id}**

---

### Produtos
- **GET /produtos** — **Query** (opcional): `page`, `size`, `nome`, `sku`, `ativo`, `minPreco`, `maxPreco`  
  **Body (resposta)**: `[]` | `[ { ...ProdutoDTO... } ]`  
  **Headers**: paginação (mesmos 4 headers)
- **POST /produtos**
  ```json
  { "nome": "Camiseta", "sku": "CAM-001", "preco": 59.90, "estoque": 10, "ativo": true }
  ```
- **GET /produtos/{id}**
- **PUT /produtos/{id}**
  ```json
  { "nome": "Camiseta Premium", "sku": "CAM-001", "preco": 79.90, "estoque": 15, "ativo": true }
  ```
- **PATCH /produtos/{id}/estoque**
  ```json
  { "delta": 5 }
  ```
- **DELETE /produtos/{id}**

---

### Pedidos
- **GET /pedidos** — **Query** (opcional): `page`, `size`, `clienteId`, `status`  
  **Body (resposta)**: `[]` | `[ { ...PedidoDTO... } ]`  
  **Headers**: paginação
- **POST /pedidos**
  ```json
  {
    "clienteId": 1,
    "itens": [ { "produtoId": 1, "quantidade": 2 } ],
    "observacoes": "Entregar à tarde"
  }
  ```
- **GET /pedidos/{id}**
- **PUT /pedidos/{id}**
  ```json
  {
    "clienteId": 1,
    "itens": [ { "produtoId": 1, "quantidade": 2 } ],
    "observacoes": "Observação atualizada"
  }
  ```
- **PATCH /pedidos/{id}/status**
  ```json
  { "status": "PAGO" }
  ```
  **Status válidos**: `ABERTO`, `PAGO`, `ENVIADO`, `ENTREGUE`, `CANCELADO`
- **DELETE /pedidos/{id}**

---

### Itens do Pedido
- **GET /pedidos/{id}/itens**
- **POST /pedidos/{id}/itens**
  ```json
  { "produtoId": 1, "quantidade": 1 }
  ```
- **DELETE /pedidos/{id}/itens/{itemId}**

---

## Erros (padrão de resposta)
- **400** — payload inválido / validação.
- **404** — recurso não encontrado.

**Exemplo:**
```json
{
  "timestamp": "2025-09-22T19:22:33Z",
  "status": 404,
  "error": "Not Found",
  "message": "Cliente não encontrado",
  "path": "/clientes/9999"
}
```

---

## Estrutura do projeto
```
src/
└─ main/
   ├─ java/com/example/api_pedidos/
   │  ├─ controller/       # Endpoints HTTP (Clientes, Produtos, Pedidos)
   │  ├─ service/          # Regras de negócio (interfaces + impl)
   │  ├─ repository/       # Repositórios In-Memory
   │  ├─ dto/              # DTOs (entrada/saída/patch)
   │  ├─ model/            # Domínio (Cliente, Produto, Pedido, Item, Status)
   │  ├─ exception/        # NotFoundException + Handler global
   │  ├─ util/             # PaginationUtil (headers de paginação)
   │  └─ ApiPedidosApplication.java
   └─ resources/
      └─ application.properties
```

---

## Notas de desenvolvimento
- **Paginação**: use `page` e `size` na **Query** (opcionais). O body retorna só a lista; headers trazem os números.
- **In-memory**: ideal para testes; ao reiniciar, os dados são limpos.
- **Preço de item** no pedido é **mock** no service (ex.: `10.0`) para facilitar o fluxo.
