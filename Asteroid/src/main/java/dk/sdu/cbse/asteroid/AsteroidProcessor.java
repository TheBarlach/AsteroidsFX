package dk.sdu.cbse.asteroid;

import dk.sdu.cbse.common.Entity;
import dk.sdu.cbse.common.EntityType;
import dk.sdu.cbse.common.GameData;
import dk.sdu.cbse.common.GameWorld;
import dk.sdu.cbse.common.IEntityProcessorService;

import java.util.ArrayList;
import java.util.List;

public class AsteroidProcessor implements IEntityProcessorService {

    @Override
    public void process(GameData gameData, GameWorld world) {
        List<Entity> asteroids = new ArrayList<>(world.getEntitiesByType(EntityType.ASTEROID));

        for (Entity asteroid : asteroids) {
            asteroid.setX(asteroid.getX() + asteroid.getDx());
            asteroid.setY(asteroid.getY() + asteroid.getDy());

            asteroid.setRotation(asteroid.getRotation() + 1);

            asteroid.wrapAround(gameData.getWidth(), gameData.getHeight());
            asteroid.updateView();
        }
    }
}