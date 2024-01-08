package fr.atope.atools;

import fr.atope.atools.commands.ACommand;
import fr.atope.atools.configs.ItemFile;
import fr.atope.atools.items.ItemManager;
import fr.leyra.main.VPlugin;
import lombok.Getter;

public final class ATools extends VPlugin {

    @Getter
    private static ATools INSTANCE;

    @Override
    public void onPluginEnable() {

        INSTANCE = this;

        saveDefaultConfig();
        getConfigManager().registerConfig(new ItemFile());

        ItemManager.getINSTANCE().init();

        registerCommand(new ACommand(this));

    }

    @Override
    public void onPluginDisable() {

    }

    @Override
    public String getPrefix() {
        return "ATools";
    }
}
