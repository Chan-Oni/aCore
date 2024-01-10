package fr.atope.acore.configs;

import fr.atope.acore.ACore;
import fr.leyra.files.ConfigFile;
import org.bukkit.ChatColor;

public class ItemFile extends ConfigFile {

    public ItemFile() {
        super(ACore.getInstance());
    }

    @Override
    public String getFileName() {
        return "items.yml";
    }

    @Override
    public String autoReplace(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

}
