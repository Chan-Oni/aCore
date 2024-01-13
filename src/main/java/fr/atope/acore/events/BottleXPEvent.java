package fr.atope.acore.events;

import fr.leyra.objects.NBTEditor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class BottleXPEvent implements Listener {

    @EventHandler
    public void xpClickEvent(PlayerInteractEvent event) {

        if (event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_BLOCK))
            return;

        Player p = event.getPlayer();
        ItemStack item = event.getItem();
        if (item == null || !item.hasItemMeta()) return;

        if (!NBTEditor.hasNBTTag(item, "xp")) return;

        int amount = NBTEditor.getInt(item, "xp");
        p.setLevel(p.getLevel() + amount);

        p.setItemInHand(new ItemStack(Material.AIR));

    }

}
