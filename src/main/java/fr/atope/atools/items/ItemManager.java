package fr.atope.atools.items;

import fr.atope.atools.ATools;
import fr.leyra.objects.ItemStackBuilder;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedHashMap;

public class ItemManager {

    @Getter
    private static final ItemManager INSTANCE = new ItemManager();
    @Getter
    public LinkedHashMap<String, ItemStack> items = new LinkedHashMap<>();
    private ATools main = ATools.getINSTANCE();

    public ItemStack hammer_level_1, hammer_level_2, hammer_level_3, farmhoe_level_1, farmhoe_level_2, farmhoe_level_3;

    public void init() {
        createHammer();
        createHoe();
    }

    private void createHammer() {

        hammer_level_1 = new ItemStackBuilder(Material.getMaterial(main.getConfigManager().getConfigFile("items.yml").getString("hammer_level_1.material")))
                .setFileMeta("hammer_level_1", main.getConfigManager().getConfigFile("items.yml"))
                .make();
        registerItems("hammer_level_1", hammer_level_1);
        hammer_level_2 = new ItemStackBuilder(Material.getMaterial(main.getConfigManager().getConfigFile("items.yml").getString("hammer_level_2.material")))
                .setFileMeta("hammer_level_2", main.getConfigManager().getConfigFile("items.yml"))
                .make();
        registerItems("hammer_level_2", hammer_level_2);
        hammer_level_3 = new ItemStackBuilder(Material.getMaterial(main.getConfigManager().getConfigFile("items.yml").getString("hammer_level_3.material")))
                .setFileMeta("hammer_level_3", main.getConfigManager().getConfigFile("items.yml"))
                .make();
        registerItems("hammer_level_3", hammer_level_3);

    }

    private void createHoe() {

        farmhoe_level_1 = new ItemStackBuilder(Material.getMaterial(main.getConfigManager().getConfigFile("items.yml").getString("farmhoe_level_1.material")))
                .setFileMeta("farmhoe_level_1", main.getConfigManager().getConfigFile("items.yml"))
                .make();
        registerItems("farmhoe_level_1", farmhoe_level_1);
        farmhoe_level_2 = new ItemStackBuilder(Material.getMaterial(main.getConfigManager().getConfigFile("items.yml").getString("farmhoe_level_2.material")))
                .setFileMeta("farmhoe_level_2", main.getConfigManager().getConfigFile("items.yml"))
                .make();
        registerItems("farmhoe_level_2", farmhoe_level_2);
        farmhoe_level_3 = new ItemStackBuilder(Material.getMaterial(main.getConfigManager().getConfigFile("items.yml").getString("farmhoe_level_3.material")))
                .setFileMeta("farmhoe_level_3", main.getConfigManager().getConfigFile("items.yml"))
                .make();
        registerItems("farmhoe_level_3", farmhoe_level_3);

    }

    private void registerItems(String name, ItemStack itemStack) {
        getItems().put(name, itemStack);
    }

}
