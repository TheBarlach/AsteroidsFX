module Collision {
    requires Common;

    provides dk.sdu.cbse.common.IPostEntityProcessorService
            with dk.sdu.cbse.collision.CollisionProcessor;
}