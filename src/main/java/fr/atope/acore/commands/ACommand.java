package fr.atope.acore.commands;

import fr.atope.acore.ACore;
import fr.leyra.commands.Command;
import fr.leyra.commands.CommandArgs;

public class ACommand {

    private ACore main;

    public ACommand(ACore main) {
        this.main = main;
    }

    @Command(name = "acore.reload", aliases = "acores")
    public void aCoreCommand(CommandArgs cmd) {



    }

}
