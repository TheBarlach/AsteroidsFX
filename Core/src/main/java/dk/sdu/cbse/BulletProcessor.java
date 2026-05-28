package dk.sdu.cbse;

import java.util.ArrayList;
import java.util.List;

public class BulletProcessor implements IEntityProcessorService {

    @Override
    public void process(GameData gameData, GameWorld world) {
        updateBullets(gameData, world.getPlayerBullets());
        updateBullets(gameData, world.getEnemyBullets());
    }

    private void updateBullets(GameData gameData, List<Bullet> bulletList) {
        List<Bullet> bulletsToRemove = new ArrayList<>();

        for (Bullet bullet : bulletList) {
            bullet.update(gameData.getWidth(), gameData.getHeight());

            if (!bullet.isAlive()) {
                bulletsToRemove.add(bullet);
                bullet.getEntity().setAlive(false);
            }
        }

        bulletList.removeAll(bulletsToRemove);
    }
}