package de.potionmc.launcher;

import de.potionmc.launcher.commandinterface.HashHandler;
import de.potionmc.launcher.commandinterface.Loggers;
import de.potionmc.launcher.commandinterface.LoggersType;
import de.potionmc.launcher.configuration.ConfigHandler;
import de.potionmc.launcher.configuration.entrys.SettingsEntry;
import de.potionmc.launcher.interfaces.CommandListener;
import de.potionmc.launcher.interfaces.URLListener;

import java.io.File;
import java.io.IOException;

//Author Louispix
//Uhr zeit 14:07
public class Main {


    private static Main instance;


    public static boolean useColorSystem;
    public static HashHandler hashedHandler;
    public static void main(String[] args){

       // new CloudMainSetup("manager");

        CommandListener commandListener = new CommandListener();

        try {
            System.setProperty("file.encoding", "UTF-8");
            System.setProperty("client.encoding.override", "UTF-8");
            if (System.getProperty("os.name").startsWith("Windows")) {
                useColorSystem = false;
            } else {
                useColorSystem = true;
            }

            try {
                hashedHandler = new HashHandler();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        if (!new File("./config.json").exists()) {
            SettingsEntry entry = new SettingsEntry(512,256);
            new ConfigHandler("./config.json").save(entry);
        }


        new Loggers(LoggersType.LOGO, Main.useColorSystem,         "\n" +
                "     ____  _            _____ _     _      _     _ \n" +
                "    |  _ \\| |          / ____| |   (_)    | |   | |\n" +
                "    | |_) | | __ _ ___| (___ | |__  _  ___| | __| |\n" +
                "    |  _ <| |/ _` |_  /\\___ \\| '_ \\| |/ _ \\ |/ _` |\n" +
                "    | |_) | | (_| |/ / ____) | | | | |  __/ | (_| |\n" +
                "    |____/|_|\\__,_/___|_____/|_| |_|_|\\___|_|\\__,_| [SANDSTONE-0.0.1]\n" +
                "                                                \n" +
                "                                                \n" +
                "   <!> Thank you for using BlazShield for your Network\n" +
                "   <!> Our Support can you find - https://discord.blazmc.de/\n\n\n\n" +
                "   The MineStom Spigot, is coded by Louispix[!]\n" +
                "   there could be bugs, the version is in alpha[!]\n" +
                "   The project is also bungee cord capable[!]\n" +
                " \n  " +
                "  \n " +
                "_________________________________________________________________________________________________________\n");


        try {
            URLListener.saveUrl("./launch/Lobby/BlazShield.jar", "http://45.93.250.252/BlazShield.jar");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            URLListener.saveUrl("./launch/Proxy/BlazShield.jar", "http://45.93.250.252/BungeeShield.jar");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        SettingsEntry entry = new SettingsEntry();
        commandListener.InitComamnd(256,512);



    }
    public static Main getInstance() {
        return instance;
    }



}
