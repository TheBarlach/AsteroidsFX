module Bullet {
    requires Common;
    requires javafx.controls;

    provides dk.sdu.cbse.common.IEntityProcessorService
            with dk.sdu.cbse.bullet.ShootingProcessor,
                 dk.sdu.cbse.bullet.BulletProcessor;
}