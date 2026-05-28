package dk.sdu.cbse;

import java.util.ArrayList;
import java.util.List;

public class CollisionProcessor implements IPostEntityProcessorService {

    @Override
    public void process(GameData gameData, GameWorld world) {
        checkPlayerBulletsAgainstEnemy(gameData, world);
        checkEnemyBulletsAgainstPlayer(gameData, world);
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
}