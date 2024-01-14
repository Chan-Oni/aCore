package fr.atope.acore.configs;

import fr.atope.acore.ACore;
import fr.leyra.files.ConfigFile;
import org.bukkit.ChatColor;

public class ConfigurationFile extends ConfigFile {
    public ConfigurationFile() {
        super(ACore.getInstance());
    }

    @Override
    public String getFileName() {
        return "config.yml";
    }

    @Override
    public String autoReplace(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
