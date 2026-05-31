module Asteroid {
    requires Common;
    requires javafx.controls;

    provides dk.sdu.cbse.common.IGamePluginService
            with dk.sdu.cbse.asteroid.AsteroidPlugin;

    provides dk.sdu.cbse.common.IEntityProcessorService
            with dk.sdu.cbse.asteroid.AsteroidProcessor;
}