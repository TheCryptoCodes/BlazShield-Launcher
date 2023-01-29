package de.potionmc.launcher;

import de.potionmc.launcher.cloud.CommandManager;
import de.potionmc.launcher.cloud.commands.Help_CMD;
import de.potionmc.launcher.cloud.commands.Stop_CMD;
import de.potionmc.launcher.cloud.commands.Version_CMD;
import de.potionmc.launcher.commandinterface.HashHandler;
import de.potionmc.launcher.commandinterface.Loggers;
import de.potionmc.launcher.commandinterface.LoggersType;
import de.potionmc.launcher.interfaces.URLListener;

import lombok.SneakyThrows;
import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.*;

//Author Louispix
//Uhr zeit 14:07



public class Main {


    public static boolean useColorSystem;


    public static HashHandler hashedHandler;

    private static final String PREFIX = "\033[36mBlazShield \033[37m» ";


    private static volatile LineReader lineReader;
    private static volatile Terminal terminal;
    private static volatile boolean running = false;

@SneakyThrows
    public static void main(String[] args)  {
 //       new de.potionmc.networking.NettyDriver();
//
//
 //   NettyDriver.getInstance().nettyServer = new NettyServer();
 //   NettyDriver.getInstance().nettyServer.bind(2002).start();
 //   NettyDriver.getInstance().packetDriver.handelListener(new NetworkHandler());


        Process process = new Process() {
            @Override
            public OutputStream getOutputStream() {
                return null;
            }

            @Override
            public InputStream getInputStream() {
                return null;
            }

            @Override
            public InputStream getErrorStream() {
                return null;
            }

            @Override
            public int waitFor() throws InterruptedException {
                return 0;
            }

            @Override
            public int exitValue() {
                return 0;
            }

            @Override
            public void destroy() {

            }
        };



        CommandManager commandManager = new CommandManager();
        commandManager.register("help", "can you see all cloud commands", new Help_CMD());
        commandManager.register("version", "can you see all cloud commands", new Version_CMD());
        commandManager.register("ver", "can you see all cloud commands", new Version_CMD());
        commandManager.register("stop", "can you see all cloud commands", new Stop_CMD());
        commandManager.register("shutdown", "can you see all cloud commands", new Stop_CMD());

        String ProxyPath;
        String LobbyPath;
        LobbyPath = "/launch/Lobby/";
        ProxyPath = "/launch/Proxy/";

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
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        final Thread thread = new Thread(null, () -> {
            try {
                terminal = TerminalBuilder.builder().color(true).dumb(true).encoding("UTF-8").build();
            } catch (IOException e) {
                e.printStackTrace();
            }
            lineReader = LineReaderBuilder.builder().terminal(terminal).build();
            running = true;
            while (running) {
                String command;
                try {
                    command = lineReader.readLine(PREFIX);
                    String finalCommand = command.replace(PREFIX, "");
                    String[] args1 = finalCommand.split(" ");
                    String[] astring = new String[args1.length - 1];
                    System.arraycopy(args1, 1, astring, 0, args1.length - 1);
                    String[] arguments = astring;
                    String commandName = args1[0];
                    if (!commandManager.exists(commandName)) {
                        new Loggers(LoggersType.WARN, Main.useColorSystem, "The command was not found please type HELP to get help");
                    } else {
                        commandManager.execute(commandName, arguments);
                    }
                } catch (EndOfFileException e) {
                    return;
                }
            }
        }, "BlazShield");
        thread.setPriority(Thread.MAX_PRIORITY);
        thread.start();




    new Loggers(LoggersType.LOGO, Main.useColorSystem, "\n" +
            "     ____  _            _____ _     _      _     _ \n" +
            "    |  _ \\| |          / ____| |   (_)    | |   | |\n" +
            "    | |_) | | __ _ ___| (___ | |__  _  ___| | __| |\n" +
            "    |  _ <| |/ _` |_  /\\___ \\| '_ \\| |/ _ \\ |/ _` |\n" +
            "    | |_) | | (_| |/ / ____) | | | | |  __/ | (_| |\n" +
            "    |____/|_|\\__,_/___|_____/|_| |_|_|\\___|_|\\__,_| [OBSIDIAN-0.0.1]\n" +
            "                                                \n" +
            "_________________________________________________________________________________________________________\n");
        a("everything is being prepared...");
        a("an instance of the Manager is executed");
        b("a new node called InternalNode wants to connect");
        h("type 'start' to start proxy and lobby");
        h("type 'help' to see the help list");
        if (!new File("./launch/Proxy/").exists()) {
            a("Proxy Template was not found");
            if (new File("./launch/Proxy/").mkdir()) {
                a("The Proxy Template is createt");
                try {
                    URLListener.saveUrl("./launch/Proxy/BlazShield.jar", "http://45.93.250.252/BungeeShield.jar");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    URLListener.saveUrl("./launch/Proxy/velocity.toml", "http://45.93.250.252/velocity.toml");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    URLListener.saveUrl("./launch/Proxy/forwarding.secret", "http://45.93.250.252/forwarding.secret");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        if (!new File("./launch/Lobby/").exists()) {
            a("Lobby Template was not found");
            if (new File("./launch/Lobby/").mkdir()) {
                a("The Lobby Template is createt");
                try {
                    URLListener.saveUrl("./launch/Lobby/BlazShield.jar", "http://45.93.250.252/BlazShield.jar");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                URLListener.saveUrl("./launch/Lobby/config.json", "https://workupload.com/start/296anAG8hr4");
            }
        }
        if (!process.isAlive()) {
            a("The Service is starting");
            process = Runtime.getRuntime().exec("java -Xms" + 216 + "M -Xmx" + 512 + "M -jar BlazShield.jar", (String[]) null, new File(System.getProperty("user.dir") + ProxyPath));
            process = Runtime.getRuntime().exec("java -Xms" + 216 + "M -Xmx" + 512 + "M -jar BlazShield.jar", (String[]) null, new File(System.getProperty("user.dir") + LobbyPath));
        } else {
            a("The Service is not online");
        }
       // commandListener.InitComamnd(256, 512);

    }


    private static void a(String message) {
        new Loggers(LoggersType.INFO, Main.useColorSystem, message);
    }

    private static void b(String message) {
        new Loggers(LoggersType.SERVICEREM, Main.useColorSystem, message);
    }

    private static void h(String message) {
        new Loggers(LoggersType.HELP, Main.useColorSystem, message);

    }
}
