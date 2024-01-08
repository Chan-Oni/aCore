package fr.atope.hydrasia.items;

import fr.atope.hydrasia.HydrasiaItems;
import fr.leyra.objects.ItemStackBuilder;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

public class ItemManager {

    @Getter
    private static final ItemManager INSTANCE = new ItemManager();
    private HydrasiaItems main = HydrasiaItems.getINSTANCE();

    public ItemStack hammer_level_1, hammer_level_2, hammer_level_3, farmhoe1, farmhoe2, farmhoe3;

    public void init() {

        createHammer();

    }

    private void createHammer() {

        hammer_level_1 = new ItemStackBuilder()
                .setEvadiaFileMeta("hammer_level_1", main.getConfigManager().getConfigFile("items.yml"))
                .make();
        hammer_level_2 = new ItemStackBuilder()
                .setEvadiaFileMeta("hammer_level_2", main.getConfigManager().getConfigFile("items.yml"))
                .make();
        hammer_level_3 = new ItemStackBuilder()
                .setEvadiaFileMeta("hammer_level_3", main.getConfigManager().getConfigFile("items.yml"))
                .make();

    }

}
