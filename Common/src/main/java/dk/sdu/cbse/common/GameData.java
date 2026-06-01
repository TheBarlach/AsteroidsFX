package dk.sdu.cbse.common;

import javafx.scene.input.KeyCode;

import java.util.HashSet;
import java.util.Set;

public class GameData {

    private final int width;
    private final int height;

    private final Set<KeyCode> keys = new HashSet<>();

    private int score = 0;

    public GameData(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void addKey(KeyCode keyCode) {
        keys.add(keyCode);
    }

    public void removeKey(KeyCode keyCode) {
        keys.remove(keyCode);
    }

    public boolean isKeyDown(KeyCode keyCode) {
        return keys.contains(keyCode);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addScore(int points) {
        score += points;
    }

    public void resetScore() {
        score = 0;
    }
}