# 📦 Paragraph Chopper (UTF-8 Chunker)

A lightweight Clojure/Babashka-powered web app that takes UTF-8 user input from a form, removes all punctuation and whitespace, and returns the cleaned content split into 10-character chunks — shown in editable fields with one-click "Copy" buttons.

---

## ✨ Features

- Accepts multilingual UTF-8 input (Chinese, emoji, etc.)
- Removes all punctuation (ASCII, CJK, full-width), newlines, and spaces
- Splits cleaned text into 10-character chunks
- Returns as HTML input fields with "Copy" buttons
- Run locally with [Babashka](https://github.com/babashka/babashka)
- Optional public access via [ngrok](https://ngrok.com)

---

## 🚀 Quick Start (Ubuntu/Linux)

### 📦 Requirements

- Babashka (install with: `curl -s https://raw.githubusercontent.com/babashka/babashka/master/install | bash`)
- Git (pre-installed on most systems)
- ngrok (optional, for public testing)

---

### 🧪 Run Locally

```bash
git clone https://github.com/tzekensoh/paragraph_chopper.git
cd paragraph_chopper

# Make sure Babashka is installed
bb server.clj


To share your local server with others, install ngrok, then run:
ngrok http 8080

This will generate a public HTTPS URL (e.g., https://abc123.ngrok-free.app) that tunnels directly to your local app at
http://localhost:8080.

If it’s your first time using ngrok, register at ngrok.com, get your auth token,
and run:
ngrok config add-authtoken YOUR_TOKEN_HERE to enable access.
