package fr.atope.acore.configs;

import fr.atope.acore.ACore;
import fr.leyra.files.ConfigFile;
import org.bukkit.ChatColor;

public class MessageFile extends ConfigFile {

    public MessageFile() {
        super(ACore.getInstance());
    }

    @Override
    public String getFileName() {
        return "messages.yml";
    }

    @Override
    public String autoReplace(String s) {
        return ChatColor.translateAlternateColorCodes('&', s.replaceAll("%prefix%", getCustomConfig().getString("prefix")));
    }

}
