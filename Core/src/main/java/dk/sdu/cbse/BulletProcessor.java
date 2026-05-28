package dk.sdu.cbse;

import java.util.ArrayList;
import java.util.List;

public class BulletProcessor implements IEntityProcessorService {

    @Override
    public void process(GameData gameData, GameWorld world) {
        updatePlayerBullets(gameData, world);
        updateEnemyBullets(gameData, world);
    }

    private void updatePlayerBullets(GameData gameData, GameWorld world) {
        List<Bullet> bulletsToRemove = new ArrayList<>();

        for (Bullet bullet : world.getPlayerBullets()) {
            bullet.update(gameData.getWidth(), gameData.getHeight());

            if (!bullet.isAlive()) {
                bulletsToRemove.add(bullet);
                bullet.getEntity().setAlive(false);
            }
        }

        for (Bullet bullet : bulletsToRemove) {
            world.removePlayerBullet(bullet);
        }
    }

    private void updateEnemyBullets(GameData gameData, GameWorld world) {
        List<Bullet> bulletsToRemove = new ArrayList<>();

        for (Bullet bullet : world.getEnemyBullets()) {
            bullet.update(gameData.getWidth(), gameData.getHeight());

            if (!bullet.isAlive()) {
                bulletsToRemove.add(bullet);
                bullet.getEntity().setAlive(false);
            }
        }

        for (Bullet bullet : bulletsToRemove) {
            world.removeEnemyBullet(bullet);
        }
    }
}