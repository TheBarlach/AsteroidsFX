module Core {
    requires javafx.controls;
    requires Common;
    requires spring.context;

    uses dk.sdu.cbse.common.IGamePluginService;
    uses dk.sdu.cbse.common.IEntityProcessorService;
    uses dk.sdu.cbse.common.IPostEntityProcessorService;

    exports dk.sdu.cbse;

    opens dk.sdu.cbse.config to spring.core, spring.beans, spring.context;
}