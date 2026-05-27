package dk.sdu.cbse;

import javafx.scene.Node;

public class Entity {

    private Node view;

    private double x;
    private double y;
    private double dx;
    private double dy;
    private double rotation;
    private double radius;
    private boolean alive = true;

    public Entity(Node view, double x, double y, double radius) {
        this.view = view;
        this.x = x;
        this.y = y;
        this.radius = radius;

        updateView();
    }

    public void updateView() {
        view.setTranslateX(x);
        view.setTranslateY(y);
        view.setRotate(rotation);
    }

    public void wrapAround(int screenWidth, int screenHeight) {
        if (x < 0) {
            x = screenWidth;
        }

        if (x > screenWidth) {
            x = 0;
        }

        if (y < 0) {
            y = screenHeight;
        }

        if (y > screenHeight) {
            y = 0;
        }
    }

    public boolean collidesWith(Entity other) {
        double distanceX = other.getX() - x;
        double distanceY = other.getY() - y;
        double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);

        return distance < this.radius + other.radius;
    }

    public Node getView() {
        return view;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getDx() {
        return dx;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public double getDy() {
        return dy;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
}