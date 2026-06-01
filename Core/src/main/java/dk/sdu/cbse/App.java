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
import dk.sdu.cbse.config.GameConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.lang.module.Configuration;
import java.lang.module.ModuleFinder;
import java.lang.module.ModuleReference;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.ServiceLoader;
import dk.sdu.cbse.services.GameServiceRegistry;

public class App extends Application {

    private AnnotationConfigApplicationContext context;

    private GameData gameData;
    private GameWorld world;

    private final Pane root = new Pane();
    private final Label scoreLabel = new Label();

    private GameServiceRegistry serviceRegistry;

    private ModuleLayer pluginLayer;

    @Override
    public void start(Stage stage) {
        context = new AnnotationConfigApplicationContext(GameConfig.class);

        gameData = context.getBean(GameData.class);
        world = context.getBean(GameWorld.class);
        serviceRegistry = context.getBean(GameServiceRegistry.class);

        root.setPrefSize(gameData.getWidth(), gameData.getHeight());
        root.setStyle("-fx-background-color: black;");

        setupScoreLabel();

        loadPluginLayer();
        loadPlugins();
        loadProcessors();

        for (IGamePluginService plugin : serviceRegistry.getGamePlugins()) {
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

    private void loadPluginLayer() {
        try {
            Path pluginsDir = Path.of("plugins");

            if (!Files.exists(pluginsDir)) {
                Files.createDirectories(pluginsDir);
            }

            ModuleFinder finder = ModuleFinder.of(pluginsDir);

            Set<String> bootModuleNames = ModuleLayer.boot()
                    .modules()
                    .stream()
                    .map(Module::getName)
                    .collect(Collectors.toSet());

            Set<String> pluginModuleNames = finder.findAll()
                    .stream()
                    .map(ModuleReference::descriptor)
                    .map(descriptor -> descriptor.name())
                    .filter(moduleName -> !bootModuleNames.contains(moduleName))
                    .collect(Collectors.toSet());

            if (pluginModuleNames.isEmpty()) {
                pluginLayer = null;
                return;
            }

            ModuleLayer parentLayer = ModuleLayer.boot();

            Configuration configuration = parentLayer.configuration()
                    .resolve(finder, ModuleFinder.of(), pluginModuleNames);

            pluginLayer = parentLayer.defineModulesWithOneLoader(
                    configuration,
                    ClassLoader.getSystemClassLoader());

            System.out.println("Loaded plugin modules: " + pluginModuleNames);
        } catch (Exception exception) {
            pluginLayer = null;
            System.out.println("Could not load plugin layer: " + exception.getMessage());
        }
    }

    private void loadPlugins() {
        ServiceLoader<IGamePluginService> loader;

        if (pluginLayer != null) {
            loader = ServiceLoader.load(pluginLayer, IGamePluginService.class);
        } else {
            loader = ServiceLoader.load(IGamePluginService.class);
        }

        for (IGamePluginService plugin : loader) {
            serviceRegistry.addGamePlugin(plugin);
        }
    }

    private void loadProcessors() {
        ServiceLoader<IEntityProcessorService> entityLoader;

        if (pluginLayer != null) {
            entityLoader = ServiceLoader.load(pluginLayer, IEntityProcessorService.class);
        } else {
            entityLoader = ServiceLoader.load(IEntityProcessorService.class);
        }

        for (IEntityProcessorService processor : entityLoader) {
            serviceRegistry.addEntityProcessor(processor);
        }

        ServiceLoader<IPostEntityProcessorService> postEntityLoader;

        if (pluginLayer != null) {
            postEntityLoader = ServiceLoader.load(pluginLayer, IPostEntityProcessorService.class);
        } else {
            postEntityLoader = ServiceLoader.load(IPostEntityProcessorService.class);
        }

        for (IPostEntityProcessorService processor : postEntityLoader) {
            serviceRegistry.addPostEntityProcessor(processor);
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
        for (IEntityProcessorService processor : serviceRegistry.getEntityProcessors()) {
            processor.process(gameData, world);
        }

        for (IPostEntityProcessorService processor : serviceRegistry.getPostEntityProcessors()) {
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

        root.getChildren().removeIf(node -> node != scoreLabel
                && world.getEntities().stream().noneMatch(entity -> entity.getView() == node));
    }

    @Override
    public void stop() {
        if (context != null) {
            context.close();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}