package kr.gyeoul.util;

import kr.gyeoul.ServerSettings;
import kr.gyeoul.util.JsonManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Path;

public class JsonManager {

    private static Path workingDirectory = Path.of(System.getProperty("user.dir"));
    //아래는 example임. 위가 해당 jar실행중일 경우 실행경로고 아래가 그 안에서 config.json 경로 찾는거.
//    private static Path configPath = workingDirectory.resolve("config.json");
    private static final Logger logger = LoggerFactory.getLogger(JsonManager.class);

    public static void createFolder(String file){
        File folder = new File(workingDirectory.toString() + File.separator + file);
        if(!folder.exists()){
            try{
                if(folder.mkdirs()){
                    logger.info("§a폴더가 성공적으로 생성되었습니다.");
                } else {
                    logger.info("§a폴더 생성 실패.");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static String[] checkDirJsonList(String path){
        File folder = new File(path);
        if(folder.exists() && folder.isDirectory()){
            return folder.list((dir, name) -> name.endsWith(".json"));
        }
        return new String[0];
    }

    public static boolean JsonNotExists(String path, String name){
        //File.separator -> 운영체제에 맞게 자동 변환. ex) 윈도우 -> \
        return new File( path + File.separator + name + ".json").exists();
    }

//    public String getPath() {
//        return path;
//    }

//    public void setPath(String path) {
//        this.path = path;
//    }
}
