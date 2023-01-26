package de.potionmc.launcher.commandinterface;


import de.potionmc.launcher.Main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CommandHandler implements ICommandHandler {
  private List<Command> commandList = new ArrayList<>();
  
  public void registerCommands(Command command) {
    this.commandList.add(command);
  }

  @Override
  public void registerCommands(net.minestom.server.command.builder.Command command1) {
  }

  public boolean executeline(String line) {
    if (line.contains(" ")) {
      List<String> command = Arrays.asList(line.split(" "));
      List<String> arguments = new ArrayList<>();
      for (String args : command) {
        if (!args.equalsIgnoreCase(command.get(0)))
          arguments.add(args); 
      } 
      if (getCommandByName(command.get(0)) == null) {
        new Loggers(LoggersType.WARN, Main.useColorSystem, "The command was not found please type HELP to get help");
        return false;
      } 
      Command commandtoExecute = getCommandByName(command.get(0));
      commandtoExecute.execute(line, arguments);
    } else {
      if (line.length() == 0) {
        new Loggers(LoggersType.WARN, Main.useColorSystem, "The command was not found please type HELP to get help");
        return false;
      } 
      if (getCommandByName(line) == null) {
        new Loggers(LoggersType.WARN, Main.useColorSystem, "The command was not found please type HELP to get help");
        return false;
      } 
      Command commandtoExecute = getCommandByName(line);
      commandtoExecute.execute(line, new ArrayList<>());
    } 
    return true;
  }
  
  public Command getCommandByName(String command) {
    for (Command commands : this.commandList) {
      if (commands.getName().equalsIgnoreCase(command))
        return commands; 
      if (Arrays.<String>asList(commands.getAliases()).contains(command))
        return commands; 
    } 
    return null;
  }
}
