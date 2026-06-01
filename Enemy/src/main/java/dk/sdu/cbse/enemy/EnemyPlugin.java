package dk.sdu.cbse.enemy;

import dk.sdu.cbse.common.Entity;
import dk.sdu.cbse.common.EntityType;
import dk.sdu.cbse.common.GameData;
import dk.sdu.cbse.common.GameWorld;
import dk.sdu.cbse.common.IGamePluginService;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class EnemyPlugin implements IGamePluginService {

    @Override
    public void start(GameData gameData, GameWorld world) {
        Polygon view = new Polygon(
                0, -14,
                14, 0,
                0, 14,
                -14, 0
        );

        view.setFill(Color.RED);

        Entity enemy = new Entity(
                EntityType.ENEMY,
                view,
                100,
                100,
                14
        );

        enemy.setDx(1.2);
        enemy.setDy(0.8);

        world.addEntity(enemy);
    }

    @Override
    public void stop(GameData gameData, GameWorld world) {
        Entity enemy = world.getFirstEntityByType(EntityType.ENEMY);

        if (enemy != null) {
            world.removeEntity(enemy);
        }
    }
}