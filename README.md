# Dash Lat Net Backend

Backend da aplicação **Dash Lat Net**, responsável pelo monitoramento de conectividade e latência de múltiplos hosts de rede.

A aplicação fornece uma API REST desenvolvida com **Java 25 + Spring Boot**, executa os testes de ping, registra os resultados utilizando **Hibernate/JPA** e disponibiliza os dados para o frontend React.

---

## 📋 Funcionalidades

* Monitoramento de múltiplos hosts
* Execução de testes de ping
* Medição de latência em milissegundos
* Identificação de hosts alcançáveis e não alcançáveis
* Registro dos resultados no banco de dados
* Consulta dos logs de ping
* Consulta dos hosts monitorados
* API REST para integração com o frontend
* Integração com Hibernate/JPA
* Suporte a CORS para o frontend React
* Execução contínua do monitoramento no servidor

---

## 🖥️ Tecnologias utilizadas

* Java 25
* Spring Boot
* Spring Web
* Spring Data JPA
* Hibernate
* Maven
* Banco de dados relacional
* API REST

---

---

### Em meu Guithub [mateuslph](https://github.com/mateuslph) tenho o Front-end específico para encaixar nessa API sem a nescessidade da readaptação do código. Está pronto e funcionando, a seguir os links:
> Em React:
> #### Dash-Lat-Net-Front, disponível [AQUI](https://github.com/mateuslph/dash_lat_net_front)
> #### Dash-Lat-Frontend, disponível [AQUI](https://github.com/mateuslph/dash-lat-frontend)

> Em Python:
> #### Dasboardpy-Intermediario, disponível [AQUI](https://github.com/mateuslph/dasboardpy_intermediario)

---

# 🏗️ Arquitetura

A estrutura recomendada para o backend é:

```text
src/
└── main/
    │
    ├── java/
    │   └── com/
    │       └── dash_lat_net/
    │           │
    │           ├── DashLatNetApplication.java
    │           │
    │           ├── controller/
    │           │   └── PingController.java
    │           │
    │           ├── service/
    │           │   └── PingService.java
    │           │
    │           ├── repository/
    │           │   └── PingRepository.java
    │           │
    │           ├── entity/
    │           │   └── PingLog.java
    │           │
    │           ├── dto/
    │           │   └── PingResponseDTO.java
    │           │
    │           ├── config/
    │           │   └── CorsConfig.java
    │           │
    │           └── exception/
    │               └── ...
    │
    └── resources/
        │
        ├── application.properties
        └── application.yml
```

O frontend trabalha com uma resposta semelhante a:

```json
{
  "host": "google.com",
  "reachable": true,
  "latency": 20
}
```

---

## Configuração

Configurações gerais da aplicação ficam em:

```text
config/
```

Incluindo configurações relacionadas ao acesso do frontend à API.

---

# 🌐 API REST

A API utiliza como base:

```text
http://localhost:8080/api/ping
```

---

## GET `/api/ping/logs`

Retorna os registros de ping armazenados.

### Requisição

```http
GET http://localhost:8080/api/ping/logs
```

### Exemplo de resposta

```json
[
  {
    "host": "google.com",
    "reachable": true,
    "latency": 20
  },
  {
    "host": "8.8.8.8",
    "reachable": true,
    "latency": 20
  },
  {
    "host": "1.1.1.1",
    "reachable": true,
    "latency": 21
  },
  {
    "host": "208.67.220.220",
    "reachable": true,
    "latency": 29
  }
]
```

Essa é a principal rota utilizada pelo frontend para atualizar o Dashboard.

---

# 🌎 GET `/api/ping/hosts`

Retorna a lista de hosts monitorados.

### Requisição

```http
GET http://localhost:8080/api/ping/hosts
```

### Exemplo

```json
[
  "google.com",
  "8.8.8.8",
  "1.1.1.1",
  "208.67.220.220"
]
```

---

# 📡 GET `/api/ping/{host}`

Executa/consulta o processamento de ping para um host específico.

### Exemplo

```http
GET http://localhost:8080/api/ping/google.com
```

Ou:

```http
GET http://localhost:8080/api/ping/8.8.8.8
```

### Exemplo de resposta

```json
{
  "host": "google.com",
  "reachable": true,
  "latency": 20
}
```

---

# 🖧 Hosts monitorados

O backend pode monitorar múltiplos hosts.

No ambiente atual, os registros observados incluem:

```text
google.com
8.8.8.8
1.1.1.1
208.67.220.220
```

Exemplo dos registros produzidos pelo servidor:

```text
Host: google.com       | Latência: 20ms
Host: 8.8.8.8          | Latência: 20ms
Host: 1.1.1.1          | Latência: 21ms
Host: 208.67.220.220   | Latência: 29ms
```

O monitoramento é repetido e os resultados são registrados.

---

# 💾 Persistência

Os resultados dos testes de ping são armazenados utilizando:

```text
Spring Data JPA
        │
        ▼
Hibernate
        │
        ▼
Banco de dados
```

Os registros armazenados permitem que o frontend consulte o histórico através de:

```text
GET /api/ping/logs
```

---

# 🔄 Fluxo da aplicação

O funcionamento geral é:

```text
                    ┌──────────────────┐
                    │   Spring Boot    │
                    │     Java 25      │
                    └────────┬─────────┘
                             │
                             ▼
                     ┌───────────────┐
                     │ PingService   │
                     └───────┬───────┘
                             │
                    executa os pings
                             │
              ┌──────────────┼──────────────┐
              ▼              ▼              ▼
          google.com       8.8.8.8       1.1.1.1
              │              │              │
              └──────────────┼──────────────┘
                             │
                             ▼
                      PingRepository
                             │
                             ▼
                         Hibernate
                             │
                             ▼
                         Banco
                             │
                             ▼
                     /api/ping/logs
                             │
                             ▼
                         React
                             │
                             ▼
                       Dashboard
```

---

# 🌐 CORS

O frontend atual é executado pelo Vite em:

```text
http://localhost:5173
```

Por isso a API precisa permitir requisições provenientes dessa origem.

Exemplo utilizando o Controller:

```java
@CrossOrigin(
    origins = "http://localhost:5173"
)
```

# ⚙️ Configuração

A configuração principal da aplicação fica normalmente em:

```text
src/main/resources/application.properties
```

ou:

```text
src/main/resources/application.yml
```

A configuração pode conter parâmetros relacionados a:

* porta do servidor;
* banco de dados;
* Hibernate;
* JPA;
* logs;
* configurações da aplicação.

---

# 🔌 Porta do servidor

O backend é utilizado pelo frontend através da porta:

```text
8080
```

Portanto:

```text
http://localhost:8080
```

A API fica disponível em:

```text
http://localhost:8080/api/ping
```

---

# 🚀 Execução do projeto

## Pré-requisitos

Instale:

* JDK 25
* Maven
* Banco de dados utilizado pelo projeto

Verifique o Java:

```bash
java -version
```

O resultado deve indicar Java 25.

Verifique o Maven:

```bash
mvn -version
```

---

# 📦 Instalação

Clone o projeto:

```bash
git clone git@github.com:mateuslph/dash_lat_net_back.git
```

Entre no diretório:

```bash
cd dash_lat_net
```

---

# ▶️ Executando com Maven

Para iniciar o projeto:

```bash
mvn spring-boot:run
```

Alternativamente, gere o pacote:

```bash
mvn clean package
```

Depois execute o JAR:

```bash
java -jar target/<arquivo>.jar
```

---

# 🧪 Testando a API

Depois de iniciar o backend, teste:

```text
http://localhost:8080/api/ping/logs
```

Também:

```text
http://localhost:8080/api/ping/hosts
```

E um host específico:

```text
http://localhost:8080/api/ping/google.com
```

---

# 🖥️ Integração com o Frontend

O frontend React utiliza:

```text
http://localhost:8080/api/ping/logs
```

A aplicação consulta os dados periodicamente para atualizar o Dashboard.

Fluxo:

```text
Backend
   │
   │ GET /api/ping/logs
   ▼
React
   │
   ▼
usePingLogs()
   │
   ├── HostList
   ├── SummaryCards
   ├── LatencyChart
   ├── SuccessFailureChart
   ├── AverageLatencyChart
   ├── HostStatusTable
   └── PingHistoryTable
```

---

# 📊 Exemplo de monitoramento

O servidor pode gerar continuamente:

```text
Hibernate: Gravando log ->
Host: google.com | Latência: 20ms

Hibernate: Gravando log ->
Host: 8.8.8.8 | Latência: 20ms

Hibernate: Gravando log ->
Host: 1.1.1.1 | Latência: 21ms

Hibernate: Gravando log ->
Host: 208.67.220.220 | Latência: 29ms
```

O frontend transforma esses registros em:

* gráficos;
* indicadores;
* status dos hosts;
* histórico;
* média de latência.

---

# 📌 Status

**Em desenvolvimento.**

O backend atualmente fornece a infraestrutura REST para o monitoramento de múltiplos hosts e armazenamento dos resultados dos testes de ping.

---

## 👨‍💻 Projeto

**Dash Lat Net**

Backend desenvolvido utilizando:

```text
Java 25
Spring Boot
Spring Data JPA
Hibernate
Maven
REST API
```

Frontend relacionado:

```text
React
TypeScript
Vite
Material UI
Axios
Chart.js
```
