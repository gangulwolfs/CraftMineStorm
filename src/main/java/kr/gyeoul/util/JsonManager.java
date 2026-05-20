package kr.gyeoul.util;

import kr.gyeoul.util.JsonManager;

import java.io.File;
import java.nio.file.Path;

public class JsonManager {

    Path workingDirectory = Path.of(System.getProperty("user.dir"));
    //아래는 example임. 위가 해당 jar실행중일 경우 실행경로고 아래가 그 안에서 config.json 경로 찾는거.
    Path configPath = workingDirectory.resolve("config.json");

    public void createFolder(String file){
        File folder = new File(file);
        if(!folder.exists()){
            try{
                if(folder.mkdirs()){
//                    ProEcoShop.getInstance().getLogger().info("§a폴더가 성공적으로 생성되었습니다.");
                } else {
//                    ProEcoShop.getInstance().getLogger().info("§a폴더 생성 실패.");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public String[] checkDirJsonList(String path){
        File folder = new File(path);
        if(folder.exists() && folder.isDirectory()){
            return folder.list((dir, name) -> name.endsWith(".json"));
        }
        return new String[0];
    }

    public boolean JsonNotExists(String path, String name){
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
