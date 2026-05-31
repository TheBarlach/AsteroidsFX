package dk.sdu.cbse;

import dk.sdu.cbse.common.Entity;
import dk.sdu.cbse.common.GameData;
import dk.sdu.cbse.common.GameWorld;
import dk.sdu.cbse.common.IEntityProcessorService;
import dk.sdu.cbse.common.IGamePluginService;
import dk.sdu.cbse.common.IPostEntityProcessorService;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public class App extends Application {

    private final GameData gameData = new GameData(800, 600);
    private final GameWorld world = new GameWorld();

    private final Pane root = new Pane();
    private final Label scoreLabel = new Label();

    private final List<IGamePluginService> gamePlugins = new ArrayList<>();
    private final List<IEntityProcessorService> entityProcessors = new ArrayList<>();
    private final List<IPostEntityProcessorService> postEntityProcessors = new ArrayList<>();

    @Override
    public void start(Stage stage) {
        root.setPrefSize(gameData.getWidth(), gameData.getHeight());
        root.setStyle("-fx-background-color: black;");

        setupScoreLabel();

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

    private void setupScoreLabel() {
        scoreLabel.setTextFill(Color.WHITE);
        scoreLabel.setStyle("-fx-font-size: 20px;");
        scoreLabel.setTranslateX(10);
        scoreLabel.setTranslateY(10);
        root.getChildren().add(scoreLabel);
    }

    private void loadPlugins() {
        ServiceLoader<IGamePluginService> loader = ServiceLoader.load(IGamePluginService.class);

        for (IGamePluginService plugin : loader) {
            gamePlugins.add(plugin);
        }
    }

    private void loadProcessors() {
        ServiceLoader<IEntityProcessorService> entityLoader =
                ServiceLoader.load(IEntityProcessorService.class);

        for (IEntityProcessorService processor : entityLoader) {
            entityProcessors.add(processor);
        }

        ServiceLoader<IPostEntityProcessorService> postEntityLoader =
                ServiceLoader.load(IPostEntityProcessorService.class);

        for (IPostEntityProcessorService processor : postEntityLoader) {
            postEntityProcessors.add(processor);
        }
    }

    private void startGameLoop() {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };

        timer.start();
    }

    private void update() {
        for (IEntityProcessorService processor : entityProcessors) {
            processor.process(gameData, world);
        }

        for (IPostEntityProcessorService processor : postEntityProcessors) {
            processor.process(gameData, world);
        }

        world.removeDeadEntities();

        updateView();
    }

    private void updateView() {
        scoreLabel.setText("Score: " + gameData.getScore());

        for (Entity entity : world.getEntities()) {
            if (!root.getChildren().contains(entity.getView())) {
                root.getChildren().add(entity.getView());
            }
        }

        root.getChildren().removeIf(node ->
                node != scoreLabel
                        && world.getEntities().stream().noneMatch(entity -> entity.getView() == node)
        );
    }

    public static void main(String[] args) {
        launch(args);
    }
}