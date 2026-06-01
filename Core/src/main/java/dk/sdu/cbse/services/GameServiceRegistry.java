package dk.sdu.cbse.services;

import dk.sdu.cbse.common.IEntityProcessorService;
import dk.sdu.cbse.common.IGamePluginService;
import dk.sdu.cbse.common.IPostEntityProcessorService;

import java.util.ArrayList;
import java.util.List;

public class GameServiceRegistry {

    private final List<IGamePluginService> gamePlugins = new ArrayList<>();
    private final List<IEntityProcessorService> entityProcessors = new ArrayList<>();
    private final List<IPostEntityProcessorService> postEntityProcessors = new ArrayList<>();

    public List<IGamePluginService> getGamePlugins() {
        return gamePlugins;
    }

    public List<IEntityProcessorService> getEntityProcessors() {
        return entityProcessors;
    }

    public List<IPostEntityProcessorService> getPostEntityProcessors() {
        return postEntityProcessors;
    }

    public void addGamePlugin(IGamePluginService plugin) {
        gamePlugins.add(plugin);
    }

    public void addEntityProcessor(IEntityProcessorService processor) {
        entityProcessors.add(processor);
    }

    public void addPostEntityProcessor(IPostEntityProcessorService processor) {
        postEntityProcessors.add(processor);
    }

    public void clear() {
        gamePlugins.clear();
        entityProcessors.clear();
        postEntityProcessors.clear();
    }
}