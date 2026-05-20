package kr.gyeoul.instances;

import kr.gyeoul.instances.light.LightEngine;
import kr.gyeoul.serverSettings;
import net.hollowcube.polar.PolarLoader;
import net.hollowcube.polar.PolarReader;
import net.hollowcube.polar.PolarWorld;
import net.kyori.adventure.text.Component;
import net.minestom.server.MinecraftServer;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.InstanceManager;
import net.minestom.server.instance.block.Block;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class worldInstance {

    private InstanceManager instanceManager;
    private InstanceContainer instanceContainer;

    public worldInstance(InstanceManager instanceManager, InstanceContainer instanceContainer){
        this.instanceManager = instanceManager;
        // instance 역할임.
        this.instanceContainer = instanceContainer;
        polarWorldLoader("world");
    }

    public void worldGeneratorSelection(worldType worldType, String worldName){
        switch (worldType) {
            case TEST -> {
                testWorldGenerator(worldName);
            }
            case DEFAULT -> {
                defaultWorldGenerator(worldName);
            }
            case FLAT -> {
                flatWorldGenerator(worldName);
            }
            case VOID -> {
                voidWorldGenerator(worldName);
            }
            default -> testWorldGenerator(worldName);
        }
    }

    private void defaultWorldGenerator(String worldName) {

    }

    private void voidWorldGenerator(String worldName) {

    }

    private void flatWorldGenerator(String worldName) {

    }

    private void testWorldGenerator(String worldName){
        try{
            instanceContainer.setGenerator(unit ->
                    unit.modifier().fillHeight(-60, -40, Block.GRASS_BLOCK));
            lightEngine();
            polarFormatSaveChunk(worldName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void polarWorldLoader(String worldName) {
        if(Path.of(worldName + File.separator + "world.polar").toFile().exists()){
            serverSettings.getLogger().info(Component.text("Polar world file found. Loading world: " + worldName));
            try{
                // Read the Polar file
                byte[] worldData = Files.readAllBytes(Path.of(worldName + File.separator + worldName + ".polar"));
                PolarWorld polarWorld = PolarReader.read(worldData);
                // Load it into an instance
                instanceContainer.setChunkLoader(new PolarLoader(polarWorld));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            serverSettings.getLogger().warn(Component.text("Polar world file not found for world: " + worldName + ". Please ensure the file exists at the specified path."));
        }
    }

    private void lightEngine(){
        //original style.
        //        instanceContainer.setChunkSupplier(LightingChunk::new);
        //lib chunk.
        LightEngine lightEngine = new LightEngine();
        lightEngine.recalculateInstance(instanceContainer);
    }

    //using polar format

    public void polarFormatSaveChunk(String worldName) throws IOException {
         this.instanceContainer.setChunkLoader(new PolarLoader(Path.of(File.separator + worldName + File.separator + worldName + ".polar")));
        // Saving
        this.instanceContainer.saveChunksToStorage();
    }

}
