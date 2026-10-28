# CamundaApp

## Описание проекта

`CamundaApp` — микросервис на Spring Boot 3.x, использующий Camunda 8 (движок Zeebe) для управления бизнес-процессами.
Приложение реализует CRUD-логику для пользователей и тикетов, а также демонстрирует запуск процессов из
REST-контроллеров.

### Ключевые особенности

- **Spring Boot 3.5.x** — быстрый старт, автоконфигурация, DevTools.
- **Camunda 8 (Zeebe)** — интеграция через `camunda-spring-boot-starter` (версия 8.9.19).
- **PostgreSQL 18** — база данных, подключаемая через Spring Data JPA.
- **Liquibase** — миграции схемы.
- **Lombok** — сокращение boilerplate-кода.
- **JUnit 5 + Mockito + Testcontainers** — модульные и интеграционные тесты.
- **Docker** — контейнеризация приложения и инфраструктуры.
- **Java 21+** — минимальная версия для сборки и запуска.

## Структура проекта

Проект построен по принципу **Clean Architecture** (слои «порты и адаптеры»): внешние зависимости не проникают во
внутренние слои. Каждый слой имеет свою ответственность и зависит только от слоёв, находящихся ближе к центру.

### Описание слоёв

| Слой                 | Назначение                                                                                     |
|----------------------|------------------------------------------------------------------------------------------------|
| **`presentation`**   | REST-интерфейс: контроллеры, DTO для API, глобальные обработчики ошибок.                       |
| **`application`**    | Бизнес-логика: use case'и, порты in/out, бизнес-DTO и мапперы.                                 |
| **`infrastructure`** | Внешние интеграции: адаптеры к репозиториям и Camunda, JPA-сущности/репозитории, конфигурация. |
| **`domain`**         | Чистые доменные модели (aggregate roots + value objects), независимые от фреймворков.          |

### Дерево структуры

```
src/main/java/ru/lakeevda/camundaapp/
├── CamundaAppApplication.java
├── application/
│   ├── dto/                — бизнес-запросы и ответы (CreateRequest, GetResponse…)
│   ├── mapper/             — маппинг domain ↔ application DTO
│   ├── port/               — порты
│   │   ├── in/             — вводные порты
│   │   │   └── usecase/    — порты use case'ов (TicketUseCase, UserUseCase)
│   │   └── out/            — выводные порты
│   │       ├── process/    — порты для взаимодействия с бизнес-процессами
│   │       └── repository/ — порты репозиториев
│   └── usecase/            — реализации use case'ов
├── domain/model/           — модели бизнес-сущностей
│   ├── ticket/             — Ticket (aggregate root) + value objects
│   └── user/               — User (aggregate root) + value objects
├── infrastructure/
│   ├── adapter/            — адаптеры
│   │   ├── camunda/        — адаптеры Camunda
│   │   │   ├── process/    — реализация порта для взаимодействия с Camunda
│   │   │   └── worker/     — worker'ы для задач процессов Camunda
│   │   └── repository/     — реализации портов репозиториев
│   ├── config/             — конфигурации
│   ├── dto/camunda/ticket/ — адаптация данных Camunda в домены
│   └── persistence/
│       ├── entity/         — JPA-сущности (TicketEntity, UserEntity)
│       ├── mapper/         — маппинг сущностей ↔ DTO
│       └── repository/     — Spring Data JPA репозитории
└── presentation/
    ├── dto/                — запросы и ответы REST-контроллеров
    ├── mapper/             — конвертация domain ↔ REST DTO
    └── rest/controller/    — REST-контроллеры (User, Ticket, Camunda, Advice)

src/main/resources/         — конфигурации Spring, Liquibase миграции БД, BPMN-схемы и формы

src/test/java/ru/lakeevda/camundaapp/  — тесты (дублируют структуру main)
```

## Сборка и запуск

### Требования

- **Java 21+**
- **Maven 3.9+**
- **Docker** (для запуска через docker-compose)

### Maven

```bash
./mvnw clean package
```

Для локального запуска без Docker создайте схему БД — Liquibase не создаёт её автоматически:

```sql
CREATE SCHEMA IF NOT EXISTS camunda_app;
```

### Docker Compose

В корне проекта находится `docker-compose.yaml`, который запускает:

- PostgreSQL (версия 18)
- Camunda 8 (Zeebe Broker) — движок бизнес-процессов
- Приложение в контейнере

```bash
docker compose up --build
```

После запуска REST-API доступно по адресу `http://localhost:8080/api`.

## Тесты

### Модульные

```bash
./mvnw test
```

Модульные тесты используют **Mockito** для изоляции бизнес-логики от внешних зависимостей (БД, Camunda). Покрывают
домен, use case'и и адаптеры репозиториев.

### Интеграционные

```bash
./mvnw verify
```

Интеграционные тесты (`*IntegrationTest`) запускаются на фазе `verify` через **Testcontainers** и проверяют
взаимодействие с PostgreSQL и Camunda 8. Для их запуска нужен Docker.

## Конфигурация

Базовая конфигурация — `src/main/resources/application.properties` (локальный запуск: БД на `localhost:5434`,
Zeebe на `localhost:26500`/`localhost:8082`).

При запуске через `docker-compose.yaml` адреса БД и Zeebe переопределяются переменными окружения контейнера
`camunda-app`: `SPRING_DATASOURCE_URL`, `CAMUNDA_GRPC_ADDRESS`, `CAMUNDA_REST_ADDRESS`. Отдельный профиль не используется.

Схема `camunda_app` создаётся при первой инициализации контейнера `postgres` (скрипт
`db/changelog/baseline/init/init.sql`) и задана как схема по умолчанию через `search_path`.

## BPMN-процессы

Процессы размещены в `src/main/resources/processes/bpmn`. В примере есть процесс `createTicketProcess.bpmn`, который
демонстрирует создание тикета через Camunda 8.

## REST API

| Метод    | Путь                      | Назначение                                  |
|----------|---------------------------|---------------------------------------------|
| `GET`    | `/api/users?email=...`    | Получить пользователя по email              |
| `POST`   | `/api/users`              | Создать пользователя                        |
| `GET`    | `/api/tickets?userId=...` | Получить тикеты пользователя                |
| `DELETE` | `/api/tickets/{ticketId}` | Удалить тикет                               |
| `POST`   | `/api/process/start`      | Запустить BPMN-процесс по `processId`       |
