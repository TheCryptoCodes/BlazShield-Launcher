package de.potionmc.launcher.configuration.interfaces;

public interface IConfigHandler {

    void save(IConfig config);
    IConfig read(Class<? extends IConfig> configClass);

    String convert(IConfig config);

    IConfig convert(String json, Class<? extends IConfig> configClass);

}
