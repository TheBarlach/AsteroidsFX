package dk.sdu.cbse.collision;

import dk.sdu.cbse.common.Entity;
import dk.sdu.cbse.common.EntityType;
import dk.sdu.cbse.common.GameData;
import dk.sdu.cbse.common.GameWorld;
import dk.sdu.cbse.common.IPostEntityProcessorService;

import java.util.ArrayList;
import java.util.List;

public class CollisionProcessor implements IPostEntityProcessorService {

    @Override
    public void process(GameData gameData, GameWorld world) {
        checkPlayerBulletsAgainstEnemy(gameData, world);
        checkEnemyBulletsAgainstPlayer(gameData, world);
        checkBulletsAgainstAsteroids(gameData, world);
        checkPlayerAgainstAsteroids(gameData, world);
        checkEnemyAgainstAsteroids(gameData, world);
    }

    private void checkPlayerBulletsAgainstEnemy(GameData gameData, GameWorld world) {
        Entity enemy = world.getFirstEntityByType(EntityType.ENEMY);

        if (enemy == null) {
            return;
        }

        List<Entity> bullets = new ArrayList<>(world.getEntitiesByType(EntityType.BULLET));

        for (Entity bullet : bullets) {
            if (!"PLAYER".equals(bullet.getOwner())) {
                continue;
            }

            if (bullet.collidesWith(enemy)) {
                bullet.setAlive(false);

                gameData.addScore(100);

                respawnEnemy(enemy);
            }
        }
    }

    private void checkEnemyBulletsAgainstPlayer(GameData gameData, GameWorld world) {
        Entity player = world.getFirstEntityByType(EntityType.PLAYER);

        if (player == null) {
            return;
        }

        List<Entity> bullets = new ArrayList<>(world.getEntitiesByType(EntityType.BULLET));

        for (Entity bullet : bullets) {
            if (!"ENEMY".equals(bullet.getOwner())) {
                continue;
            }

            if (bullet.collidesWith(player)) {
                bullet.setAlive(false);

                respawnPlayer(gameData, player);
            }
        }
    }

    private void respawnPlayer(GameData gameData, Entity player) {
        player.setX(gameData.getWidth() / 2.0);
        player.setY(gameData.getHeight() / 2.0);
        player.setDx(0);
        player.setDy(0);
        player.setRotation(0);
        player.updateView();
    }

    private void respawnEnemy(Entity enemy) {
        enemy.setX(100);
        enemy.setY(100);
        enemy.setDx(1.2);
        enemy.setDy(0.8);
        enemy.setRotation(0);
        enemy.updateView();
    }

    private void checkBulletsAgainstAsteroids(GameData gameData, GameWorld world) {
        List<Entity> bullets = new ArrayList<>(world.getEntitiesByType(EntityType.BULLET));
        List<Entity> asteroids = new ArrayList<>(world.getEntitiesByType(EntityType.ASTEROID));

        for (Entity bullet : bullets) {
            for (Entity asteroid : asteroids) {
                if (bullet.collidesWith(asteroid)) {
                    bullet.setAlive(false);
                    asteroid.setAlive(false);

                    if ("PLAYER".equals(bullet.getOwner())) {
                        gameData.addScore(50);
                    }

                    break;
                }
            }
        }
    }

    private void checkPlayerAgainstAsteroids(GameData gameData, GameWorld world) {
        Entity player = world.getFirstEntityByType(EntityType.PLAYER);

        if (player == null) {
            return;
        }

        List<Entity> asteroids = new ArrayList<>(world.getEntitiesByType(EntityType.ASTEROID));

        for (Entity asteroid : asteroids) {
            if (player.collidesWith(asteroid)) {
                respawnPlayer(gameData, player);
                break;
            }
        }
    }

    private void checkEnemyAgainstAsteroids(GameData gameData, GameWorld world) {
        Entity enemy = world.getFirstEntityByType(EntityType.ENEMY);

        if (enemy == null) {
            return;
        }

        List<Entity> asteroids = new ArrayList<>(world.getEntitiesByType(EntityType.ASTEROID));

        for (Entity asteroid : asteroids) {
            if (enemy.collidesWith(asteroid)) {
                respawnEnemy(enemy);
                break;
            }
        }
    }
}