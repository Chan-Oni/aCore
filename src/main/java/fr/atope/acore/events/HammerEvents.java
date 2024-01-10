package fr.atope.acore.events;

import fr.atope.acore.ACore;
import fr.atope.acore.items.ItemManager;
import fr.leyra.objects.ItemStackBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.block.CraftBlock;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class HammerEvents implements Listener {

    private ACore main;

    public HammerEvents(ACore main) {
        this.main = main;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void blockBreak(BlockBreakEvent event) {

        Player player = event.getPlayer();
        Block block = event.getBlock();

        if (!main.getWorldGuardManager().canBuild(player, block.getLocation())) return;

        if (player.getItemInHand() == null) return;
        if (!player.getItemInHand().hasItemMeta()) return;

        ItemStack item = player.getItemInHand();
        if (item == null || !item.hasItemMeta()) return;

        if (!ItemStackBuilder.checkItemsWithoutLore(player.getItemInHand(), ItemManager.getInstance().getItems().get("hammer")))
            return;

        for (Block blocks : getSquare(block.getLocation(), 3)) {
            blocks.breakNaturally(player.getItemInHand());
        }
        player.setItemInHand(new ItemStackBuilder(player.getItemInHand()).removeCustDura(1, main.getDurabilityPlaceholder()).make());

    }

    public static List<Block> getSquare(Location location, int radius) {
        List<Block> blocks = new ArrayList<>();
        for (int x = location.getBlockX() - (radius / 2); x <= location.getBlockX() + (radius / 2); x++) {
            for (int z = location.getBlockZ() - (radius / 2); z <= location.getBlockZ() + (radius / 2); z++) {
                for (int y = location.getBlockY() - (radius / 2); y <= location.getBlockY() + (radius / 2); y++) {
                    if (location.getWorld().getBlockAt(x, y, z).getType().equals(Material.BEDROCK)) continue;
                    blocks.add(location.getWorld().getBlockAt(x, y, z));
                }
            }
        }
        return blocks;
    }

}
