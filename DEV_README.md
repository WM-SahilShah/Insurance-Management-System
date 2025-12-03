# Developer README

This document explains the architecture of the Insurance Management System, how the code is organized, and where/how to apply the Builder pattern safely in this repository.

## Overview

- Spring Boot REST API for managing clients, insurance policies, and claims.
- Uses Spring Data JPA for persistence, ModelMapper for DTO/entity mapping, Lombok to reduce boilerplate, and JWT-based authentication implemented with servlet filters.

## High-level Architecture

- Layers and responsibilities:
  - Controller layer (`com.wm.controller`): exposes REST endpoints and handles HTTP request/response mapping.
  - Service layer (`com.wm.service`): business logic and orchestration.
  - Repository layer (`com.wm.repository`): Spring Data JPA interfaces that handle persistence.
  - Model layer (`com.wm.model`): JPA entities (domain objects).
  - DTO layer (`com.wm.dto`): shapes used at API boundaries to decouple internal models from external representation.
  - Config & Security (`com.wm.config`): application beans, security filter chain, and JWT filters.

## Key files and locations

- `src/main/java/com/wm/controller` — REST controllers (endpoints and request mappings).
- `src/main/java/com/wm/service` — service interfaces and implementations.
- `src/main/java/com/wm/repository` — Spring Data repositories.
- `src/main/java/com/wm/model` — JPA entities (`Client`, `InsurancePolicy`, `Claim`, `Address`).
- `src/main/java/com/wm/dto` — DTO classes used by controllers.
- `src/main/java/com/wm/config` — `AppConfig`, JWT generation/validation filters, and security constants.

## Security summary

- JWT flow implemented with two filters:
  - `JwtGenraterTokanFilter` — generates a JWT when authentication exists (configured to run after successful sign-in) and sets it in the `Authorization` response header.
  - `JwtTokanValideterFilter` — validates the incoming JWT on requests (reads `Authorization` header, parses token, populates SecurityContext).
- See `AppConfig#securityConfigrationChain` for the configured `SecurityFilterChain` and permitted paths.

## DTOs vs Entities

- Controllers accept and return DTOs (`com.wm.dto.*`) while services and repositories operate on entities in `com.wm.model`.
- This separation keeps the API contract stable and prevents JPA-specific behavior leaking into the REST layer.

## Mapping between DTOs and Entities

- The project defines a `ModelMapper` bean in `AppConfig`. Use this mapper in services to convert DTOs to entities and vice versa, e.g.:

```java
ModelMapper mapper = new ModelMapper();
InsurancePolicy policy = mapper.map(insurancePolicyDto, InsurancePolicy.class);
```

## Development tips

- Keep controllers thin: validate input, map to DTOs/entities, and delegate to services.
- Keep services transactional and orchestrating only. Persistence details should be in repositories.
- Write unit tests for service logic and integration tests for controllers.

## Run & debug

- Build and run with Maven wrapper from the project root (bash):

  ```bash
  ./mvnw clean package
  ./mvnw spring-boot:run
  ```

- Or run the packaged jar:

  ```bash
  java -jar target/*.jar
  ```
