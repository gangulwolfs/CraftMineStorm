package kr.gyeoul.command;

import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.CommandExecutor;

public class ServerCommand extends Command {

    public ServerCommand(String name, String... aliases) {
        super(name, aliases);
    }

    public ServerCommand(String name) {
        super(name);
    }

    public ServerCommand(String name, String[] aliases, CommandExecutor executor) {
        super(name, aliases);
        setDefaultExecutor(executor);
    }




}
