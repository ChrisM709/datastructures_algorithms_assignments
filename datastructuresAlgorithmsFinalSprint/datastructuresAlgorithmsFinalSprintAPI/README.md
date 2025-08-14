# Binary Search Tree API (Backend)

A small Spring Boot API your React app can call to:
- take a comma‑separated list of numbers,
- build a Binary Search Tree (BST),
- save the result, and
- fetch previous trees.

> This README matches your current code (no “balanced” feature).

---

## 1) What’s in here

```
src/main/java/org/backendapi
├─ TreeApiApplication.java              # app entrypoint & package scanning
├─ controller/TreeController.java       # /api/trees endpoints
├─ service/TreeService.java             # build BST, serialize, persist
├─ repository/TreeResultRepository.java # Spring Data JPA repository
├─ model/TreeResult.java                # DB entity (input + treeJson + createdAt)
├─ dto/TreeRequest.java                 # { numbers }
├─ dto/TreeResponse.java                # { id, numbers, tree }
└─ util/
   ├─ BinarySearchTree.java             # insert() logic
   └─ TreeNode.java                     # { value, left, right }
```

The **React app** runs separately and calls these endpoints.

---

## 2) Quick start

### Prereqs
- Java 21+ installed
- Maven available (`mvn`) or the wrapper (`mvnw.cmd` on Windows / `./mvnw` on macOS/Linux)

### Run
```bash
# Windows (PowerShell)
mvnw.cmd spring-boot:run

# macOS/Linux
./mvnw spring-boot:run
```
You should see: **Tomcat started on port 8080**.

---

## 3) API you’ll call from React

Base URL: `http://localhost:8080/api/trees`

### Create / process a tree
**POST** `/api/trees`  
Body:
```json
{ "numbers": "4,2,8,3" }
```
Notes:
- `numbers` = comma‑separated integers (spaces OK).

Response (example):
```json
{
  "id": 1,
  "numbers": "4,2,8,3",
  "tree": {
    "value": 4,
    "left": { "value": 2, "left": null, "right": { "value": 3, "left": null, "right": null } },
    "right": { "value": 8, "left": null, "right": null }
  }
}
```

**cURL**
```bash
curl -X POST http://localhost:8080/api/trees \
  -H "Content-Type: application/json" \
  -d '{"numbers":"4,2,8,3"}'
```

### List previous trees
**GET** `/api/trees`

**cURL**
```bash
curl http://localhost:8080/api/trees
```

### Get one by id (optional)
**GET** `/api/trees/{id}`

---

## 4) How it works

1. The controller receives your JSON (`numbers`).  
2. The service:
   - parses the CSV into a `List<Integer>`,
   - inserts each number into a BST (duplicates effectively ignored),
   - converts the tree to JSON `{value,left,right}`,
   - saves `{inputNumbers, treeJson, createdAt}` to H2, and
   - returns the JSON to your client.
3. The repository is a Spring Data `JpaRepository`—no SQL to write.

---

## 5) React usage example

```js
// Create
const create = async (numbersCsv) => {
  const r = await fetch("/api/trees", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ numbers: numbersCsv })
  });
  if (!r.ok) throw new Error(await r.text());
  return r.json();
};

// List previous
const list = async () => fetch("/api/trees").then(r => r.json());
```

**Dev without CORS headaches:** use a Vite proxy that forwards `/api` to `http://localhost:8080`,
or allow CORS on the controller for `http://localhost:5173`.

---

## 6) Tests (minimum 3)

- **BinarySearchTreeTest** – inserts `[5,1,7]`, asserts root/left/right.
- **TreeServiceTest** – `createAndSave("4,2,8,3")` assigns an `id`, stores JSON.
- **TreeControllerTest** – POST `/api/trees` returns 200 and has `$.tree.value`.

Run:
```bash
# Windows
mvnw.cmd test
# macOS/Linux
./mvnw test
```

---

## 7) Database (H2 file‑backed by default)

`src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:h2:file:./data/bstdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

spring.jpa.hibernate.ddl-auto=update
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

- File‑backed H2 persists across restarts (`./data/bstdb.mv.db`).
- Visit **http://localhost:8080/h2-console** to inspect data.  
  JDBC URL: `jdbc:h2:file:./data/bstdb`, user: `sa`, password: *(empty)*.

**Switch to MySQL (optional):**
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/bstdb
spring.datasource.username=root
spring.datasource.password=yourpass
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

---

## 8) Common issues

- **`jakarta.persistence.* cannot be resolved`** → Reload the Maven project, clean Java language server, then build once:
  ```bash
  mvnw.cmd -q -DskipTests package   # Windows
  ./mvnw -q -DskipTests package     # macOS/Linux
  ```

- **No repositories found** → Ensure `TreeApiApplication` is in `org.backendapi` and packages match folder names. Add:
  ```java
  @SpringBootApplication(scanBasePackages="org.backendapi")
  @EnableJpaRepositories("org.backendapi.repository")
  @EntityScan("org.backendapi.model")
  ```

- **CORS (browser only)** → Add `@CrossOrigin(origins={"http://localhost:5173","http://127.0.0.1:5173"})` to the controller,
  or use a Vite proxy so the browser never sees a cross‑origin request.

- **404 `/api/trees`** → Backend not running, wrong port, or scanning issue (see above).

- **500 on POST** → Non‑numeric input; service throws. Optionally add a `@RestControllerAdvice`
  to return a clean 400 with an error message.

---

# Backend Walkthrough — File Tour & Flow Cheat‑Sheet

## Big picture

```
React / Postman  ─┐
   POST /api/trees│ JSON { numbers: "4,2,8,3" }
                   ▼
          [ TreeController ]  → validates & delegates
                   ▼
             [ TreeService ]  → parse CSV → build BST → serialize JSON
                   ▼
      [ TreeResultRepository ] → save to H2 DB (TreeResult entity)
                   ▼
               JSON response { id, numbers, tree:{value,left,right} }
```

## File‑by‑file

- **TreeApiApplication.java** — boots Spring + Tomcat, enables scanning for controllers, entities, repositories.  
- **TreeController.java** — REST endpoints: `POST /api/trees`, `GET /api/trees`, `GET /api/trees/{id}`.  
- **TreeRequest.java** — request DTO `{ numbers }`.  
- **TreeResponse.java** — response DTO `{ id, numbers, tree }`.  
- **TreeService.java** — core logic: parse CSV → build BST → JSON → persist → return.  
- **TreeResultRepository.java** — `JpaRepository<TreeResult, Long>` (CRUD, no SQL).  
- **TreeResult.java** — JPA entity storing the original input and serialized tree JSON.  
- **BinarySearchTree.java / TreeNode.java** — the data structure used by the service.  
- **application.properties** — H2 file‑backed DB + JPA settings + H2 console.

## Quick testing

Create:
```bash
curl -X POST http://localhost:8080/api/trees \
  -H "Content-Type: application/json" \
  -d '{"numbers":"4,2,8,3"}'
```

List:
```bash
curl http://localhost:8080/api/trees
```

H2 Console: `http://localhost:8080/h2-console`  
JDBC URL: `jdbc:h2:file:./data/bstdb` — User: `sa` — Password: *(empty)*
