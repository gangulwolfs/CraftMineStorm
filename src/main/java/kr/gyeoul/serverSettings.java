package kr.gyeoul;

import kr.gyeoul.command.*;
import kr.gyeoul.instances.WorldInstance;
import kr.gyeoul.instances.worldType;
import kr.gyeoul.listener.events.MinestomEventListener;
import kr.gyeoul.listener.plugin.PluginLoader;
import kr.gyeoul.util.JsonManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import net.minestom.server.MinecraftServer;
import net.minestom.server.instance.InstanceContainer;

import java.io.File;
import java.io.InputStream;
import java.net.ServerSocket;
import java.nio.file.Files;
import java.nio.file.Path;

//핵심은 serverSettings에 static을 사용해서 통합으로 불러올 수 있게 하는거.
//싱글톤 형태로 대체.
public final class ServerSettings {
    private static ServerSettings instance;
    private MinecraftServer minecraftServer;
    private InstanceContainer instanceContainer;
    private final ComponentLogger logger = MinecraftServer.LOGGER;
    private WorldInstance worldInstance;
    private MinestomEventListener eventListener;

    private ServerSettings() {
        // MinecraftServer 초기화
        minecraftServer = MinecraftServer.init();
        instanceContainer = MinecraftServer.getInstanceManager().createInstanceContainer();
        worldInstance = new WorldInstance(MinecraftServer.getInstanceManager(), instanceContainer);
        eventListener = new MinestomEventListener(minecraftServer, instanceContainer);
    }

    public static ServerSettings getInstance() {
        if (instance == null){
            instance = new ServerSettings();
        }
        return instance;
    }

    private void setup(){
        createDefaultServerConfig();
        JsonManager.createFolder("plugins");
        if(!createEula()){
            return;
        }
        worldInstance.worldGeneratorSelection(worldType.TEST, "world");
    }


    public void start() {
        // Server startup logic here
        try {
            if (new ServerSocket(25565).isClosed()) {
                logger.info(Component.text("해당 포트가 이미 사용 중입니다. 서버를 시작합니다."));
            } else {
                logger.info(Component.text("해당 포트가 이미 사용 중입니다. 서버를 시작할 수 없습니다."));
                return;
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        setup();
        PluginLoader.getInstance().start();
        commandRegister();
        minecraftServer.start("0.0.0.0", 25565);
        logger.info(Component.text("마인크래프트 서버가 시작되었습니다."));
    }

    private void commandRegister() {
        MinecraftServer.getCommandManager().register(new ServerCommand("help"));
        MinecraftServer.getCommandManager().register(new GameModeCommand());
        MinecraftServer.getCommandManager().register(new SetSpawnCommand());
        MinecraftServer.getCommandManager().register(new SpawnCommand());
        MinecraftServer.getCommandManager().register(new TeleportCommand());
    }


    //EULA 생성용.
    private boolean createEula(){
        Path configPath = Path.of(System.getProperty("user.dir")).resolve("eula.json");
        File file = new File(configPath.toUri());
        if(!file.exists()){
            try (InputStream inputStream = Main.class.getResourceAsStream("/eula.json")) {
                if (inputStream != null) {
                    Files.copy(inputStream, configPath);
                    logger.info(Component.text("EULA 파일이 생성되었습니다. eula.json 파일을 열어 EULA에 동의해주세요."));
                    return false;
                } else {
                    logger.warn(Component.text("EULA 파일을 찾을 수 없습니다. 서버를 시작할 수 없습니다."));
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    //configPath.toUri()
    //config.json 생성용.
    private void createDefaultServerConfig(){
        Path configPath = Path.of(System.getProperty("user.dir")).resolve("config.json");
        File file = new File(configPath.toUri());
        if(!file.exists()){
            try (InputStream inputStream = Main.class.getResourceAsStream("/config.json")) {
                if (inputStream != null) {
                    Files.copy(inputStream, configPath);
                    logger.info(Component.text("config.json 파일이 생성되었습니다."));
                    return;
                } else {
                    logger.info(Component.text("config.json 파일을 찾을 수 없습니다. 치명적인 오류가 발생합니다."));
                    throw new RuntimeException("config.json 파일을 찾을 수 없습니다. 치명적인 오류가 발생합니다.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public MinecraftServer getMinecraftServer() {
        return minecraftServer;
    }

    public MinestomEventListener getEventListener() {
        return eventListener;
    }

    public InstanceContainer getInstanceContainer() {
        return instanceContainer;
    }

    public ComponentLogger getLogger() {
        return logger;
    }
}
