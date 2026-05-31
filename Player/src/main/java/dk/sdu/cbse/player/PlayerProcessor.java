package dk.sdu.cbse.player;

import dk.sdu.cbse.common.Entity;
import dk.sdu.cbse.common.EntityType;
import dk.sdu.cbse.common.GameData;
import dk.sdu.cbse.common.GameWorld;
import dk.sdu.cbse.common.IEntityProcessorService;
import javafx.scene.input.KeyCode;

public class PlayerProcessor implements IEntityProcessorService {

    @Override
    public void process(GameData gameData, GameWorld world) {
        Entity player = world.getFirstEntityByType(EntityType.PLAYER);

        if (player == null) {
            return;
        }

        if (gameData.isKeyDown(KeyCode.LEFT)) {
            player.setRotation(player.getRotation() - 5);
        }

        if (gameData.isKeyDown(KeyCode.RIGHT)) {
            player.setRotation(player.getRotation() + 5);
        }

        if (gameData.isKeyDown(KeyCode.UP)) {
            accelerate(player);
        }

        player.setX(player.getX() + player.getDx());
        player.setY(player.getY() + player.getDy());

        player.setDx(player.getDx() * 0.99);
        player.setDy(player.getDy() * 0.99);

        player.wrapAround(gameData.getWidth(), gameData.getHeight());
        player.updateView();
    }

    private void accelerate(Entity player) {
        double radians = Math.toRadians(player.getRotation() - 90);

        player.setDx(player.getDx() + Math.cos(radians) * 0.2);
        player.setDy(player.getDy() + Math.sin(radians) * 0.2);
    }
}