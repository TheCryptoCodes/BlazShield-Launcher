package de.potionmc.launcher.configuration.entrys;


import de.potionmc.launcher.configuration.interfaces.IConfig;

//Author Louispix
//Uhr zeit 04:00
public class SettingsEntry implements IConfig {

    private Integer  maxMemory;
    private Integer minMemory;

    public SettingsEntry(Integer maxMemory, Integer minMemory) {
        this.maxMemory = maxMemory;
        this.minMemory = minMemory;

    }
    public SettingsEntry() {}
    public Integer getMaxMemory() {
        return maxMemory;
    }
    public Integer getMinMemory() {
        return minMemory;
    }
}
