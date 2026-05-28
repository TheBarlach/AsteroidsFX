package dk.sdu.cbse;

import javafx.scene.input.KeyCode;

public class ShootingProcessor implements IEntityProcessorService {

    private long lastPlayerShot = 0;
    private long lastEnemyShot = 0;

    @Override
    public void process(GameData gameData, GameWorld world) {
        handlePlayerShooting(gameData, world);
        handleEnemyShooting(world);
    }

    private void handlePlayerShooting(GameData gameData, GameWorld world) {
        if (world.getPlayer() == null) {
            return;
        }

        long now = System.nanoTime();

        if (!gameData.isKeyDown(KeyCode.SPACE)) {
            return;
        }

        if (now - lastPlayerShot < 300_000_000) {
            return;
        }

        Bullet bullet = world.getPlayer().shoot();
        world.addPlayerBullet(bullet);

        lastPlayerShot = now;
    }

    private void handleEnemyShooting(GameWorld world) {
        if (world.getEnemy() == null || world.getPlayer() == null) {
            return;
        }

        long now = System.nanoTime();

        if (now - lastEnemyShot < 1_200_000_000) {
            return;
        }

        Bullet bullet = world.getEnemy().shootAt(world.getPlayer());
        world.addEnemyBullet(bullet);

        lastEnemyShot = now;
    }
}