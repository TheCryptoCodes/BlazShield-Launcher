package de.potionmc.launcher.commandinterface;

import net.minestom.server.command.builder.Command;

public interface ICommandHandler {
  void registerCommands(Command paramCommand);
  
  boolean executeline(String paramString);
}
