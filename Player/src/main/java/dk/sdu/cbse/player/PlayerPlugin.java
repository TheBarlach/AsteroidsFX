package dk.sdu.cbse.player;

import dk.sdu.cbse.common.Entity;
import dk.sdu.cbse.common.EntityType;
import dk.sdu.cbse.common.GameData;
import dk.sdu.cbse.common.GameWorld;
import dk.sdu.cbse.common.IGamePluginService;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class PlayerPlugin implements IGamePluginService {

    @Override
    public void start(GameData gameData, GameWorld world) {
        Polygon view = new Polygon(
                0, -15,
                10, 10,
                -10, 10
        );

        view.setFill(Color.WHITE);

        Entity player = new Entity(
                EntityType.PLAYER,
                view,
                gameData.getWidth() / 2.0,
                gameData.getHeight() / 2.0,
                15
        );

        world.addEntity(player);
    }

    @Override
    public void stop(GameData gameData, GameWorld world) {
        Entity player = world.getFirstEntityByType(EntityType.PLAYER);

        if (player != null) {
            world.removeEntity(player);
        }
    }
}