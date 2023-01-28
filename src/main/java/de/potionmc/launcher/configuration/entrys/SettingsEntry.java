package de.potionmc.launcher.configuration.entrys;


import de.potionmc.launcher.configuration.interfaces.IConfig;

//Author Louispix
//Uhr zeit 04:00
public class SettingsEntry implements IConfig {

    private Integer  maxMemory;
    private Integer minMemory;


    private String Task;
    private String Node;

    public SettingsEntry(Integer maxMemory, Integer minMemory, String task, String node) {
        this.maxMemory = maxMemory;
        this.minMemory = minMemory;

        this.Node = node;
        this.Task = task;

    }
    public SettingsEntry() {}
    public Integer getMaxMemory() {
        return maxMemory;
    }
    public Integer getMinMemory() {
        return minMemory;
    }

    public String getTask() {
        return Task;
    }
    public String getNode() {
        return Node;
    }
}
