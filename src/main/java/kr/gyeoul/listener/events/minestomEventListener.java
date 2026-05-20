package kr.gyeoul.listener.events;

import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.PlayerSkin;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.event.player.PlayerSkinInitEvent;
import net.minestom.server.event.player.PlayerSpawnEvent;
import net.minestom.server.instance.InstanceContainer;

public class minestomEventListener {

    private MinecraftServer minecraftServer;
    private GlobalEventHandler globalEventHandler;

    public minestomEventListener(MinecraftServer minecraftServer, InstanceContainer instanceContainer){
        this.minecraftServer = minecraftServer;
        this.globalEventHandler = MinecraftServer.getGlobalEventHandler();
        //플레이어 기본 접속 허용 이벤트
        globalEventHandler.addListener(AsyncPlayerConfigurationEvent.class, event -> {
            event.setSpawningInstance(instanceContainer);
        });
        //플레이어 스킨 적용 이벤트
        globalEventHandler.addListener(PlayerSkinInitEvent.class, event -> {
            Player player = event.getPlayer();
            player.setSkin(PlayerSkin.fromUuid(player.getUuid().toString()));
        });
        //플레이어 접속후 스폰 허용 이벤트
        globalEventHandler.addListener(PlayerSpawnEvent.class, event -> {
            Player player = event.getPlayer();
            player.setRespawnPoint(new Pos(0, 42, 0));
        });
    }

}
