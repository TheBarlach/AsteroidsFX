module Player {
    requires Common;
    requires javafx.controls;

    provides dk.sdu.cbse.common.IGamePluginService
            with dk.sdu.cbse.player.PlayerPlugin;

    provides dk.sdu.cbse.common.IEntityProcessorService
            with dk.sdu.cbse.player.PlayerProcessor;
}