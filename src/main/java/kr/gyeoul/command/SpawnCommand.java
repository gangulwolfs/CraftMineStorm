package kr.gyeoul.command;

import net.minestom.server.command.builder.Command;
import net.minestom.server.entity.Player;
import net.minestom.server.network.ConnectionManager;

public class SpawnCommand extends Command {

    public SpawnCommand(String... aliases) {
        super("spawn", aliases);
        setDefaultExecutor((sender, context) -> {
            if(!(sender instanceof Player) && aliases.length < 1){
                sender.sendMessage("/spawn <playerName>");
                return;
            } else if (!(sender instanceof Player) && aliases.length > 1) {
                Player target = new ConnectionManager().getOnlinePlayerByUsername(aliases[0]);
                if(target != null){
                    target.teleport(target.getRespawnPoint());
                    sender.sendMessage(target.getName() + " 플레이어 이동 완료.");
                    return;
                }
                sender.sendMessage("존재하지 않는 플레이어: " + aliases[0]);
                return;
            }
            Player player = (Player) sender;
            player.teleport(player.getRespawnPoint());
            sender.sendMessage("스폰 위치로 이동합니다.");
        });
    }

}
