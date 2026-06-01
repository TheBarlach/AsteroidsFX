package dk.sdu.cbse.common;

public interface IGamePluginService {

    void start(GameData gameData, GameWorld world);

    void stop(GameData gameData, GameWorld world);
}