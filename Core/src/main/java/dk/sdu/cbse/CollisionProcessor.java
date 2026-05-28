package dk.sdu.cbse;

import java.util.ArrayList;
import java.util.List;

public class CollisionProcessor implements IPostEntityProcessorService {

    @Override
    public void process(GameData gameData, GameWorld world) {
        checkPlayerBulletsAgainstEnemy(gameData, world);
        checkEnemyBulletsAgainstPlayer(gameData, world);
        checkPlayerBulletsAgainstAsteroids(world);
        checkEnemyBulletsAgainstAsteroids(world);
        checkPlayerAgainstAsteroids(gameData, world);
        checkEnemyAgainstAsteroids(gameData, world);
    }

    private void checkPlayerBulletsAgainstEnemy(GameData gameData, GameWorld world) {
        if (world.getEnemy() == null) {
            return;
        }

        List<Bullet> bulletsToRemove = new ArrayList<>();

        for (Bullet bullet : world.getPlayerBullets()) {
            if (bullet.getEntity().collidesWith(world.getEnemy().getEntity())) {
                bulletsToRemove.add(bullet);
                bullet.getEntity().setAlive(false);

                world.getEnemy().respawn(gameData.getWidth(), gameData.getHeight());
            }
        }

        for (Bullet bullet : bulletsToRemove) {
            world.removePlayerBullet(bullet);
        }
    }

    private void checkEnemyBulletsAgainstPlayer(GameData gameData, GameWorld world) {
        if (world.getPlayer() == null) {
            return;
        }

        List<Bullet> bulletsToRemove = new ArrayList<>();

        for (Bullet bullet : world.getEnemyBullets()) {
            if (bullet.getEntity().collidesWith(world.getPlayer().getEntity())) {
                bulletsToRemove.add(bullet);
                bullet.getEntity().setAlive(false);

                world.getPlayer().respawn(gameData.getWidth(), gameData.getHeight());
            }
        }

        for (Bullet bullet : bulletsToRemove) {
            world.removeEnemyBullet(bullet);
        }
    }

    private void checkPlayerBulletsAgainstAsteroids(GameWorld world) {
        List<Bullet> bulletsToRemove = new ArrayList<>();
        List<Asteroid> asteroidsToRemove = new ArrayList<>();

        for (Bullet bullet : world.getPlayerBullets()) {
            for (Asteroid asteroid : world.getAsteroids()) {
                if (bullet.getEntity().collidesWith(asteroid.getEntity())) {
                    bulletsToRemove.add(bullet);
                    asteroidsToRemove.add(asteroid);

                    bullet.getEntity().setAlive(false);
                    asteroid.getEntity().setAlive(false);

                    break;
                }
            }
        }

        for (Bullet bullet : bulletsToRemove) {
            world.removePlayerBullet(bullet);
        }

        for (Asteroid asteroid : asteroidsToRemove) {
            world.removeAsteroid(asteroid);
        }
    }

    private void checkPlayerAgainstAsteroids(GameData gameData, GameWorld world) {
        if (world.getPlayer() == null) {
            return;
        }

        for (Asteroid asteroid : world.getAsteroids()) {
            if (world.getPlayer().getEntity().collidesWith(asteroid.getEntity())) {
                world.getPlayer().respawn(gameData.getWidth(), gameData.getHeight());
                break;
            }
        }
    }

    private void checkEnemyBulletsAgainstAsteroids(GameWorld world) {
        List<Bullet> bulletsToRemove = new ArrayList<>();
        List<Asteroid> asteroidsToRemove = new ArrayList<>();

        for (Bullet bullet : world.getEnemyBullets()) {
            for (Asteroid asteroid : world.getAsteroids()) {
                if (bullet.getEntity().collidesWith(asteroid.getEntity())) {
                    bulletsToRemove.add(bullet);
                    asteroidsToRemove.add(asteroid);

                    bullet.getEntity().setAlive(false);
                    asteroid.getEntity().setAlive(false);

                    break;
                }
            }
        }

        for (Bullet bullet : bulletsToRemove) {
            world.removeEnemyBullet(bullet);
        }

        for (Asteroid asteroid : asteroidsToRemove) {
            world.removeAsteroid(asteroid);
        }
    }

    private void checkEnemyAgainstAsteroids(GameData gameData, GameWorld world) {
        if (world.getEnemy() == null) {
            return;
        }

        for (Asteroid asteroid : world.getAsteroids()) {
            if (world.getEnemy().getEntity().collidesWith(asteroid.getEntity())) {
                world.getEnemy().respawn(gameData.getWidth(), gameData.getHeight());
                break;
            }
        }
    }
}