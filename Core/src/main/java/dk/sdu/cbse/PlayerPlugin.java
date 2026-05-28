package dk.sdu.cbse;

public class PlayerPlugin implements IGamePluginService {

    @Override
    public void start(GameData gameData, GameWorld world) {
        Player player = new Player(
                gameData.getWidth() / 2.0,
                gameData.getHeight() / 2.0
        );

        world.setPlayer(player);
    }

    @Override
    public void stop(GameData gameData, GameWorld world) {
        world.setPlayer(null);
    }
}