module Enemy {
    requires Common;
    requires javafx.controls;

    provides dk.sdu.cbse.common.IGamePluginService
            with dk.sdu.cbse.enemy.EnemyPlugin;

    provides dk.sdu.cbse.common.IEntityProcessorService
            with dk.sdu.cbse.enemy.EnemyProcessor;
}