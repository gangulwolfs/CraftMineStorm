package kr.gyeoul.instances;

import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.InstanceManager;
import net.minestom.server.instance.block.Block;

public class worldInstance {

    private static InstanceManager instanceManager;
    private static InstanceContainer instanceContainer;

    public worldInstance(InstanceManager instanceManager, InstanceContainer instanceContainer){
        worldInstance.instanceManager = instanceManager;
        worldInstance.instanceContainer = instanceContainer;
    }

    private void defaultWorldGenerator(){
        instanceContainer.setGenerator(unit ->
                unit.modifier().fillHeight(0, 40, Block.GRASS_BLOCK));
    }

    public static InstanceManager getInstanceManager() {
        return instanceManager;
    }

    public static InstanceContainer getInstanceContainer() {
        return instanceContainer;
    }
}
