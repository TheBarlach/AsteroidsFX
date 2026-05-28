package dk.sdu.cbse;

public class AsteroidPlugin implements IGamePluginService {

    @Override
    public void start(GameData gameData, GameWorld world) {
        world.addAsteroid(new Asteroid(200, 150));
        world.addAsteroid(new Asteroid(500, 300));
        world.addAsteroid(new Asteroid(650, 450));
    }

    @Override
    public void stop(GameData gameData, GameWorld world) {
        world.getAsteroids().clear();
    }
}