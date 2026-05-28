package dk.sdu.cbse;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

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

        loadPlugins();
        loadProcessors();

        for (IGamePluginService plugin : gamePlugins) {
            plugin.start(gameData, world);
        }

        updateView();

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

        for (IPostEntityProcessorService processor : postEntityProcessors) {
            processor.process(gameData, world);
        }

        updateView();
    }

    private void updateView() {
        for (Entity entity : world.getEntities()) {
            if (!root.getChildren().contains(entity.getView())) {
                root.getChildren().add(entity.getView());
            }
        }

        root.getChildren().removeIf(node -> world.getEntities().stream().noneMatch(entity -> entity.getView() == node));
    }

    private void loadPlugins() {
        ServiceLoader<IGamePluginService> loader = ServiceLoader.load(IGamePluginService.class);

        for (IGamePluginService plugin : loader) {
            gamePlugins.add(plugin);
        }
    }

    private void loadProcessors() {
        ServiceLoader<IEntityProcessorService> entityLoader = ServiceLoader.load(IEntityProcessorService.class);

        for (IEntityProcessorService processor : entityLoader) {
            entityProcessors.add(processor);
        }

        entityProcessors.sort((a, b) -> Integer.compare(getProcessorPriority(a), getProcessorPriority(b)));

        ServiceLoader<IPostEntityProcessorService> postEntityLoader = ServiceLoader
                .load(IPostEntityProcessorService.class);

        for (IPostEntityProcessorService processor : postEntityLoader) {
            postEntityProcessors.add(processor);
        }
    }

    private int getProcessorPriority(IEntityProcessorService processor) {
        if (processor instanceof PlayerProcessor) {
            return 10;
        }

        if (processor instanceof EnemyProcessor) {
            return 20;
        }

        if (processor instanceof ShootingProcessor) {
            return 30;
        }

        if (processor instanceof BulletProcessor) {
            return 40;
        }

        if (processor instanceof AsteroidProcessor) {
            return 50;
        }
        
        return 100;
    }

    public static void main(String[] args) {
        launch(args);
    }
}