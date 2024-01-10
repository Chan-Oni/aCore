package fr.atope.acore.menu;

import fr.atope.acore.items.ItemManager;
import fr.leyra.gui.Menu;
import fr.leyra.objects.ItemStackBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

public class AMenu extends Menu {

    public AMenu(Player player) {
        super(player);
    }

    @Override
    public String getMenuName() {
        return ChatColor.GOLD + "" + ChatColor.BOLD + "aCore Items";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {

        if(event.getRawSlot() >= 54) {
            event.setCancelled(false);
        } else {
            if(event.getCurrentItem().getType() != Material.STAINED_GLASS_PANE) {
                event.getWhoClicked().getInventory().addItem(event.getCurrentItem());
            }
        }

    }

    @Override
    public void closeMenu(InventoryCloseEvent inventoryCloseEvent) {}

    @Override
    public void setMenuItems() {

        setOutlines(new ItemStackBuilder(new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14)).setName(" ").make());

        ItemManager.getInstance().getItems().forEach((s, itemStack) -> inventory.addItem(itemStack));

    }
}
