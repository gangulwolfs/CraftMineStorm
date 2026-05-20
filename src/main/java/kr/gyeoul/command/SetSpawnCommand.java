package kr.gyeoul.command;

import net.minestom.server.command.builder.Command;
import net.minestom.server.entity.Player;
import net.minestom.server.network.ConnectionManager;

public class SetSpawnCommand extends Command {
    public SetSpawnCommand(String... aliases) {
        super("setspawn", aliases);
        setDefaultExecutor((sender, context) -> {
            if (!(sender instanceof Player)) {
                sender.sendMessage("이 명령어는 플레이어만 사용할 수 있습니다.");
                return;
            }
            Player player = (Player) sender;
            ConnectionManager connectionManager = new ConnectionManager();
            connectionManager.getConfigPlayers().forEach(p -> p.setRespawnPoint(player.getPosition()));
            sender.sendMessage("현재 위치가 스폰 위치로 설정되었습니다.");
        });
    }
}
