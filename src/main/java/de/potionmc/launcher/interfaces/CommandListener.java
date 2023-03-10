package de.potionmc.launcher.interfaces;

import de.potionmc.launcher.Main;
import de.potionmc.launcher.commandinterface.Loggers;
import de.potionmc.launcher.commandinterface.LoggersType;
import net.minestom.server.MinecraftServer;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

//Author Louispix
//Uhr zeit 10:15
public class CommandListener {
   // SettingsEntry entry = (SettingsEntry) new ConfigHandler("./config.json").read(SettingsEntry.class);
    Scanner scanner = new Scanner(System.in);

    String ProxyPath;
    String LobbyPath;

    String ProxyName;

    String GroupName;

    int slots;

    int onlineplayers;

    int startport;

    int minMemory;

    int maxMemory;

    Process process;

    //Timer timer;

   //     String group = ProxyName.split((new FileHandler()).loadToExistingConfiguration(CoreConfig.Core).getString("Splitter"))[0];
      //  this.GroupName = group;



            public void InitComamnd(int minMemory, int maxMemory) {
                this.slots = slots;
                this.startport = startport;
                this.LobbyPath = "/launch/Lobby/";
                this.ProxyPath = "/launch/Proxy/";
                this.minMemory = minMemory;
                this.maxMemory = maxMemory;
                //   ConsoleSender sender = new ConsoleSender();


                MinecraftServer minecraftServer = new MinecraftServer();

                while (scanner.hasNext()) {
                    String anway = scanner.nextLine();
                    if (anway.equalsIgnoreCase("version")) {
                        new Loggers(LoggersType.INFO, Main.useColorSystem, "Der Server Laüft über BlazShield");
                        new Loggers(LoggersType.INFO, Main.useColorSystem, "Version [OBSIDIAN-0.0.1]");
                    } else if (anway.equalsIgnoreCase("stop")) {
                        new Loggers(LoggersType.INFO, Main.useColorSystem, "Der Server wurde erfolgreich gestoppt");
                        System.exit(-0);
                        for (int i = 0; i != 200; i++) {
                            System.out.println(" " + i);
                    }
                    } else if (anway.equalsIgnoreCase("start")) {
                        try {
                            //   Runtime.getRuntime().exec("java -Xms" + 125 + "M -Xmx" + 256 + "M -jar BlazShield.jar");
                            CommandListener.this.process = Runtime.getRuntime().exec("java -Xms" + CommandListener.this.minMemory + "M -Xmx" + CommandListener.this.maxMemory + "M -jar BlazShield.jar", (String[]) null, new File(System.getProperty("user.dir") + CommandListener.this.LobbyPath));
                            CommandListener.this.process = Runtime.getRuntime().exec("java -Xms" + CommandListener.this.minMemory + "M -Xmx" + CommandListener.this.maxMemory + "M -jar BlazShield.jar", (String[]) null, new File(System.getProperty("user.dir") + CommandListener.this.ProxyPath));
                            new Loggers(LoggersType.INFO, Main.useColorSystem, "Der Server wurde erfolgreich gestartet");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                    } else if (anway.equalsIgnoreCase("help")) {
                        new Loggers(LoggersType.INFO, Main.useColorSystem, "The following Commands are registered:");
                        new Loggers(LoggersType.INFO, Main.useColorSystem, " ");

                        new Loggers(LoggersType.INFO, Main.useColorSystem, "/version Aliases: [version, ver] - the Cloud version is online!");
                        new Loggers(LoggersType.INFO, Main.useColorSystem, "/start Aliases: [start, run] - the service started command!");
                        new Loggers(LoggersType.INFO, Main.useColorSystem, "/stop Aliases: [stop, shutdown] - Cloud stop command!");
                        new Loggers(LoggersType.INFO, Main.useColorSystem, " ");

                        new Loggers(LoggersType.INFO, Main.useColorSystem, "Threads: " + Runtime.getRuntime().availableProcessors());
                        new Loggers(LoggersType.INFO, Main.useColorSystem, "OS System: " + System.getProperty("os.name"));
                        new Loggers(LoggersType.INFO, Main.useColorSystem, "Support: http://discord.blazmc.de");




                    } else {
                        new Loggers(LoggersType.WARN, Main.useColorSystem, "The command was not found please type HELP to get help");
                    }
                }
            }
}

