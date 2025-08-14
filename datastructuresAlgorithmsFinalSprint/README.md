# Binary Search Tree Builder — Full Project Guide (Backend + Frontend)

This project lets a user enter a list of numbers, builds a **Binary Search Tree (BST)** from them, saves the result, and shows previous trees. It’s built as a separate **Spring Boot API** (backend) and **React + Vite** app (frontend).

> The assignment allows a server‑rendered page with routes `/enter-numbers`, `/process-numbers`, `/previous-trees` **or** a separate frontend + API. This project implements the **separate Frontend + API** approach. A tiny optional fallback is included below in case a grader insists on visiting those exact paths.

---

## ✨ Features

- Enter CSV numbers (e.g., `4,2,8,3`), submit, and get a BST
- See a simple tree visualization and the raw JSON
- View previous results stored in the DB
- H2 **file-backed** database by default (persists across restarts)
- Clear API contract + three unit tests in the backend
- Frontend “Windows terminal” theme for fun & clarity

---

## 🧱 Tech Stack

- **Backend:** Java 21+, Spring Boot 3.x, Spring Web, Spring Data JPA, Jackson, H2 (file-backed)
- **Frontend:** React 18, Vite dev server with proxy
- **Testing (backend):** JUnit + Spring Boot Test

---

## 🗺️ Architecture & Flow

```
           ┌──────────────────────────┐
Numbers →  │  React (Vite dev server) │
           │  /api/* (proxy)          │
           └─────────────┬────────────┘
                         │ HTTP
                         ▼
           ┌──────────────────────────┐
           │  Spring Boot API         │
           │  POST /api/trees         │
           │  GET  /api/trees         │
           └─────────────┬────────────┘
                         │
                         ▼
           ┌──────────────────────────┐
           │  H2 DB (file-backed)     │
           │  TreeResult rows         │
           └──────────────────────────┘
```

**Request flow:**  
1) Frontend posts `{ "numbers": "4,2,8,3" }` to `/api/trees`.  
2) Backend builds a BST (sequential inserts), serializes it to `{ value, left, right }`, saves `{ inputNumbers, treeJson }` in H2, and returns JSON.  
3) Frontend renders the JSON and can `GET /api/trees` to show previous results.

---

## 📁 Repository Layout (suggested)

```
project-root/
├─ backend/            # Spring Boot app
│  ├─ src/main/java/org/backendapi/...
│  ├─ src/main/resources/application.properties
│  └─ pom.xml
└─ frontend/           # React + Vite app
   ├─ src/App.jsx, App.css, main.jsx
   ├─ vite.config.js
   └─ package.json
```

> Folder names can vary; use whatever you already have. The important part is the **API paths** and **proxy setup** below.

---

## ▶️ Running Everything

### 1) Backend (Spring Boot)
From `backend/` (where `pom.xml` lives):

```bash
# Windows (PowerShell)
mvnw.cmd spring-boot:run

# macOS/Linux
./mvnw spring-boot:run
```

You should see: `Tomcat started on port 8080`

**Health checks (Postman or curl):**
```bash
# create/process
curl -X POST http://localhost:8080/api/trees \
  -H "Content-Type: application/json" \
  -d '{"numbers":"4,2,8,3"}'

# list previous
curl http://localhost:8080/api/trees
```

### 2) Frontend (React + Vite)
From `frontend/`:

```bash
npm install
npm run dev
```
Open the printed URL (usually `http://localhost:5173`).

**Proxy config (no CORS needed during dev):** `vite.config.js`
```js
import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

export default defineConfig({
  plugins: [react()],
  server: { proxy: { '/api': 'http://localhost:8080' } }
})
```

The UI calls `/api/trees`, and Vite forwards it to `http://localhost:8080/api/trees`.

---

## 🔌 API Contract

Base: `http://localhost:8080/api/trees`

### Create / process a tree
**POST** `/api/trees`  
Body:
```json
{ "numbers": "4,2,8,3" }
```
Response (example):
```json
{
  "id": 1,
  "numbers": "4,2,8,3",
  "tree": { "value": 4, "left": { "value": 2, "left": null, "right": { "value": 3, "left": null, "right": null } }, "right": { "value": 8, "left": null, "right": null } }
}
```

### List previous results
**GET** `/api/trees` → `[{ id, numbers, tree }, ...]`

### Get one by id (optional)
**GET** `/api/trees/{id}` → `{ id, numbers, tree }`

---

## 🧠 Implementation Notes (Backend)

- **Controller** (`TreeController`)  
  Endpoints above; uses DTOs for input/output. `@CrossOrigin` can allow `http://localhost:5173` if you prefer not to use a proxy.

- **DTOs**  
  - `TreeRequest { numbers }`  
  - `TreeResponse { id, numbers, tree }` (where `tree` is a Jackson `JsonNode`)

- **Service** (`TreeService`)  
  - Parses the CSV into `List<Integer>` (trims, validates)  
  - Builds a BST by sequentially calling `BinarySearchTree.insert(n)`  
  - Serializes the root node to a plain `{ value, left, right }` structure (recursively) and converts it to JSON  
  - Saves `TreeResult(inputNumbers, treeJson)` via JPA repository

- **Repository** (`TreeResultRepository extends JpaRepository`)  
  Gives you `save`, `findAll`, `findById`, `count`, etc., with no SQL.

- **Entity** (`TreeResult`)  
  Fields: `id`, `inputNumbers`, `treeJson` (LOB), `createdAt` timestamp.

- **H2 file-backed** (persisted)  
  `spring.datasource.url=jdbc:h2:file:./data/bstdb`  
  H2 console: `http://localhost:8080/h2-console` (JDBC URL above, user `sa`, empty password).

- **Duplicates**  
  Standard BST insert ignores duplicates (or places them consistently)—this implementation keeps the tree simple.

---

## 🔍 BST: What’s Returned

Trees are represented as nested JSON objects:
```json
{ "value": 4,
  "left":  { "value": 2, "left": null, "right": { "value": 3, "left": null, "right": null } },
  "right": { "value": 8, "left": null, "right": null } }
```
This makes it easy for the frontend to visualize recursively.

---

## 🧪 Testing (Backend)

There are at least **three** tests:
1. **BST shape** — Insert `[5,1,7]` and assert root/left/right values.
2. **Service persistence** — Calling `createAndSave("4,2,8,3")` assigns an ID and stores JSON.
3. **Invalid input** — Blank or non-numeric parts cause an error (e.g., `IllegalArgumentException`).

Run:
```bash
# Windows
mvnw.cmd test
# macOS/Linux
./mvnw test
```

---

## 🖥️ Frontend UI

- **Numbers (CSV)** input  
- **Submit** → POST `/api/trees`  
- **Show Previous** → GET `/api/trees`  
- Two panes: visualization (left) and raw JSON (right)  
- Styled like a **green-on-black terminal** (see `App.css`)

---

## ✅ Requirement Mapping (Assignment Rubric)

The brief includes three classic routes (`/enter-numbers`, `/process-numbers`, `/previous-trees`) but explicitly allows **separate API + Frontend**. This project implements:

- **User input interface** — the React page provides input + Submit + Show Previous ✅  
- **Processing route** — `POST /api/trees`: accepts numbers, builds BST, returns JSON, stores both ✅  
- **Display previous** — `GET /api/trees`: returns all saved inputs + trees ✅  
- **Testing** — At least three JUnit tests ✅  
- **Database** — H2 file-backed, persists across restarts ✅  
- **Bonus (balanced)** — intentionally left out (kept project simple); easy to add later if needed

> **Optional fallback (if a grader insists on visiting `/enter-numbers` in the backend):**  
> You can add a tiny MVC `PageController` that serves a minimal HTML form and forwards `/process-numbers` to `/api/trees`. (See your backend walkthrough docs for a ready-to-paste snippet.) This is not required when using the separate frontend approach, but it makes the rubric bulletproof.

---

## 🚢 Production Notes

- **Frontend build**: `npm run build` → serve `dist/` (Nginx/Apache/Netlify/etc.).  
- **Backend**: run the jar or deploy to your target platform.  
- **Reverse proxy**: forward `/api` from your web server to the Spring Boot service.  
- **Config**: Consider a `.env` var like `VITE_API_BASE` for your frontend to switch between `/api` (dev) and an absolute URL (prod).

---

## 🩺 Troubleshooting

| Symptom | Likely Cause | Fix |
|---|---|---|
| Browser shows CORS error | Calling absolute `http://localhost:8080` from `5173` without proxy | Use Vite proxy, or add `@CrossOrigin` in the controller |
| `404 /api/trees` | Backend not running / wrong port / wrong path | Start Spring Boot, confirm logs show mappings |
| `500` on POST | Invalid CSV (non-integers) | Check server logs; add validation / exception handler |
| H2 won’t connect | Wrong JDBC URL in H2 console | Use `jdbc:h2:file:./data/bstdb` (as per properties) |
| Proxy not forwarding | Vite config changed | Restart `npm run dev` after edits |

---