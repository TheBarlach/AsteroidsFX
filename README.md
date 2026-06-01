# AsteroidsFX

AsteroidsFX is a modular JavaFX-based Asteroids game built with the Java Platform Module System (JPMS), ServiceLoader, ModuleLayer plugins, Spring dependency injection, unit testing, and a separate scoring microservice.

The project is designed as a component-based game architecture where each major game feature is implemented as an independent module. Modules can be added, removed, or loaded dynamically without changing the core game engine.

---

## Project Overview

The purpose of the project is to demonstrate how an Asteroids game can be built using modular and extensible Java architecture principles.

The game currently includes:

- Player spaceship
- Enemy spaceship
- Bullets
- Asteroids
- Collision detection
- Score system
- External scoring microservice
- Dynamic plugin loading
- Spring-managed core services
- Unit and component tests

---

## Project Structure

```text
AsteroidsFX
├── Common
├── Core
├── Player
├── Enemy
├── Bullet
├── Asteroid
├── Collision
└── ScoringService