package de.potionmc.launcher.cloud;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandManager {

    private Map<String, Command> commands;

    public CommandManager() {
        commands = new HashMap<>();
    }

    public void register(String name, String description, ICommand iCommand) {
        Command command = new Command(name, description, iCommand);
        commands.put(name, command);
    }

    public void unregister(String name) {
        commands.remove(name);
    }

    public boolean exists(String name) {
        if(commands.containsKey(name)) return true;
        return false;
    }

    public void execute(String name, String[] args) {
        Command command = commands.get(name);
        command.getICommand().execute(args);
    }

    public StringBuilder getCommandsInString() {
        StringBuilder strings = new StringBuilder();
        commands.forEach((s, command) -> {
            strings.append(s + " - " + command.getDescription() + "\n");
        });
        return strings;
    }

    public int getCommandCount() {
        return commands.size();
    }

}
