module Collision {
    requires Common;
    requires spring.web;

    provides dk.sdu.cbse.common.IPostEntityProcessorService
            with dk.sdu.cbse.collision.CollisionProcessor;
}