package fr.atope.acore;

import com.sk89q.worldguard.WorldGuard;
import fr.atope.acore.commands.*;
import fr.atope.acore.configs.ItemFile;
import fr.atope.acore.configs.MessageFile;
import fr.atope.acore.dependencies.FactionManager;
import fr.atope.acore.dependencies.WorldGuardManager;
import fr.atope.acore.events.*;
import fr.atope.acore.items.ItemManager;
import fr.leyra.files.ConfigFile;
import fr.leyra.main.VPlugin;
import lombok.Getter;
import org.bukkit.ChatColor;

@Getter
public final class ACore extends VPlugin {

    @Getter
    private static ACore instance;
    private WorldGuardManager worldGuardManager;
    private FactionManager factionManager;

    @Override
    public void onPluginEnable() {

        long sst = System.currentTimeMillis();

        instance = this;
        worldGuardManager = new WorldGuardManager();
        factionManager = new FactionManager();

        saveDefaultConfig();
        getConfigManager().registerConfig(new ItemFile());
        getConfigManager().registerConfig(new MessageFile());

        ItemManager.getInstance().init();

        registerCommands();
        registerEvents();

        sendEnableMessage(sst);

    }

    @Override
    public void onPluginDisable() {}

    @Override
    public String getPrefix() {
        return getConfigManager().getConfigFile("messages.yml").getString("prefix");
    }

    private void registerCommands() {
        registerCommand(new ACommand(this));
        registerCommand(new AItemsCommand(this));
        registerCommand(new VisionCommand(this));
        registerCommand(new ChatCommands(this));
        registerCommand(new TrashCommand(this));
        registerCommand(new FurnaceCommand(this));
        registerCommand(new BottleXPCommand(this));
        registerCommand(new AnnounceCommand());
        registerCommand(new WelcomeCommand(this));
    }

    private void registerEvents() {
        registerEvent(new PlayerJoinEvent(this));
        registerEvent(new ChatEvent());
        registerEvent(new HammerEvents(this));
        registerEvent(new FarmHoeEvent(this));
        registerEvent(new DynamiteEvent(this));
        registerEvent(new BottleXPEvent());
    }

    private void sendEnableMessage(long sst) {
        sendInfo(ChatColor.LIGHT_PURPLE + "\n            ______                                \n" +
                "           /      \\                               \n" +
                "  ______  |  $$$$$$\\  ______    ______    ______  \n" +
                " |      \\ | $$   \\$$ /      \\  /      \\  /      \\ \n" +
                "  \\$$$$$$\\| $$      |  $$$$$$\\|  $$$$$$\\|  $$$$$$\\\n" +
                " /      $$| $$   __ | $$  | $$| $$   \\$$| $$    $$\n" +
                "|  $$$$$$$| $$__/  \\| $$__/ $$| $$      | $$$$$$$$\n" +
                " \\$$    $$ \\$$    $$ \\$$    $$| $$       \\$$     \\\n" +
                "  \\$$$$$$$  \\$$$$$$   \\$$$$$$  \\$$        \\$$$$$$$ \n \n " +
                "       Implementation time: " + (-sst + System.currentTimeMillis()) + " ms\n" +
                "           By Leyra and Kairoomc.\n" +
                "             discord.gg/atope \n ");
    }

    public ConfigFile getMessageConfig() {
        return getConfigManager().getConfigFile("messages.yml");
    }

    public ConfigFile getItemsConfig() {
        return getConfigManager().getConfigFile("items.yml");
    }

    public WorldGuard getWorldGuard() {
        return WorldGuard.getInstance();
    }

    public String getDurabilityPlaceholder() {
        return getItemsConfig().getString("durability");
    }
}
