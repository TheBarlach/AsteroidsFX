package dk.sdu.cbse;

public class AsteroidProcessor implements IEntityProcessorService {

    @Override
    public void process(GameData gameData, GameWorld world) {
        for (Asteroid asteroid : world.getAsteroids()) {
            asteroid.update(gameData.getWidth(), gameData.getHeight());
        }
    }
}