package dk.sdu.cbse.enemy;

import dk.sdu.cbse.common.Entity;
import dk.sdu.cbse.common.EntityType;
import dk.sdu.cbse.common.GameData;
import dk.sdu.cbse.common.GameWorld;
import dk.sdu.cbse.common.IEntityProcessorService;

import java.util.Random;

public class EnemyProcessor implements IEntityProcessorService {

    private final Random random = new Random();
    private int changeDirectionCounter = 0;

    @Override
    public void process(GameData gameData, GameWorld world) {
        Entity enemy = world.getFirstEntityByType(EntityType.ENEMY);

        if (enemy == null) {
            return;
        }

        enemy.setX(enemy.getX() + enemy.getDx());
        enemy.setY(enemy.getY() + enemy.getDy());

        changeDirectionCounter--;

        if (changeDirectionCounter <= 0) {
            randomizeMovement(enemy);
        }

        enemy.wrapAround(gameData.getWidth(), gameData.getHeight());
        enemy.updateView();
    }

    private void randomizeMovement(Entity enemy) {
        enemy.setDx(random.nextDouble(-2, 2));
        enemy.setDy(random.nextDouble(-2, 2));
        enemy.setRotation(random.nextDouble(0, 360));

        changeDirectionCounter = random.nextInt(60, 180);
    }
}