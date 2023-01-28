package de.potionmc.launcher.interfaces;

import de.potionmc.launcher.Main;
import de.potionmc.launcher.cloud.CommandManager;
import de.potionmc.launcher.cloud.ICommand;
import de.potionmc.launcher.commandinterface.Loggers;
import de.potionmc.launcher.commandinterface.LoggersType;

import java.util.List;

//Author Louispix
//Uhr zeit 23:22
public class Setup implements ICommand {
    @Override
    public void execute(List<String> args) {
        new Loggers(LoggersType.INFO, Main.useColorSystem, "The following Commands are registered:");
        new Loggers(LoggersType.INFO, Main.useColorSystem, " ");

        new Loggers(LoggersType.INFO, Main.useColorSystem, "'version' Aliases: [version, ver] - the Cloud version is online!");
        new Loggers(LoggersType.INFO, Main.useColorSystem, "'start' Aliases: [start, run] - the service start command!");
        new Loggers(LoggersType.INFO, Main.useColorSystem, "'stop' Aliases: [stop, shutdown] - Cloud stop command!");
        new Loggers(LoggersType.INFO, Main.useColorSystem, " ");

        new Loggers(LoggersType.INFO, Main.useColorSystem, "Threads: " + Runtime.getRuntime().availableProcessors());
        new Loggers(LoggersType.INFO, Main.useColorSystem, "OS System: " + System.getProperty("os.name"));
        new Loggers(LoggersType.INFO, Main.useColorSystem, "Support: http://discord.blazmc.de");
    }
}
