package dk.sdu.cbse;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Bullet {

    private final Entity entity;

    private int lifeTime = 120;

    public Bullet(double x, double y, double dx, double dy) {
        Circle view = new Circle(3);
        view.setFill(Color.YELLOW);

        entity = new Entity(view, x, y, 3);
        entity.setDx(dx);
        entity.setDy(dy);
    }

    public void update(int screenWidth, int screenHeight) {
        entity.setX(entity.getX() + entity.getDx());
        entity.setY(entity.getY() + entity.getDy());

        lifeTime--;

        if (
                entity.getX() < 0 ||
                entity.getX() > screenWidth ||
                entity.getY() < 0 ||
                entity.getY() > screenHeight
        ) {
            lifeTime = 0;
        }

        entity.updateView();
    }

    public boolean isAlive() {
        return lifeTime > 0;
    }

    public Entity getEntity() {
        return entity;
    }

    public Circle getView() {
        return (Circle) entity.getView();
    }
}