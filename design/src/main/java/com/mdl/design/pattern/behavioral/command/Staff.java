package com.mdl.design.pattern.behavioral.command;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by geely
 */
public class Staff {
    private List<Command> commandList = new ArrayList<Command>();
    public void addCommand(Command command){
        commandList.add(command);
    }

    public void executeCommands(){
        for(Command command : commandList){
            command.execute();
        }
        commandList.clear();
    }

}
