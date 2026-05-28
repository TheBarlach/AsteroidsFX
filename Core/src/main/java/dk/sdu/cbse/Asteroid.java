package dk.sdu.cbse;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.util.Random;

public class Asteroid {

    private final Entity entity;
    private final Random random = new Random();

    public Asteroid(double x, double y) {
        Polygon view = new Polygon(
                -20, -10,
                -10, -22,
                10, -18,
                22, -5,
                16, 15,
                0, 24,
                -18, 12
        );

        view.setFill(Color.GRAY);

        entity = new Entity(view, x, y, 24);

        entity.setDx(random.nextDouble(-1.5, 1.5));
        entity.setDy(random.nextDouble(-1.5, 1.5));
        entity.setRotation(random.nextDouble(0, 360));

        entity.updateView();
    }

    public void update(int screenWidth, int screenHeight) {
        entity.setX(entity.getX() + entity.getDx());
        entity.setY(entity.getY() + entity.getDy());

        entity.setRotation(entity.getRotation() + 1);

        entity.wrapAround(screenWidth, screenHeight);
        entity.updateView();
    }

    public Entity getEntity() {
        return entity;
    }
}