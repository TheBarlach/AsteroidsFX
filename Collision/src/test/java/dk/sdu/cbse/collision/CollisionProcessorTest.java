package dk.sdu.cbse.collision;

import dk.sdu.cbse.common.Entity;
import dk.sdu.cbse.common.EntityType;
import dk.sdu.cbse.common.GameData;
import dk.sdu.cbse.common.GameWorld;
import javafx.scene.shape.Circle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class CollisionProcessorTest {

    @Test
    void playerBulletShouldHitEnemyAndAddScore() {
        GameData gameData = new GameData(800, 600);
        GameWorld world = new GameWorld();

        Entity enemy = new Entity(
                EntityType.ENEMY,
                new Circle(10),
                100,
                100,
                10
        );

        Entity bullet = new Entity(
                EntityType.BULLET,
                new Circle(3),
                100,
                100,
                3
        );

        bullet.setOwner("PLAYER");

        world.addEntity(enemy);
        world.addEntity(bullet);

        CollisionProcessor processor = new CollisionProcessor();

        processor.process(gameData, world);

        assertEquals(100, gameData.getScore());
        assertFalse(bullet.isAlive());
    }
}