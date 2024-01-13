package fr.atope.acore.commands;

import fr.atope.acore.ACore;
import fr.atope.acore.items.ItemManager;
import fr.leyra.commands.Command;
import fr.leyra.commands.CommandArgs;

public class ACommand {

    private ACore main;

    public ACommand(ACore main) {
        this.main = main;
    }

    @Command(name = "acore.reload", aliases = "acores")
    public void aCoreCommand(CommandArgs cmd) {

        if (!cmd.getSender().hasPermission(main.getConfig().getString("permissions.acore-reload"))) return;

        main.reloadConfig();
        main.getConfigManager().reloadConfigs();

        ItemManager.getInstance().init();

        cmd.getSender().sendMessage(main.getMessageConfig().getString("reload-message"));

    }

}
