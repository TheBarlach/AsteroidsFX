package dk.sdu.cbse.common;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GameWorld {

    private final List<Entity> entities = new ArrayList<>();

    public List<Entity> getEntities() {
        return entities;
    }

    public void addEntity(Entity entity) {
        if (entity != null && !entities.contains(entity)) {
            entities.add(entity);
        }
    }

    public void removeEntity(Entity entity) {
        entities.remove(entity);
    }

    public List<Entity> getEntitiesByType(EntityType type) {
        return entities.stream()
                .filter(entity -> entity.getType() == type)
                .collect(Collectors.toList());
    }

    public Entity getFirstEntityByType(EntityType type) {
        return entities.stream()
                .filter(entity -> entity.getType() == type)
                .findFirst()
                .orElse(null);
    }

    public void removeDeadEntities() {
        entities.removeIf(entity -> !entity.isAlive());
    }
}