package dk.sdu.cbse;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Player {

    private final Entity entity;

    public Player(double x, double y) {
        Polygon view = new Polygon(
                0, -15,
                10, 10,
                -10, 10
        );

        view.setFill(Color.WHITE);

        entity = new Entity(view, x, y, 15);
    }

    public void rotateLeft() {
        entity.setRotation(entity.getRotation() - 5);
    }

    public void rotateRight() {
        entity.setRotation(entity.getRotation() + 5);
    }

    public void accelerate() {
        double radians = Math.toRadians(entity.getRotation() - 90);

        entity.setDx(entity.getDx() + Math.cos(radians) * 0.2);
        entity.setDy(entity.getDy() + Math.sin(radians) * 0.2);
    }

    public Bullet shoot() {
        double radians = Math.toRadians(entity.getRotation() - 90);

        double bulletDx = Math.cos(radians) * 6;
        double bulletDy = Math.sin(radians) * 6;

        return new Bullet(entity.getX(), entity.getY(), bulletDx, bulletDy);
    }

    public void update(int screenWidth, int screenHeight) {
        entity.setX(entity.getX() + entity.getDx());
        entity.setY(entity.getY() + entity.getDy());

        entity.setDx(entity.getDx() * 0.99);
        entity.setDy(entity.getDy() * 0.99);

        entity.wrapAround(screenWidth, screenHeight);
        entity.updateView();
    }

    public void respawn(int screenWidth, int screenHeight) {
        entity.setX(screenWidth / 2.0);
        entity.setY(screenHeight / 2.0);
        entity.setDx(0);
        entity.setDy(0);
        entity.setRotation(0);

        entity.updateView();
    }

    public Entity getEntity() {
        return entity;
    }

    public Polygon getView() {
        return (Polygon) entity.getView();
    }

    public double getX() {
        return entity.getX();
    }

    public double getY() {
        return entity.getY();
    }
}