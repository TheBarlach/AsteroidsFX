# AsteroidsFX

AsteroidsFX is a modular JavaFX-based Asteroids game built with Java Platform Module System (JPMS), ServiceLoader, ModuleLayer plugins, Spring dependency injection, unit testing, and a separate scoring microservice.

The project is designed as a component-based game architecture, where each major game feature is implemented as an independent module. Modules can be added, removed, or loaded dynamically without changing the core game engine.

---

## Project Overview

The goal of the project is to demonstrate a modular and extensible Asteroids game using modern Java architecture principles.

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

## Architecture

The project is split into several independent Maven/JPMS modules:

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
Common

The Common module contains shared classes and interfaces used by all other modules.

It includes:

Entity
EntityType
GameData
GameWorld
IGamePluginService
IEntityProcessorService
IPostEntityProcessorService

This module does not depend on any game component module. It only contains shared contracts and data structures.

Core

The Core module contains the main JavaFX application and the game loop.

Its responsibilities are:

Starting the JavaFX application
Starting the Spring context
Loading game services with ServiceLoader
Loading external plugins with ModuleLayer
Running entity processors
Running post-entity processors
Rendering entities on screen

The Core module does not directly depend on Player, Enemy, Bullet, Asteroid, or Collision.

Player

The Player module provides:

PlayerPlugin
PlayerProcessor

It creates the player entity and handles player movement using keyboard input.

Enemy

The Enemy module provides:

EnemyPlugin
EnemyProcessor

It creates the enemy spaceship and controls random movement.

Bullet

The Bullet module provides:

ShootingProcessor
BulletProcessor

It handles player shooting, enemy shooting, and bullet movement.

Asteroid

The Asteroid module provides:

AsteroidPlugin
AsteroidProcessor

It creates and moves asteroids. This module can also be loaded dynamically from the plugins folder.

Collision

The Collision module provides:

CollisionProcessor
ScoringClient

It handles collisions between:

Player bullets and enemies
Enemy bullets and players
Bullets and asteroids
Player and asteroids
Enemy and asteroids

It also sends score updates to the external scoring microservice.

ScoringService

The ScoringService module is a Spring Boot microservice responsible for managing the score.

It exposes REST endpoints for:

Getting the current score
Adding points
Resetting the score
Module System

The project uses JPMS with module-info.java files in each module.

The Core module uses service interfaces:

uses dk.sdu.cbse.common.IGamePluginService;
uses dk.sdu.cbse.common.IEntityProcessorService;
uses dk.sdu.cbse.common.IPostEntityProcessorService;

Feature modules provide implementations:

provides dk.sdu.cbse.common.IGamePluginService
        with dk.sdu.cbse.player.PlayerPlugin;

This makes the game extensible without hardcoding component dependencies in Core.

ServiceLoader

The game uses Java ServiceLoader to discover plugins and processors at runtime.

Examples of loaded services:

IGamePluginService
IEntityProcessorService
IPostEntityProcessorService

This allows each module to register its own behavior without being directly referenced by the core game engine.

Dynamic Plugin Loading

The project supports dynamic plugin loading using JPMS ModuleLayer.

External plugin JAR files can be placed in:

plugins/

For example:

plugins/Asteroid-1.0-SNAPSHOT.jar

The Core module loads plugin modules from this folder at startup.

This makes it possible to add or remove plugins without changing the core application code.

If the Asteroid plugin is removed from the plugins folder, the game still starts and runs without asteroids.

Spring Integration

The Core module uses Spring to manage central game services.

Spring currently manages:

GameData
GameWorld
GameServiceRegistry
GameScoreClient

This demonstrates dependency injection while still preserving the JPMS and ServiceLoader architecture.

The Spring configuration is located in:

Core/src/main/java/dk/sdu/cbse/config/GameConfig.java
Scoring Microservice

The score system is implemented as a separate Spring Boot microservice.

The service is located in:

ScoringService

It exposes these endpoints:

GET  /score
POST /score/add?points=100
POST /score/reset

Example usage:

curl http://localhost:8080/score
curl -X POST "http://localhost:8080/score/add?points=100"
curl -X POST "http://localhost:8080/score/reset"

The game communicates with the scoring microservice through HTTP using Spring RestTemplate.

Controls
Left Arrow   - Rotate player left
Right Arrow  - Rotate player right
Up Arrow     - Accelerate player
Space        - Shoot
Scoring Rules
Player bullet hits enemy      +100 points
Player bullet hits asteroid   +50 points
Enemy bullet hits player      Player respawns
Player hits asteroid          Player respawns
Enemy hits asteroid           Enemy respawns
How to Build

From the root folder:

mvn clean install

This builds all modules and copies the required JAR files into:

mods-mvn/
libs/
How to Run the Scoring Microservice

Open a terminal and run:

mvn -pl ScoringService spring-boot:run

The service runs on:

http://localhost:8080

Test it with:

curl http://localhost:8080/score
How to Run the Game

Open another terminal and run:

mvn exec:exec --non-recursive

The scoring microservice should be running before starting the game if score updates should be stored externally.

How to Use the Asteroid Plugin

To use the asteroid module as an external plugin, place the built JAR file in the plugins folder:

mkdir -p plugins
cp Asteroid/target/Asteroid-1.0-SNAPSHOT.jar plugins/

If Asteroid is commented out in the root pom.xml, it can still be loaded dynamically from the plugins folder.

Example:

<!-- <module>Asteroid</module> -->

The game will still load the asteroid module from:

plugins/Asteroid-1.0-SNAPSHOT.jar

To disable asteroids, remove or rename the plugin JAR:

mv plugins/Asteroid-1.0-SNAPSHOT.jar plugins/Asteroid-1.0-SNAPSHOT.jar.disabled

The game should still start without crashing.

Testing

The project includes tests for both common game logic and component behavior.

Examples:

GameWorldTest
CollisionProcessorTest

Run all tests:

mvn test

Run tests for a specific module:

mvn -pl Common test
mvn -pl Collision test