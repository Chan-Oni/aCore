package fr.atope.acore.items;

import fr.atope.acore.ACore;
import fr.leyra.files.ConfigFile;
import fr.leyra.objects.ItemStackBuilder;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedHashMap;

public class ItemManager {

    @Getter
    private static final ItemManager instance = new ItemManager();
    @Getter
    private LinkedHashMap<String, ItemStack> items = new LinkedHashMap<>();
    private ACore main = ACore.getInstance();

    public void init() {
        createItems(main.getItemsConfig());
    }

    private void createItems(ConfigFile configFile) {
        ConfigurationSection itemSections = configFile.getConfigurationSection("");
        if (itemSections == null) {
            main.sendError("items.yml file is empty !");
            return;
        }
        for (String configKey : itemSections.getKeys(false)) {
            if(configKey.equals("durability")) continue;
            ItemStack itemStack = createItem(configKey);
            registerItems(configKey, itemStack);
        }
    }

    public ItemStack createItem(String configKey) {
        ConfigFile conf = main.getItemsConfig();
        if (!conf.getCustomConfig().isConfigurationSection(configKey)) {
            return new ItemStackBuilder(new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14)).setName(ChatColor.RED + "INVALID ITEM " + configKey).make();
        }
        if (Material.getMaterial(conf.getString(configKey + ".material")) == null) {
            return new ItemStackBuilder(new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14)).setName(ChatColor.RED + "INVALID MATERIAL " + conf.getString(configKey + ".material")).make();
        }
        return new ItemStackBuilder(Material.getMaterial(conf.getString(configKey + ".material")))
                .setFileMeta(configKey, main.getItemsConfig())
                .make();
    }

    private void registerItems(String name, ItemStack itemStack) {
        getItems().put(name, itemStack);
    }

}
