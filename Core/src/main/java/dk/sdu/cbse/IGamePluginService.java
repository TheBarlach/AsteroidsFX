package dk.sdu.cbse;

public interface IGamePluginService {

    void start(GameData gameData, GameWorld world);

    void stop(GameData gameData, GameWorld world);
}