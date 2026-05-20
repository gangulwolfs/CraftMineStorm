package kr.gyeoul.command;

import net.minestom.server.command.builder.Command;

public class HelpCommand extends Command {
    public HelpCommand(String name, String... aliases) {
        super(name, aliases);
        setDefaultExecutor((sender, context) -> {
            sender.sendMessage("도움말 명령어입니다. 사용 가능한 명령어 목록:");
            sender.sendMessage("/help - 도움말을 표시합니다.");
            sender.sendMessage("/spawn - 스폰 위치로 이동합니다.");
            sender.sendMessage("/setspawn - 현재 위치를 스폰 위치로 설정합니다.");
            sender.sendMessage("/gamemode <mode> - 게임 모드를 변경합니다. (survival, creative, adventure)");
            sender.sendMessage("/tp <player> - 다른 플레이어에게 텔레포트합니다.");
        });
    }

}
