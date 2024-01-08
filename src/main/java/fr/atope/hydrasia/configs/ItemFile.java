package fr.atope.hydrasia.configs;

import fr.atope.hydrasia.HydrasiaItems;
import fr.leyra.files.ConfigFile;
import org.bukkit.ChatColor;

import java.io.File;

public class ItemFile extends ConfigFile {

    public ItemFile() {
        super(HydrasiaItems.getINSTANCE());
    }

    @Override
    public String getFileName() {
        return "items.yml";
    }

    @Override
    public String autoReplace(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    @Override
    public String defaultNoValueMessage() {
        return ChatColor.RED + "Problème dans la configuration...";
    }

    @Override
    public File getFile() {
        return new File(HydrasiaItems.getINSTANCE().getDataFolder(), getFileName());
    }
}
