# CamundaApp

## Описание проекта

`CamundaApp` — микросервис на Spring Boot 3.x, использующий Camunda 8 (движок Zeebe) для управления бизнес-процессами.
Приложение реализует CRUD-логику для пользователей и тикетов, а также демонстрирует запуск процессов из
REST-контроллеров.

### Ключевые особенности

- **Spring Boot 3.5.x** — быстрый старт, автоконфигурация, DevTools.
- **Camunda 8 (Zeebe)** — интеграция через `camunda-spring-boot-starter` (версия 8.9.19).
- **PostgreSQL 15** — база данных, подключаемая через Spring Data JPA.
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

Модульные тесты используют **Mockito** для изоляции бизнес-логики от внешних зависимостей (БД, Camunda). Покрывают use
case'и и контроллеры.

### Интеграционные

```bash
./mvnw verify -Pintegration-tests
```

Интеграционные тесты запускаются через **Testcontainers** и проверяют взаимодействие с PostgreSQL и Camunda 8.

## Конфигурация

Файлы конфигурации находятся в `src/main/resources`:

- `application.properties` — общая конфигурация Spring.
- `application-docker.properties` — настройки для Docker-среды (путь к БД, адрес Zeebe).

Для запуска с Docker-профилем:

```bash
./mvnw spring-boot:run -Dspring.profiles.active=docker
```

## BPMN-процессы

Процессы размещены в `src/main/resources/processes/bpmn`. В примере есть процесс `createTicketProcess.bpmn`, который
демонстрирует создание тикета через Camunda 8.

## Лицензия

MIT License — см. файл `LICENSE`.
