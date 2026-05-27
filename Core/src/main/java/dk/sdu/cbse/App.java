package dk.sdu.cbse;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class App extends Application {

    private final GameData gameData = new GameData(800, 600);

    private final Pane root = new Pane();

    private final GameWorld world = new GameWorld();

    private final PlayerProcessor playerProcessor = new PlayerProcessor();
    private final EnemyProcessor enemyProcessor = new EnemyProcessor();
    
    private long lastPlayerShot = 0;
    private long lastEnemyShot = 0;

    @Override
    public void start(Stage stage) {
        root.setPrefSize(gameData.getWidth(), gameData.getHeight());
        root.setStyle("-fx-background-color: black;");

        world.setPlayer(new Player(gameData.getWidth() / 2.0, gameData.getHeight() / 2.0));
        world.setEnemy(new Enemy(100, 100));

        root.getChildren().add(world.getPlayer().getView());
        root.getChildren().add(world.getEnemy().getView());

        Scene scene = new Scene(root);

        scene.setOnKeyPressed(event -> gameData.addKey(event.getCode()));
        scene.setOnKeyReleased(event -> gameData.removeKey(event.getCode()));

        stage.setTitle("AsteroidsFX");
        stage.setScene(scene);
        stage.show();

        startGameLoop();
    }

    private void startGameLoop() {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update(now);
            }
        };

        timer.start();
    }

    private void update(long now) {
        handlePlayerInput(now);

        playerProcessor.process(gameData, world);
        enemyProcessor.process(gameData, world);

        handleEnemyShooting(now);

        updateBullets(world.getPlayerBullets());
        updateBullets(world.getEnemyBullets());

        checkCollisions();
    }

    private void handlePlayerInput(long now) {
        if (gameData.isKeyDown(KeyCode.SPACE)) {
            shootPlayerBullet(now);
        }
    }

    private void shootPlayerBullet(long now) {
        if (now - lastPlayerShot < 300_000_000) {
            return;
        }

        Bullet bullet = world.getPlayer().shoot();
        world.addPlayerBullet(bullet);
        root.getChildren().add(bullet.getView());

        lastPlayerShot = now;
    }

    private void handleEnemyShooting(long now) {
        if (now - lastEnemyShot < 1_200_000_000) {
            return;
        }

        Bullet bullet = world.getEnemy().shootAt(world.getPlayer());
        world.addEnemyBullet(bullet);
        root.getChildren().add(bullet.getView());

        lastEnemyShot = now;
    }

    private void updateBullets(List<Bullet> bulletList) {
        List<Bullet> bulletsToRemove = new ArrayList<>();

        for (Bullet bullet : bulletList) {
            bullet.update(gameData.getWidth(), gameData.getHeight());

            if (!bullet.isAlive()) {
                bulletsToRemove.add(bullet);
                root.getChildren().remove(bullet.getView());
            }
        }

        bulletList.removeAll(bulletsToRemove);
    }

    private void checkCollisions() {
        List<Bullet> playerBulletsToRemove = new ArrayList<>();

        for (Bullet bullet : world.getPlayerBullets()) {
            if (bullet.getEntity().collidesWith(world.getEnemy().getEntity())) {
                playerBulletsToRemove.add(bullet);
                root.getChildren().remove(bullet.getView());

                world.getEnemy().respawn(gameData.getWidth(), gameData.getHeight());
            }
        }

        world.getPlayerBullets().removeAll(playerBulletsToRemove);

        List<Bullet> enemyBulletsToRemove = new ArrayList<>();

        for (Bullet bullet : world.getEnemyBullets()) {
            if (bullet.getEntity().collidesWith(world.getPlayer().getEntity())) {
                enemyBulletsToRemove.add(bullet);
                root.getChildren().remove(bullet.getView());

                world.getPlayer().respawn(gameData.getWidth(), gameData.getHeight());
            }
        }

        world.getEnemyBullets().removeAll(enemyBulletsToRemove);
    }

    public static void main(String[] args) {
        launch(args);
    }
}