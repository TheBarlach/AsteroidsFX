package dk.sdu.cbse;

import javafx.scene.input.KeyCode;

public class PlayerProcessor implements IEntityProcessorService {

    @Override
    public void process(GameData gameData, GameWorld world) {
        Player player = world.getPlayer();

        if (player == null) {
            return;
        }

        if (gameData.isKeyDown(KeyCode.LEFT)) {
            player.rotateLeft();
        }

        if (gameData.isKeyDown(KeyCode.RIGHT)) {
            player.rotateRight();
        }

        if (gameData.isKeyDown(KeyCode.UP)) {
            player.accelerate();
        }

        player.update(gameData.getWidth(), gameData.getHeight());
    }
}