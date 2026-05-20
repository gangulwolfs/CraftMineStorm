package ExampleCode;

import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.event.player.PlayerSpawnEvent;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.InstanceManager;
import net.minestom.server.instance.block.Block;

public class startingExample {
    static void main(String[] args) {

        // 1. 마인스톰 서버 초기화
        MinecraftServer minecraftServer = MinecraftServer.init();

        // 2. 월드(인스턴스) 매니저 생성 및 인스턴스 컨테이너 등록
        InstanceManager instanceManager = MinecraftServer.getInstanceManager();
        InstanceContainer instanceContainer = instanceManager.createInstanceContainer();

        // 3. 최소한의 평지 청크 생성기 설정 (플레이어가 딛고 설 땅 만들기)
        // Y=40 높이까지 잔디 블록으로 채우는 간단한 로직입니다.
        instanceContainer.setGenerator(unit -> {
            unit.modifier().fillHeight(0, 40, Block.GRASS_BLOCK);
        });

        // 4. 이벤트 핸들러를 통한 플레이어 접속 및 스폰 처리
        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();

        // [이벤트 A] 플레이어가 서버 구성(Configuration) 단계일 때 인스턴스 지정
        globalEventHandler.addListener(AsyncPlayerConfigurationEvent.class, event -> {
            event.setSpawningInstance(instanceContainer);
        });

        // [이벤트 B] 플레이어가 실제로 스폰할 때 좌표 지정 (Y=42 높이에 스폰)
        globalEventHandler.addListener(PlayerSpawnEvent .class, event -> {
            Player player = event.getPlayer();
            player.setRespawnPoint(new Pos(0, 42, 0));
        });

        // 5. 서버 시작 (포트 25565에서 대기)
        minecraftServer.start("0.0.0.0", 25565);
        System.out.println("마인스톰 서버가 25565 포트에서 성공적으로 시작되었습니다!");
    }
}
