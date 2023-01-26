package de.potionmc.launcher.commandinterface;

import java.util.List;

public abstract class Command {
  private final String name;
  
  private final String[] aliases;
  
  private final String description;
  
  public Command(String name, String description, String... aliases) {
    this.name = name;
    this.description = description;
    this.aliases = aliases;
  }
  
  public abstract boolean execute(String paramString, List<String> paramList);
  
  public String getName() {
    return this.name;
  }
  
  public String[] getAliases() {
    return this.aliases;
  }
  
  public String getDescription() {
    return this.description;
  }
}
