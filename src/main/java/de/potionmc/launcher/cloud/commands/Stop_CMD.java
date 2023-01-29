package de.potionmc.launcher.cloud.commands;

import de.potionmc.launcher.Main;
import de.potionmc.launcher.cloud.ICommand;
import de.potionmc.launcher.commandinterface.Loggers;
import de.potionmc.launcher.commandinterface.LoggersType;

//Author Louispix
//Uhr zeit 23:22
public class Stop_CMD implements ICommand {


    @Override
    public void execute(String[] args) {
        new Loggers(LoggersType.INFO, Main.useColorSystem, "The service has been successfully stopped");
        System.exit(-0);
        for (int i = 0; i != 200; i++) {
            System.out.println(" " + i);
        }
    }
}
