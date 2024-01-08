package fr.atope.hydrasia;

import fr.atope.hydrasia.commands.HydrasiaCommand;
import fr.atope.hydrasia.configs.ItemFile;
import fr.atope.hydrasia.items.ItemManager;
import fr.leyra.main.VPlugin;
import lombok.Getter;
import org.bukkit.ChatColor;

public final class HydrasiaItems extends VPlugin {

    @Getter
    private static HydrasiaItems INSTANCE;

    @Override
    public void onPluginEnable() {

        INSTANCE = this;

        saveDefaultConfig();
        getConfigManager().registerConfig(new ItemFile());

        ItemManager.getINSTANCE().init();

        registerCommand(new HydrasiaCommand(this));

        sendInfo(ChatColor.GOLD + "\n" +
                " __    __                  __                               __            ______   __                                       \n" +
                "|  \\  |  \\                |  \\                             |  \\          |      \\ |  \\                                      \n" +
                "| $$  | $$ __    __   ____| $$  ______   ______    _______  \\$$  ______   \\$$$$$$_| $$_     ______   ______ ____    _______ \n" +
                "| $$__| $$|  \\  |  \\ /      $$ /      \\ |      \\  /       \\|  \\ |      \\   | $$ |   $$ \\   /      \\ |      \\    \\  /       \\\n" +
                "| $$    $$| $$  | $$|  $$$$$$$|  $$$$$$\\ \\$$$$$$\\|  $$$$$$$| $$  \\$$$$$$\\  | $$  \\$$$$$$  |  $$$$$$\\| $$$$$$\\$$$$\\|  $$$$$$$\n" +
                "| $$$$$$$$| $$  | $$| $$  | $$| $$   \\$$/      $$ \\$$    \\ | $$ /      $$  | $$   | $$ __ | $$    $$| $$ | $$ | $$ \\$$    \\ \n" +
                "| $$  | $$| $$__/ $$| $$__| $$| $$     |  $$$$$$$ _\\$$$$$$\\| $$|  $$$$$$$ _| $$_  | $$|  \\| $$$$$$$$| $$ | $$ | $$ _\\$$$$$$\\\n" +
                "| $$  | $$ \\$$    $$ \\$$    $$| $$      \\$$    $$|       $$| $$ \\$$    $$|   $$ \\  \\$$  $$ \\$$     \\| $$ | $$ | $$|       $$\n" +
                " \\$$   \\$$ _\\$$$$$$$  \\$$$$$$$ \\$$       \\$$$$$$$ \\$$$$$$$  \\$$  \\$$$$$$$ \\$$$$$$   \\$$$$   \\$$$$$$$ \\$$  \\$$  \\$$ \\$$$$$$$ \n" +
                "          |  \\__| $$                                                                                                        \n" +
                "           \\$$    $$                                                                                                        \n" +
                "            \\$$$$$$                                                                                                         \n");

    }

    @Override
    public void onPluginDisable() {

    }

    @Override
    public String getPrefix() {
        return "HydrasiaItems";
    }
}
