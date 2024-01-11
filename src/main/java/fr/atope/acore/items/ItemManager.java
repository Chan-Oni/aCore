package fr.atope.acore.items;

import fr.atope.acore.ACore;
import fr.leyra.objects.ItemStackBuilder;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedHashMap;

public class ItemManager {

    @Getter
    private static final ItemManager instance = new ItemManager();
    @Getter
    private LinkedHashMap<String, ItemStack> items = new LinkedHashMap<>();
    private ACore main = ACore.getInstance();

    public void init() {
        createTools();
    }

    private void createTools() {
        createItems("hammer", "farm_hoe", "dynamite");
    }

    private void createItems(String... configKeys) {
        for (String configKey : configKeys) {
            ItemStack itemStack = createItem(configKey);
            registerItems(configKey, itemStack);
        }
    }

    public ItemStack createItem(String configKey) {
        if (!main.getConfigManager().getConfigFile("items.yml").getCustomConfig().isConfigurationSection(configKey)) {
            return new ItemStackBuilder(new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14)).setName(ChatColor.RED + "INVALID ITEM " + configKey).make();
        }
        return new ItemStackBuilder(Material.getMaterial(main.getConfigManager().getConfigFile("items.yml").getString(configKey + ".material")))
                .setFileMeta(configKey, main.getConfigManager().getConfigFile("items.yml"))
                .make();
    }

    private void registerItems(String name, ItemStack itemStack) {
        getItems().put(name, itemStack);
    }

}
