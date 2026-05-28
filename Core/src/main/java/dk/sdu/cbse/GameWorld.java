package dk.sdu.cbse;

import java.util.ArrayList;
import java.util.List;

public class GameWorld {

    private Player player;
    private Enemy enemy;

    private final List<Bullet> playerBullets = new ArrayList<>();
    private final List<Bullet> enemyBullets = new ArrayList<>();

    private final List<Entity> entities = new ArrayList<>();

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        if (this.player != null) {
            entities.remove(this.player.getEntity());
        }

        this.player = player;

        if (player != null && !entities.contains(player.getEntity())) {
            entities.add(player.getEntity());
        }
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public void setEnemy(Enemy enemy) {
        if (this.enemy != null) {
            entities.remove(this.enemy.getEntity());
        }

        this.enemy = enemy;

        if (enemy != null && !entities.contains(enemy.getEntity())) {
            entities.add(enemy.getEntity());
        }
    }

    public List<Bullet> getPlayerBullets() {
        return playerBullets;
    }

    public List<Bullet> getEnemyBullets() {
        return enemyBullets;
    }

    public void addPlayerBullet(Bullet bullet) {
        playerBullets.add(bullet);
        entities.add(bullet.getEntity());
    }

    public void addEnemyBullet(Bullet bullet) {
        enemyBullets.add(bullet);
        entities.add(bullet.getEntity());
    }

    public void removePlayerBullet(Bullet bullet) {
        playerBullets.remove(bullet);
        entities.remove(bullet.getEntity());
    }

    public void removeEnemyBullet(Bullet bullet) {
        enemyBullets.remove(bullet);
        entities.remove(bullet.getEntity());
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public void removeEntity(Entity entity) {
        entities.remove(entity);
    }
}