package de.potionmc.launcher.cloud.commands;

import de.potionmc.launcher.Main;
import de.potionmc.launcher.cloud.ICommand;
import de.potionmc.launcher.commandinterface.Loggers;
import de.potionmc.launcher.commandinterface.LoggersType;

//Author Louispix
//Uhr zeit 23:22
public class Version_CMD implements ICommand {


    @Override
    public void execute(String[] args) {
        new Loggers(LoggersType.INFO, Main.useColorSystem, "The Service is on BlazShield");
        new Loggers(LoggersType.INFO, Main.useColorSystem, "Version [OBSIDIAN-0.0.1]");
    }
}
