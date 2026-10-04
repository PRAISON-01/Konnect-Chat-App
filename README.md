# Konnect Chat App (Anonymous Themed)

Konnect is an anonymous, real-time web messenger. Create a room, share the code, start chatting — no sign-up, no login, no accounts. Identity is handled through a lightweight, client-generated session ID instead of a user profile, so anyone with the room code can join and participate instantly.

The project is built as a practical demonstration of clean layered architecture on the backend (Controller → Service → Repository → Model), paired with a React + Tailwind CSS frontend that consumes a REST API. It's designed to be simple to reason about, properly tested, and easy to extend.

Features
- Create and join chat rooms via a shareable room code
- Post, read, and delete messages in real time
- Auto-generated anonymous aliases (no usernames to set up)
- Room and message expiry (chats don't live forever)
- Rate limiting to prevent spam

Stack
Backend: Java, Spring Boot, layered architecture
Frontend: React, Tailwind CSS
Database: H2 (dev), PostgreSQL-ready
Testing: JUnit 5, Mockito, Spring Boot Test (unit + integration)

Why this project
Most portfolio chat apps default to full auth systems. Konnect intentionally skips that to focus on a different set of engineering problems: real-time data flow, ephemeral state management, ownership without identity, and ensuring clean separation of concerns across layers — the kind of tradeoffs real systems have to make deliberately, not by default.
