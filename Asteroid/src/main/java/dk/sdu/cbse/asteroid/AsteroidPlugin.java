package dk.sdu.cbse.asteroid;

import dk.sdu.cbse.common.Entity;
import dk.sdu.cbse.common.EntityType;
import dk.sdu.cbse.common.GameData;
import dk.sdu.cbse.common.GameWorld;
import dk.sdu.cbse.common.IGamePluginService;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.util.Random;

public class AsteroidPlugin implements IGamePluginService {

    private final Random random = new Random();

    @Override
    public void start(GameData gameData, GameWorld world) {
        world.addEntity(createAsteroid(200, 150));
        world.addEntity(createAsteroid(500, 300));
        world.addEntity(createAsteroid(650, 450));
    }

    @Override
    public void stop(GameData gameData, GameWorld world) {
        for (Entity asteroid : world.getEntitiesByType(EntityType.ASTEROID)) {
            asteroid.setAlive(false);
        }
    }

    private Entity createAsteroid(double x, double y) {
        Polygon view = new Polygon(
                -20, -10,
                -10, -22,
                10, -18,
                22, -5,
                16, 15,
                0, 24,
                -18, 12
        );

        view.setFill(Color.GRAY);

        Entity asteroid = new Entity(
                EntityType.ASTEROID,
                view,
                x,
                y,
                24
        );

        asteroid.setDx(random.nextDouble(-1.5, 1.5));
        asteroid.setDy(random.nextDouble(-1.5, 1.5));
        asteroid.setRotation(random.nextDouble(0, 360));

        return asteroid;
    }
}