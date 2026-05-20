package kr.gyeoul;

import kr.gyeoul.instances.worldInstance;
import kr.gyeoul.instances.worldType;
import kr.gyeoul.listener.events.minestomEventListener;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import net.minestom.server.MinecraftServer;
import net.minestom.server.instance.InstanceContainer;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public final class serverSettings {
    private static MinecraftServer minecraftServer;
    private static InstanceContainer instanceContainer;
    private static final ComponentLogger logger = MinecraftServer.LOGGER;
    private static worldInstance worldInstance;
    private static minestomEventListener eventListener;

    public serverSettings() {
        // MinecraftServer 초기화
        minecraftServer = MinecraftServer.init();
        instanceContainer = MinecraftServer.getInstanceManager().createInstanceContainer();
        worldInstance = new worldInstance(MinecraftServer.getInstanceManager(), instanceContainer);
        eventListener = new minestomEventListener(minecraftServer, instanceContainer);
    }

    private void firstStart(){
        createDefaultServerConfig();
        if(!createEula()){
            return;
        }
        worldInstance.worldGeneratorSelection(worldType.TEST, "world");
    }


    public void start() {
        // Server startup logic here
        firstStart();
        logger.info(Component.text("마인크래프트 서버가 시작되었습니다."));
        minecraftServer.start("0.0.0.0", 25565);
    }

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

    public static MinecraftServer getMinecraftServer() {
        return minecraftServer;
    }

    public static minestomEventListener getEventListener() {
        return eventListener;
    }

    public static InstanceContainer getInstanceContainer() {
        return instanceContainer;
    }

    public static ComponentLogger getLogger() {
        return logger;
    }
}
