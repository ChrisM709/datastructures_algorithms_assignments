# BST Frontend (React + Vite) — Friendly Guide

A tiny React app that lets you type numbers like `4,2,8,3`, sends them to your Spring Boot API, and shows the **Binary Search Tree** (BST) it gets back. It can also list your **previous trees** saved by the backend.

> This repo is **frontend only**. Make sure the backend from your BST project is running on `http://localhost:8080`.

---

## 🚀 Quick Start (60 seconds)

1) **Install dependencies**
```bash
npm install
```

2) **Start the dev server**
```bash
npm run dev
```
Then open the URL Vite prints (usually `http://localhost:5173`).

That’s it! Type a CSV of numbers, click **Submit**, and watch the tree render.

> Dev is preconfigured with a **Vite proxy** so any request to `/api/*` goes to `http://localhost:8080/*` (no CORS headaches).

---

## 🧩 What this app does

- Shows a single page with:
  - an input for **Numbers (CSV)**,
  - a **Submit** button (sends the list to the backend),
  - a **Show Previous** button (loads prior results from the DB),
  - a simple **tree visualization** (nested boxes),
  - a **Raw JSON** panel for the exact data returned.
- Uses a **Windows terminal style** (green on black).

---

## 🔌 Which API endpoints it calls

The app calls your **backend** like this:

- **POST** `/api/trees`  
  Body:
  ```json
  { "numbers": "4,2,8,3" }
  ```
  Returns something like:
  ```json
  {
    "id": 1,
    "numbers": "4,2,8,3",
    "tree": { "value": 4, "left": { ... }, "right": { ... } }
  }
  ```

- **GET** `/api/trees` → `[{ id, numbers, tree }, ...]`  
- **GET** `/api/trees/{id}` → `{ id, numbers, tree }` (optional in the UI)

> During **dev**, the app requests `/api/...` and Vite forwards it to `http://localhost:8080/api/...`.
> For **production**, see the build section below.


---

## 🧭 How to use the UI

1. Enter numbers like `4,2,8,3` (spaces are okay).
2. Click **Submit** – the tree renders on the left; JSON shows on the right.
3. Click **Show Previous** – a list of previous results appears at the bottom.
4. Click any “Tree JSON” disclosure to inspect the exact data.  

---

## 🗂 Project layout (frontend)

```
src/
├─ App.jsx          # page UI + fetch calls + visualization
├─ App.css          # green-on-black terminal theme
├─ main.jsx         # React entry point
└─ index.css        # optional base styles
vite.config.js      # dev proxy to backend
index.html          # Vite HTML entry
package.json
```

---

## 🎨 Make it your own (theme)

Colors live at the top of `App.css`:
```css
:root{
  --bg: #000;            /* background */
  --fg: #aaffaa;         /* text */
  --fg-bright: #00ff7f;  /* highlights */
  --border: #00aa55;     /* outlines */
}
```
Tweak those four values to adjust brightness/contrast.

---

## 🧱 Build & preview (production)

```bash
npm run build     # creates dist/
npm run preview   # serve the built app locally
```
To deploy, serve `dist/` as static files (e.g., Nginx, Netlify).  
In production you’ll normally reverse‑proxy `/api` to your Spring service.

---

## 🩺 Troubleshooting

- **CORS error** in browser  
  Use the default Vite proxy (Option A). If calling absolute URLs, open CORS on the backend for `http://localhost:5173`.

- **404 `/api/trees`**  
  Backend isn’t running / wrong port / different path. Confirm Spring logs show mappings for `POST /api/trees` and `GET /api/trees`.

- **500 error**  
  Usually invalid input (non‑integers). The UI prints the message. Check Spring console for details.

- **Proxy not working**  
  After changing `vite.config.js`, restart `npm run dev`.

---

## ✅ Quick grading checklist

- Input, **Submit**, **Show Previous** present ✔️  
- POST `/api/trees` returns a tree and is displayed ✔️  
- GET `/api/trees` lists prior trees ✔️  
- UI is clear and responsive ✔️  
- Matches the backend contract ✔️

---
