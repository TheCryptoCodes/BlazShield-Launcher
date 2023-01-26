package de.potionmc.launcher.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.potionmc.launcher.configuration.entrys.SettingsEntry;

import de.potionmc.launcher.configuration.interfaces.IConfig;
import de.potionmc.launcher.configuration.interfaces.IConfigHandler;
import lombok.SneakyThrows;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

//Author Louispix
//Uhr zeit 03:45
public class ConfigHandler implements IConfigHandler {

    protected  final Gson GSON = (new GsonBuilder()).serializeNulls().setPrettyPrinting().disableHtmlEscaping().create();
    private String configLocation;

    public ConfigHandler(String configLocation) {
        this.configLocation = configLocation;
    }

    public ConfigHandler() {}
    public boolean exists(){
        return new File(this.configLocation).exists();
    }
    public void save(SettingsEntry config) {
        if(config != null && this.configLocation != null){
            if(!exists()){
                try {
                    new File(this.configLocation).createNewFile();
                } catch (IOException ignored) {}
            }

            try {
                try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(this.configLocation), StandardCharsets.UTF_8)) {
                    GSON.toJson(config, writer);
                } catch (IOException ignored) {
                }
            }catch (Exception ignored){}
        }else{
            System.out.println("§8[§c§lERROR§8] §7The Error is not found§8[§c§l!§8]");
        }
    }

    @SneakyThrows
    @Override
    public IConfig read(Class<? extends IConfig> configClass) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(this.configLocation), configClass);
    }

    @Override
    public String convert(IConfig config) {
        return GSON.toJson(config);
    }

    @Override
    public IConfig convert(String json, Class<? extends IConfig> configClass) {

        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, configClass);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(IConfig config) {

    }
}
