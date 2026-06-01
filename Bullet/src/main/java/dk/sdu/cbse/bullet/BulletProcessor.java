package dk.sdu.cbse.bullet;

import dk.sdu.cbse.common.Entity;
import dk.sdu.cbse.common.EntityType;
import dk.sdu.cbse.common.GameData;
import dk.sdu.cbse.common.GameWorld;
import dk.sdu.cbse.common.IEntityProcessorService;

import java.util.ArrayList;
import java.util.List;

public class BulletProcessor implements IEntityProcessorService {

    @Override
    public void process(GameData gameData, GameWorld world) {
        List<Entity> bullets = new ArrayList<>(world.getEntitiesByType(EntityType.BULLET));

        for (Entity bullet : bullets) {
            bullet.setX(bullet.getX() + bullet.getDx());
            bullet.setY(bullet.getY() + bullet.getDy());

            if (
                    bullet.getX() < 0 ||
                    bullet.getX() > gameData.getWidth() ||
                    bullet.getY() < 0 ||
                    bullet.getY() > gameData.getHeight()
            ) {
                bullet.setAlive(false);
            }

            bullet.updateView();
        }
    }
}