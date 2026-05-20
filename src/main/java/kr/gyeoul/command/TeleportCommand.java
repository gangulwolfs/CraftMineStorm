package kr.gyeoul.command;

import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.network.ConnectionManager;

import java.util.Arrays;
import java.util.List;

public class TeleportCommand extends Command {
    public TeleportCommand(String... aliases) {
        super("tp", aliases);
        setDefaultExecutor((sender, context) -> {
            if(!(sender instanceof Player) && aliases.length >= 5){
                if(!(checkDouble(aliases))){
                    return;
                }
                consoleTargetTeleport(new Pos(Double.parseDouble(aliases[0]), Double.parseDouble(aliases[1]), Double.parseDouble(aliases[3])), sender, aliases);
            }
            Player player = (Player) sender;
            if(checkDouble(aliases)){
                Pos location = new Pos(Double.parseDouble(aliases[0]), Double.parseDouble(aliases[1]), Double.parseDouble(aliases[3]));
                player.teleport(location);
                player.sendMessage("플레이어를 좌표: " + location.toString() + " 로 이동완료.");
                return;
            }
            if(aliases.length < 2){
                Player target = new ConnectionManager().getOnlinePlayerByUsername(aliases[0]);
                player.teleport(target.getPosition());
                player.sendMessage("플레이어를 좌표: " + target.getPosition().toString() + " 로 이동완료.");
            }
        });
    }

    private boolean checkDouble(String[] aliases) {
        List<String> list = Arrays.stream(aliases).toList().stream().filter(n -> Double.parseDouble(n) != 0).toList();
        return list.size() == 3;
    }

    private void consoleTargetTeleport(Pos Location, CommandSender sender, String[] aliases) {
        Player target = new ConnectionManager().getOnlinePlayerByUsername(aliases[1]);
        if(!consoleCheckNull(Location, sender, target)){
            return;
        }
        target.teleport(Location);
        sender.sendMessage(target.getName() + " 플레이어를 좌표: " + Location.toString() + " 로 이동완료.");
        return;
    }

    private boolean consoleCheckNull(Pos Location, CommandSender sender, Player target){
        if(Location == null){
            sender.sendMessage("존재하지 않는 위치: " + Location.toString());
            return false;
        }
        if(!(target.isOnline())){
            sender.sendMessage("온라인 플레이어가 아니라서 텔레포트 불가능: " + target.getName());
            return false;
        }
        return true;
    }

}
