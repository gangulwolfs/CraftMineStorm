package kr.gyeoul;

import kr.gyeoul.util.JsonManager;
import net.minestom.server.MinecraftServer;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public final class serverSettings {
    private static MinecraftServer minecraftServer;

    public serverSettings() {
        // MinecraftServer 초기화
        minecraftServer = MinecraftServer.init();
        firstStart();
    }

    private void firstStart(){
        if(!createEula()){

            //대충 서버 중단.

        }
    }


    public void start() {
        // Server startup logic here
        minecraftServer.start("0.0.0.0", 25565);
    }

    private boolean createEula(){
        Path configPath = Path.of(System.getProperty("user.dir")).resolve("eula.json");
        File file = new File(configPath.toUri());
        if(!file.exists()){
            try (InputStream inputStream = Main.class.getResourceAsStream("/eula.json")) {
                if (inputStream != null) {

                    Files.copy(inputStream, configPath);
                    return true;
                    //생성됨 메시지.
                } else {
                    //jar안에 찾을 수 없는 경우.ㅇ.ㅇ

                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //configPath.toUri()
    private void createDefaultServerConfig(){
        Path configPath = Path.of(System.getProperty("user.dir")).resolve("config.json");
        File file = new File(configPath.toUri());
        if(!file.exists()){
            try (InputStream inputStream = Main.class.getResourceAsStream("/config.json")) {
                if (inputStream != null) {

                    Files.copy(inputStream, configPath);
                    //생성됨 메시지.
                } else {
                    //jar안에 찾을 수 없는 경우.ㅇ.ㅇ
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public static MinecraftServer getMinecraftServer() {
        return minecraftServer;
    }

}
