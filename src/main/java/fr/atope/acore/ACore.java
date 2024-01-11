package fr.atope.acore;

import com.sk89q.worldguard.WorldGuard;
import fr.atope.acore.commands.ACommand;
import fr.atope.acore.commands.AItemsCommand;
import fr.atope.acore.commands.ChatCommands;
import fr.atope.acore.commands.VisionCommand;
import fr.atope.acore.configs.ItemFile;
import fr.atope.acore.configs.MessageFile;
import fr.atope.acore.dependencies.WorldGuardManager;
import fr.atope.acore.events.*;
import fr.atope.acore.items.ItemManager;
import fr.leyra.main.VPlugin;
import lombok.Getter;

public final class ACore extends VPlugin {

    @Getter
    private static ACore instance;
    @Getter
    private WorldGuardManager worldGuardManager;

    @Override
    public void onPluginEnable() {

        long sst = System.currentTimeMillis();

        instance = this;
        worldGuardManager = new WorldGuardManager();

        saveDefaultConfig();
        getConfigManager().registerConfig(new ItemFile());
        getConfigManager().registerConfig(new MessageFile());

        ItemManager.getInstance().init();

        registerCommand(new ACommand(this));
        registerCommand(new AItemsCommand(this));
        registerCommand(new VisionCommand(this));
        registerCommand(new ChatCommands(this));

        registerEvent(new PlayerJoinEvent());
        registerEvent(new ChatEvent());
        registerEvent(new HammerEvents(this));
        registerEvent(new FarmHoeEvent(this));
        registerEvent(new DynamiteEvent(this));

        sendInfo("Implementation time: " + (-sst + System.currentTimeMillis()) + " ms");

    }

    @Override
    public void onPluginDisable() {
    }

    @Override
    public String getPrefix() {
        return getConfigManager().getConfigFile("messages.yml").getString("prefix");
    }

    public WorldGuard getWorldGuard() {
        return WorldGuard.getInstance();
    }

    public String getDurabilityPlaceholder() {
        return getConfigManager().getConfigFile("items.yml").getString("durability");
    }
}
