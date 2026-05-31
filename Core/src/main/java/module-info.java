module Core {
    requires javafx.controls;
    requires Common;

    uses dk.sdu.cbse.common.IGamePluginService;
    uses dk.sdu.cbse.common.IEntityProcessorService;
    uses dk.sdu.cbse.common.IPostEntityProcessorService;

    exports dk.sdu.cbse;
}