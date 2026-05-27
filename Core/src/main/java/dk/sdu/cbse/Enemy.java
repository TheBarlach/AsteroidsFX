package dk.sdu.cbse;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.util.Random;

public class Enemy {

    private final Entity entity;
    private final Random random = new Random();

    private int changeDirectionCounter = 0;

    public Enemy(double x, double y) {
        Polygon view = new Polygon(
                0, -14,
                14, 0,
                0, 14,
                -14, 0
        );

        view.setFill(Color.RED);

        entity = new Entity(view, x, y, 14);

        randomizeMovement();
        entity.updateView();
    }

    public void update(int screenWidth, int screenHeight) {
        entity.setX(entity.getX() + entity.getDx());
        entity.setY(entity.getY() + entity.getDy());

        changeDirectionCounter--;

        if (changeDirectionCounter <= 0) {
            randomizeMovement();
        }

        entity.wrapAround(screenWidth, screenHeight);
        entity.updateView();
    }

    private void randomizeMovement() {
        entity.setDx(random.nextDouble(-2, 2));
        entity.setDy(random.nextDouble(-2, 2));
        entity.setRotation(random.nextDouble(0, 360));

        changeDirectionCounter = random.nextInt(60, 180);
    }

    public Bullet shootAt(Player player) {
        double directionX = player.getX() - entity.getX();
        double directionY = player.getY() - entity.getY();

        double length = Math.sqrt(directionX * directionX + directionY * directionY);

        if (length == 0) {
            length = 1;
        }

        double bulletDx = directionX / length * 4;
        double bulletDy = directionY / length * 4;

        return new Bullet(entity.getX(), entity.getY(), bulletDx, bulletDy);
    }

    public void respawn(int screenWidth, int screenHeight) {
        entity.setX(random.nextDouble(50, screenWidth - 50));
        entity.setY(random.nextDouble(50, screenHeight - 50));

        randomizeMovement();
        entity.updateView();
    }

    public Entity getEntity() {
        return entity;
    }

    public Polygon getView() {
        return (Polygon) entity.getView();
    }
}