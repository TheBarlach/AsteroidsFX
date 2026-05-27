package dk.sdu.cbse;

import java.util.ArrayList;
import java.util.List;

public class GameWorld {

    private Player player;
    private Enemy enemy;

    private final List<Bullet> playerBullets = new ArrayList<>();
    private final List<Bullet> enemyBullets = new ArrayList<>();

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
    }

    public List<Bullet> getPlayerBullets() {
        return playerBullets;
    }

    public List<Bullet> getEnemyBullets() {
        return enemyBullets;
    }

    public void addPlayerBullet(Bullet bullet) {
        playerBullets.add(bullet);
    }

    public void addEnemyBullet(Bullet bullet) {
        enemyBullets.add(bullet);
    }

    public void removePlayerBullet(Bullet bullet) {
        playerBullets.remove(bullet);
    }

    public void removeEnemyBullet(Bullet bullet) {
        enemyBullets.remove(bullet);
    }
}