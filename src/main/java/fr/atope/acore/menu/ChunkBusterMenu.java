package fr.atope.acore.menu;

import fr.atope.acore.ACore;
import fr.atope.acore.events.ChunkBusterEvent;
import fr.atope.acore.items.ItemManager;
import fr.leyra.files.ConfigFile;
import fr.leyra.gui.Menu;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

public class ChunkBusterMenu extends Menu {

    Location busterLocation;

    public ChunkBusterMenu(Player player, Location location) {
        super(player);
        this.busterLocation = location;
    }

    @Override
    public String getMenuName() {
        return ChatColor.translateAlternateColorCodes('&', ACore.getInstance().getConfig().getString("buster-menu-name"));
    }

    @Override
    public int getSlots() {
        return ACore.getInstance().getConfig().getInt("buster-menu-rows") * 9;
    }

    @Override
    public void handleMenu(InventoryClickEvent inventoryClickEvent) {
        FileConfiguration config = ACore.getInstance().getConfig();
        String action = config.getString("buster-menu-slots." + inventoryClickEvent.getSlot() + ".action");
        switch (action) {
            case "cancel":
                player.closeInventory();
                player.getInventory().addItem(ItemManager.getInstance().getItems().get("chunk_buster"));
                break;
            case "none":
                inventoryClickEvent.setCancelled(true);
                break;
            case "validate":
                ChunkBusterEvent cbe = new ChunkBusterEvent(ACore.getInstance());
                cbe.initializeChunkBuster(this.busterLocation);
                player.closeInventory();
                break;
            default:
                player.sendMessage(ChatColor.RED + "No action set for this item.");
                break;
        }
    }

    @Override
    public void closeMenu(InventoryCloseEvent inventoryCloseEvent) {

    }

    @Override
    public void setMenuItems() {
        ConfigFile config = ACore.getInstance().getConfigFile();
        ConfigurationSection configurationSection = config.getConfigurationSection("buster-menu-slots");
        for (String key : configurationSection.getKeys(false)) {
            int slot;
            try {
                slot = Integer.parseInt(key);
            } catch (NumberFormatException e) {
                player.sendMessage(ChatColor.RED + "Error in parsing configuration " + key + " in config.yml, must choose an integer !");
                continue;
            }
            ItemStack item = ItemManager.getInstance().createItem(config, "buster-menu-slots." + key);
            inventory.setItem(slot, item);
        }
    }
}
