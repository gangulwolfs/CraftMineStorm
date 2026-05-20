package kr.gyeoul.listener.plugin;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PluginLoader {
    private final List<MinestomPlugin> loadedPlugins = new ArrayList<>();
    private static PluginLoader instance;
    private final Logger logger = LoggerFactory.getLogger(PluginLoader.class);

    private PluginLoader(){
        loadPlugins();
    }

    public static PluginLoader getInstance(){
        if (instance == null){
            instance = new PluginLoader();
        }
        return instance;
    }

    private void loadPlugins(){
        File[] files = Path.of(System.getProperty("user.dir")).toFile().listFiles((dir, name) -> name.endsWith(".jar"));
        if (files == null) return;

        for (File file : files) {
            try (JarFile jarFile = new JarFile(file)) {
                // JAR 내부의 plugin.json 파일 검색
                JarEntry entry = jarFile.getJarEntry("plugin.json");
                if (entry == null) {
                    System.out.println("[Loader] plugin.json 파일이 없는 플러그인입니다: " + file.getName());
                    continue;
                }

                // json에서 메인 클래스 경로(main) 읽기
                JsonObject json = JsonParser.parseReader(new InputStreamReader(jarFile.getInputStream(entry))).getAsJsonObject();
                String mainClassName = json.get("main").getAsString();

                // URLClassLoader를 통해 시스템에 JAR 로드
                URL[] urls = { file.toURI().toURL() };
                URLClassLoader classLoader = new URLClassLoader(urls, this.getClass().getClassLoader());

                // 클래스를 로드하고 MinestomPlugin 인터페이스로 캐스팅하여 객체 생성
                Class<?> pluginClass = Class.forName(mainClassName, true, classLoader);

                if (MinestomPlugin.class.isAssignableFrom(pluginClass)) {
                    enablePlugins((MinestomPlugin) pluginClass.getDeclaredConstructor().newInstance());
                }

            } catch (Exception e) {
                logger.warn("플러그인 로드 실패: " + file.getName());
                e.printStackTrace();
            }
        }
    }

    public void enablePlugins(MinestomPlugin plugin){
        plugin.onEnable();
        loadedPlugins.add(plugin);
    }

    public void start() {
        logger.info("플러그인 로딩 완료. 총 " + loadedPlugins.size() + "개의 플러그인이 활성화되었습니다.");
    }
}
