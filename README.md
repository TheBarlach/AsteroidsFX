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
```

---

## Architecture

The project is split into several independent Maven and JPMS modules.

### Common

The `Common` module contains shared classes and interfaces used by the other modules.

It includes:

- `Entity`
- `EntityType`
- `GameData`
- `GameWorld`
- `IGamePluginService`
- `IEntityProcessorService`
- `IPostEntityProcessorService`

The `Common` module does not depend on any gameplay component module. It only contains shared contracts and data structures.

---

### Core

The `Core` module contains the main JavaFX application and the game loop.

Its responsibilities are:

- Starting the JavaFX application
- Starting the Spring context
- Loading game services with `ServiceLoader`
- Loading external plugins with `ModuleLayer`
- Running entity processors
- Running post-entity processors
- Rendering entities on the screen

The `Core` module does not directly depend on `Player`, `Enemy`, `Bullet`, `Asteroid`, or `Collision`.

---

### Player

The `Player` module provides:

- `PlayerPlugin`
- `PlayerProcessor`

It creates the player entity and handles player movement using keyboard input.

---

### Enemy

The `Enemy` module provides:

- `EnemyPlugin`
- `EnemyProcessor`

It creates the enemy spaceship and controls its random movement.

---

### Bullet

The `Bullet` module provides:

- `ShootingProcessor`
- `BulletProcessor`

It handles:

- Player shooting
- Enemy shooting
- Bullet movement
- Bullet cleanup when bullets leave the screen

---

### Asteroid

The `Asteroid` module provides:

- `AsteroidPlugin`
- `AsteroidProcessor`

It creates and moves asteroids.

The `Asteroid` module can also be loaded dynamically from the `plugins` folder using JPMS `ModuleLayer`.

---

### Collision

The `Collision` module provides:

- `CollisionProcessor`
- `ScoringClient`

It handles collisions between:

- Player bullets and enemies
- Enemy bullets and players
- Bullets and asteroids
- Player and asteroids
- Enemy and asteroids

It also sends score updates to the external scoring microservice.

---

### ScoringService

The `ScoringService` module is a Spring Boot microservice responsible for managing the score.

It exposes REST endpoints for:

- Getting the current score
- Adding points
- Resetting the score

---

## Java Platform Module System

The project uses JPMS through `module-info.java` files in each module.

The `Core` module uses service interfaces from `Common`:

```java
uses dk.sdu.cbse.common.IGamePluginService;
uses dk.sdu.cbse.common.IEntityProcessorService;
uses dk.sdu.cbse.common.IPostEntityProcessorService;
```

Feature modules provide implementations:

```java
provides dk.sdu.cbse.common.IGamePluginService
        with dk.sdu.cbse.player.PlayerPlugin;
```

This makes the game extensible without hardcoding component dependencies in the core game engine.

---

## ServiceLoader

The game uses Java `ServiceLoader` to discover plugins and processors at runtime.

The main service types are:

- `IGamePluginService`
- `IEntityProcessorService`
- `IPostEntityProcessorService`

This allows each module to register its own behavior without being directly referenced by the `Core` module.

---

## Dynamic Plugin Loading

The project supports dynamic plugin loading using JPMS `ModuleLayer`.

External plugin JAR files can be placed in:

```text
plugins/
```

For example:

```text
plugins/Asteroid-1.0-SNAPSHOT.jar
```

The `Core` module loads plugin modules from this folder at startup.

This makes it possible to add or remove plugins without changing the core application code.

If the `Asteroid` plugin is removed from the `plugins` folder, the game still starts and runs without asteroids.

---

## Spring Integration

The `Core` module uses Spring to manage central game services.

Spring currently manages:

- `GameData`
- `GameWorld`
- `GameServiceRegistry`
- `GameScoreClient`

This demonstrates dependency injection while still preserving the JPMS and ServiceLoader architecture.

The Spring configuration is located in:

```text
Core/src/main/java/dk/sdu/cbse/config/GameConfig.java
```

---

## Scoring Microservice

The score system is implemented as a separate Spring Boot microservice.

The service is located in:

```text
ScoringService
```

It exposes these endpoints:

```text
GET  /score
POST /score/add?points=100
POST /score/reset
```

Example usage:

```bash
curl http://localhost:8080/score
curl -X POST "http://localhost:8080/score/add?points=100"
curl -X POST "http://localhost:8080/score/reset"
```

The game communicates with the scoring microservice through HTTP using Spring `RestTemplate`.

---

## Controls

```text
Left Arrow   - Rotate player left
Right Arrow  - Rotate player right
Up Arrow     - Accelerate player
Space        - Shoot
```

---

## Scoring Rules

```text
Player bullet hits enemy      +100 points
Player bullet hits asteroid   +50 points
Enemy bullet hits player      Player respawns
Player hits asteroid          Player respawns
Enemy hits asteroid           Enemy respawns
```

---

## Build Instructions

From the root folder, run:

```bash
mvn clean install
```

This builds all modules and copies the required JAR files into:

```text
mods-mvn/
libs/
```

---

## Running the Scoring Microservice

Open a terminal and run:

```bash
mvn -pl ScoringService spring-boot:run
```

The service runs on:

```text
http://localhost:8080
```

Test it with:

```bash
curl http://localhost:8080/score
```

---

## Running the Game

Open another terminal and run:

```bash
mvn exec:exec --non-recursive
```

The scoring microservice should be running before starting the game if score updates should be stored externally.

---

## Using the Asteroid Plugin

To use the asteroid module as an external plugin, place the built JAR file in the `plugins` folder:

```bash
mkdir -p plugins
cp Asteroid/target/Asteroid-1.0-SNAPSHOT.jar plugins/
```

If `Asteroid` is commented out in the root `pom.xml`, it can still be loaded dynamically from the `plugins` folder.

Example:

```xml
<!-- <module>Asteroid</module> -->
```

The game will still load the asteroid module from:

```text
plugins/Asteroid-1.0-SNAPSHOT.jar
```

To disable asteroids, remove or rename the plugin JAR:

```bash
mv plugins/Asteroid-1.0-SNAPSHOT.jar plugins/Asteroid-1.0-SNAPSHOT.jar.disabled
```

The game should still start without crashing.

---

## Testing

The project includes tests for both common game logic and component behavior.

Examples:

- `GameWorldTest`
- `CollisionProcessorTest`

Run all tests:

```bash
mvn test
```

Run tests for a specific module:

```bash
mvn -pl Common test
mvn -pl Collision test
```

---
