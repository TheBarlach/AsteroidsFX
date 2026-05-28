package dk.sdu.cbse;

public class EnemyPlugin implements IGamePluginService {

    @Override
    public void start(GameData gameData, GameWorld world) {
        Enemy enemy = new Enemy(100, 100);

        world.setEnemy(enemy);
    }

    @Override
    public void stop(GameData gameData, GameWorld world) {
        world.setEnemy(null);
    }
}