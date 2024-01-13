package fr.atope.acore.menu;

import fr.atope.acore.items.ItemManager;
import fr.leyra.gui.PaginatedMenu;
import fr.leyra.objects.ItemStackBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class AItemsMenu extends PaginatedMenu {

    public AItemsMenu(Player player) {
        super(player);
    }

    @Override
    public ItemStack previousPage() {
        return new ItemStackBuilder(Material.PAPER).setName("§8§l» §ePage Précédente §8§l«").setGlow(true).make();
    }

    @Override
    public ItemStack nextPage() {
        return new ItemStackBuilder(Material.PAPER).setName("§8§l» §ePage Suivante §8§l«").setGlow(true).make();
    }

    @Override
    public ItemStack closeMenu() {
        return new ItemStackBuilder(Material.BARRIER).setName("§8§l» §cFermer §8§l«").setGlow(true).make();
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

        if (event.getRawSlot() >= 54) {
            event.setCancelled(false);
        } else {
            if (event.getCurrentItem().getType() != Material.STAINED_GLASS_PANE && !isBorderItem(event.getCurrentItem())) {
                event.getWhoClicked().getInventory().addItem(event.getCurrentItem());
            }
        }

    }

    @Override
    public void closeMenu(InventoryCloseEvent inventoryCloseEvent) {
    }

    @Override
    public void setMenuItems() {

        addMenuBorder(new ItemStackBuilder(new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14)).setName(" ").make());
        setItemList(new ArrayList<>(ItemManager.getInstance().getItems().values()));

    }
}
