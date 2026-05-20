package kr.gyeoul.command;

import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.minestom.server.network.ConnectionManager;

public class GameModeCommand extends Command{
    public GameModeCommand(String... aliases) {
        super("gamemode", aliases);
        setDefaultExecutor((sender, context) -> {
            if(!(sender instanceof Player) && aliases.length >= 2){
                targetChage(sender, aliases);
            }
            if(aliases.length >= 2){
                targetChage(sender, aliases);
                return;
            }
            if(!checkNull(sender, (Player) sender, aliases)){
                return;
            }
            Player player = (Player) sender;
            player.setGameMode(GameMode.valueOf(aliases[0].toUpperCase()));
            player.sendMessage(player.getName() + " 플레이어 게임모드" + GameMode.valueOf(aliases[1].toUpperCase()).name() + " 로 변경완료.");
        });
    }

    private void targetChage(CommandSender sender, String[] aliases) {
        Player target = new ConnectionManager().getOnlinePlayerByUsername(aliases[1]);
        if(!checkNull(sender, target, aliases)){
            return;
        }
        target.setGameMode(GameMode.valueOf(aliases[1].toUpperCase()));
        sender.sendMessage(target.getName() + " 플레이어 게임모드" + GameMode.valueOf(aliases[1].toUpperCase()).name() + " 로 변경완료.");
        return;
    }

    private boolean checkNull(CommandSender sender, Player player, String[] args){
        if(player == null){
            sender.sendMessage("존재하지 않는 플레이어: " + args[0]);
            return false;
        }
        if(GameMode.valueOf(args[1].toUpperCase()).name().isEmpty() || GameMode.valueOf(args[1].toUpperCase()).name().isBlank()){
            sender.sendMessage("사용 가능한 모드: survival, creative, adventure, spectator");
            return false;
        }
        return true;
    }
}
