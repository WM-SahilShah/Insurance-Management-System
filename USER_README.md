# User README

This file explains the valid ways to run and use the Insurance Management System and documents the main API endpoints and auth flow.

## Quick start

- From project root (bash):
  - Build: `./mvnw package`
  - Run: `./mvnw spring-boot:run`
  - Run the jar: `java -jar target/*.jar`

- Using Docker / Compose (if you want containerized run):
  - `docker-compose up --build`

- Authentication (JWT)
  - To authenticate use the sign-in endpoint: `POST /login` (this returns a JWT in the `Authorization` response header).
  - Include the token in subsequent requests using the `Authorization` header, prefixing the token with `Bearer`:
  - `Authorization: Bearer <token>`
  - Registration: `POST /signup` allows creating a new client account.

- Public endpoints
  - `GET /welcome` : Simple welcome message.
  - `POST /signup` : Register a new client (body: `ClientDto`).
  - `POST /login` : Authenticate; successful sign-in results in an `Authorization` header containing a JWT.

- Authenticated API endpoints
  > Note: these endpoints require a valid `Authorization` header unless explicitly permitted.

  - Clients (`/api/clients/`)
    - `GET /api/clients/` — list all clients
    - `GET /api/clients/{id}` — get client by id
    - `GET /api/clients/email/?email=<email>` — get client by email
    - `PUT /api/clients/{id}` — update client (body: `ClientDto`)
    - `DELETE /api/clients/{id}` — delete client

  - Insurance Policies (`/api/policies/`)
    - `GET /api/policies/` — list all policies
    - `GET /api/policies/{id}` — get policy by id
    - `POST /api/policies/{ClientId}` — create a new policy for the client `ClientId` (body: `InsurancePolicyDto`)
    - `PUT /api/policies/{id}` — update policy (body: `InsurancePolicyDto`)
    - `DELETE /api/policies/{id}` — delete policy

  - Claims (`/api/claims/`)
    - `GET /api/claims/` — list all claims
    - `GET /api/claims/{id}` — get claim by id
    - `POST /api/claims/{id}` — create a new claim (path variable `id` is used by controller; body: `ClaimDto`)
    - `PUT /api/claims/{id}` — update claim (body: `Claim`)
    - `DELETE /api/claims/{id}` — delete claim

- Examples
  - Register a user (replace JSON body with your client info):

```bash
curl -X POST -H "Content-Type: application/json" \
  -d '{"firstName":"Jane","lastName":"Doe","email":"jane@example.com","password":"pass123"}' \
  http://localhost:8080/signup -i
```

- Sign in and use token in subsequent requests (example with curl):

```bash
# signIn (example: using basic auth or application login; check your signIn handler for expected payload)
curl -i -X POST http://localhost:8080/login -u jane@example.com:pass123

# copy token from `Authorization` response header and use in next request
curl -H "Authorization: Bearer <token>" http://localhost:8080/api/clients/
```

- Notes & useful tips
  - Paths and semantics are driven by controller classes in `src/main/java/com/wm/controller`.
  - For testing, using DTO builders (if added) or small helper factories for fixtures simplifies creating input JSON bodies.
