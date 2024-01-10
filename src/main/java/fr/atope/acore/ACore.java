package fr.atope.acore;

import fr.atope.acore.commands.ACommand;
import fr.atope.acore.commands.AItemsCommand;
import fr.atope.acore.configs.ItemFile;
import fr.atope.acore.configs.MessageFile;
import fr.atope.acore.items.ItemManager;
import fr.leyra.main.VPlugin;
import lombok.Getter;

public final class ACore extends VPlugin {

    @Getter
    private static ACore instance;

    @Override
    public void onPluginEnable() {

        instance = this;

        saveDefaultConfig();
        getConfigManager().registerConfig(new ItemFile());
        getConfigManager().registerConfig(new MessageFile());

        ItemManager.getInstance().init();

        registerCommand(new ACommand(this));
        registerCommand(new AItemsCommand(this));

    }

    @Override
    public void onPluginDisable() {

    }

    @Override
    public String getPrefix() {
        return "ATools";
    }
}
