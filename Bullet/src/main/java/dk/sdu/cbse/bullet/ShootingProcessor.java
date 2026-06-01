package dk.sdu.cbse.bullet;

import dk.sdu.cbse.common.Entity;
import dk.sdu.cbse.common.EntityType;
import dk.sdu.cbse.common.GameData;
import dk.sdu.cbse.common.GameWorld;
import dk.sdu.cbse.common.IEntityProcessorService;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class ShootingProcessor implements IEntityProcessorService {

    private long lastPlayerShot = 0;
    private long lastEnemyShot = 0;

    @Override
    public void process(GameData gameData, GameWorld world) {
        handlePlayerShooting(gameData, world);
        handleEnemyShooting(world);
    }

    private void handlePlayerShooting(GameData gameData, GameWorld world) {
        Entity player = world.getFirstEntityByType(EntityType.PLAYER);

        if (player == null) {
            return;
        }

        if (!gameData.isKeyDown(KeyCode.SPACE)) {
            return;
        }

        long now = System.nanoTime();

        if (now - lastPlayerShot < 300_000_000) {
            return;
        }

        double radians = Math.toRadians(player.getRotation() - 90);

        double bulletDx = Math.cos(radians) * 6;
        double bulletDy = Math.sin(radians) * 6;

        Entity bullet = createBullet(player.getX(), player.getY(), bulletDx, bulletDy, "PLAYER");

        world.addEntity(bullet);

        lastPlayerShot = now;
    }

    private void handleEnemyShooting(GameWorld world) {
        Entity enemy = world.getFirstEntityByType(EntityType.ENEMY);
        Entity player = world.getFirstEntityByType(EntityType.PLAYER);

        if (enemy == null || player == null) {
            return;
        }

        long now = System.nanoTime();

        if (now - lastEnemyShot < 1_200_000_000) {
            return;
        }

        double directionX = player.getX() - enemy.getX();
        double directionY = player.getY() - enemy.getY();

        double length = Math.sqrt(directionX * directionX + directionY * directionY);

        if (length == 0) {
            length = 1;
        }

        double bulletDx = directionX / length * 4;
        double bulletDy = directionY / length * 4;

        Entity bullet = createBullet(enemy.getX(), enemy.getY(), bulletDx, bulletDy, "ENEMY");

        world.addEntity(bullet);

        lastEnemyShot = now;
    }

    private Entity createBullet(double x, double y, double dx, double dy, String owner) {
        Circle view = new Circle(3);
        view.setFill(Color.YELLOW);

        Entity bullet = new Entity(
                EntityType.BULLET,
                view,
                x,
                y,
                3
        );

        bullet.setDx(dx);
        bullet.setDy(dy);
        bullet.setOwner(owner);

        return bullet;
    }
}