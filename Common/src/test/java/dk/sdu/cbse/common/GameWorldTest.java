package dk.sdu.cbse.common;

import javafx.scene.shape.Circle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameWorldTest {

    @Test
    void shouldAddEntityToWorld() {
        GameWorld world = new GameWorld();

        Entity entity = new Entity(
                EntityType.PLAYER,
                new Circle(5),
                100,
                100,
                5
        );

        world.addEntity(entity);

        assertEquals(1, world.getEntities().size());
        assertTrue(world.getEntities().contains(entity));
    }

    @Test
    void shouldRemoveEntityFromWorld() {
        GameWorld world = new GameWorld();

        Entity entity = new Entity(
                EntityType.ENEMY,
                new Circle(5),
                100,
                100,
                5
        );

        world.addEntity(entity);
        world.removeEntity(entity);

        assertEquals(0, world.getEntities().size());
    }

    @Test
    void shouldFindEntityByType() {
        GameWorld world = new GameWorld();

        Entity player = new Entity(
                EntityType.PLAYER,
                new Circle(5),
                100,
                100,
                5
        );

        Entity enemy = new Entity(
                EntityType.ENEMY,
                new Circle(5),
                200,
                200,
                5
        );

        world.addEntity(player);
        world.addEntity(enemy);

        Entity foundPlayer = world.getFirstEntityByType(EntityType.PLAYER);

        assertNotNull(foundPlayer);
        assertEquals(EntityType.PLAYER, foundPlayer.getType());
    }

    @Test
    void shouldRemoveDeadEntities() {
        GameWorld world = new GameWorld();

        Entity entity = new Entity(
                EntityType.BULLET,
                new Circle(5),
                100,
                100,
                5
        );

        world.addEntity(entity);

        entity.setAlive(false);
        world.removeDeadEntities();

        assertEquals(0, world.getEntities().size());
    }
}