module Core {
    requires javafx.controls;

    exports dk.sdu.cbse;

    uses dk.sdu.cbse.IGamePluginService;
    uses dk.sdu.cbse.IEntityProcessorService;
    uses dk.sdu.cbse.IPostEntityProcessorService;

    provides dk.sdu.cbse.IGamePluginService
            with dk.sdu.cbse.PlayerPlugin,
            dk.sdu.cbse.EnemyPlugin,
            dk.sdu.cbse.AsteroidPlugin;

    provides dk.sdu.cbse.IEntityProcessorService
            with dk.sdu.cbse.PlayerProcessor,
            dk.sdu.cbse.EnemyProcessor,
            dk.sdu.cbse.ShootingProcessor,
            dk.sdu.cbse.BulletProcessor,
            dk.sdu.cbse.AsteroidProcessor;

    provides dk.sdu.cbse.IPostEntityProcessorService
            with dk.sdu.cbse.CollisionProcessor;
}