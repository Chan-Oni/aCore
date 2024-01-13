package fr.atope.acore.events;

import fr.atope.acore.ACore;
import fr.atope.acore.items.ItemManager;
import fr.leyra.objects.ItemStackBuilder;
import fr.leyra.objects.NBTEditor;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class InfinityToolsEvent implements Listener {

    private final HashMap<UUID, List<ItemStack>> playersItem = new HashMap<>();
    private final ACore main;

    public InfinityToolsEvent(ACore main) {
        this.main = main;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();
        List<ItemStack> playerInfiniteItems = new ArrayList<>();

        for (int i = 0; i < player.getInventory().getSize(); ++i) {

            ItemStack itemStack = player.getInventory().getItem(i);
            if (itemStack == null) continue;
            boolean isInfinite = NBTEditor.getBoolean(itemStack, "isInfinite");
            if (!isInfinite) continue;

            playerInfiniteItems.add(itemStack);

            e.getDrops().remove(itemStack);
            playersItem.put(player.getUniqueId(), playerInfiniteItems);

        }

        /*Deuxième partie : update de l'item*/

        Player killer = e.getEntity().getKiller();
        if (killer == null || killer.getItemInHand() == null || !killer.getItemInHand().hasItemMeta()) return;
        if (!NBTEditor.hasNBTTag(killer.getItemInHand(), "modified-id")) return;

        ItemStack item = killer.getItemInHand();
        int killCount = NBTEditor.getInt(item, "kills") + 1;
        ItemStack sword = updateInfiniteSword(item, e.getEntity(), killCount);
        killer.setItemInHand(sword);

    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        if (playersItem.containsKey(player.getUniqueId())) {
            playersItem.get(player.getUniqueId()).forEach(itemStack -> player.getInventory().addItem(itemStack));
            playersItem.remove(player.getUniqueId());
        }

    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {

        ItemStack itemStack = event.getItemDrop().getItemStack();
        if (itemStack == null) return;
        boolean isInfinite = NBTEditor.getBoolean(itemStack, "isInfinite");
        if (isInfinite) event.setCancelled(main.getConfig().getBoolean("enable-infinite-tools-drop"));

    }

    private ItemStack updateInfiniteSword(ItemStack item, Player victim, int kills) {
        boolean isInfinite = NBTEditor.getBoolean(item, "isInfinite");
        if (!isInfinite) {
            return item;
        }

        String id = NBTEditor.getString(item, "modified-id");
        List<String> lore = main.getItemsConfig().getStringList(id + ".lore");

        lore.replaceAll(line -> ChatColor.translateAlternateColorCodes('&', line
                .replaceAll("%kills%", Integer.toString(kills))
                .replaceAll("%last_victim%", victim.getName())
        ));

        ItemMeta meta = item.getItemMeta();
        meta.setLore(lore);
        item.setItemMeta(meta);

        NBTEditor.setIntegerNBT(item, "kills", kills);

        return item;
    }

}
