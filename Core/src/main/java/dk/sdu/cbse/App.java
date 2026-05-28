package dk.sdu.cbse;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class App extends Application {

    private final GameData gameData = new GameData(800, 600);

    private final Pane root = new Pane();

    private final GameWorld world = new GameWorld();

    private final List<IEntityProcessorService> entityProcessors = new ArrayList<>();
    private final List<IPostEntityProcessorService> postEntityProcessors = new ArrayList<>();
    private final List<IGamePluginService> gamePlugins = new ArrayList<>();

    @Override
    public void start(Stage stage) {
        root.setPrefSize(gameData.getWidth(), gameData.getHeight());
        root.setStyle("-fx-background-color: black;");

        gamePlugins.add(new PlayerPlugin());
        gamePlugins.add(new EnemyPlugin());

        entityProcessors.add(new PlayerProcessor());
        entityProcessors.add(new EnemyProcessor());
        entityProcessors.add(new ShootingProcessor());
        entityProcessors.add(new BulletProcessor());

        postEntityProcessors.add(new CollisionProcessor());

        for (IGamePluginService plugin : gamePlugins) {
            plugin.start(gameData, world);
        }

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
        for (IEntityProcessorService processor : entityProcessors) {
            processor.process(gameData, world);
        }

        addMissingBulletsToView();

        for (IPostEntityProcessorService processor : postEntityProcessors) {
            processor.process(gameData, world);
        }

        removeDeadBulletsFromView();
    }

    private void addMissingBulletsToView() {
        for (Bullet bullet : world.getPlayerBullets()) {
            if (!root.getChildren().contains(bullet.getView())) {
                root.getChildren().add(bullet.getView());
            }
        }

        for (Bullet bullet : world.getEnemyBullets()) {
            if (!root.getChildren().contains(bullet.getView())) {
                root.getChildren().add(bullet.getView());
            }
        }
    }

    private void removeDeadBulletsFromView() {
        root.getChildren()
                .removeIf(node -> world.getPlayerBullets().stream().noneMatch(bullet -> bullet.getView() == node)
                        && world.getEnemyBullets().stream().noneMatch(bullet -> bullet.getView() == node)
                        && node != world.getPlayer().getView()
                        && node != world.getEnemy().getView());
    }

    public static void main(String[] args) {
        launch(args);
    }
}