package dk.sdu.cbse;

public class EnemyProcessor implements IEntityProcessorService {

    @Override
    public void process(GameData gameData, GameWorld world) {
        Enemy enemy = world.getEnemy();

        if (enemy == null) {
            return;
        }

        enemy.update(gameData.getWidth(), gameData.getHeight());
    }
}