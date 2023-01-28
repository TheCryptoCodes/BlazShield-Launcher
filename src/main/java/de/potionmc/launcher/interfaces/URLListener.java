package de.potionmc.launcher.interfaces;

import de.potionmc.launcher.Main;
import de.potionmc.launcher.commandinterface.Loggers;
import de.potionmc.launcher.commandinterface.LoggersType;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

//Author Louispix
//Uhr zeit 23:37
public class URLListener {

    public static void saveUrl(final String filename, final String urlString)
            throws MalformedURLException, IOException {
        BufferedInputStream in = null;
        FileOutputStream fout = null;
        try {
            in = new BufferedInputStream(new URL(urlString).openStream());
            fout = new FileOutputStream(filename);

            final byte data[] = new byte[1024];
            int count;
            while ((count = in.read(data, 0, 1024)) != -1) {
                fout.write(data, 0, count);


            }
        } finally {
            if (in != null) {
                in.close();
            }
            if (fout != null) {
                fout.close();
              //  a("Succesfully downloaded BlazShield");
            }


        }
        a("Succesfully update BlazShield");

    }
    private static void a(String message) {
        new Loggers(LoggersType.DOWNLOADER, Main.useColorSystem, message);
    }


}
