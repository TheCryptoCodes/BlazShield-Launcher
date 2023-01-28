package de.potionmc.launcher.cloud;

import lombok.Getter;

@Getter
public class Command {

    private String name;
    private String description;
    private ICommand iCommand;

    public Command(String name, String description, ICommand iCommand) {
        this.name = name;
        this.description = description;
        this.iCommand = iCommand;
    }

}
