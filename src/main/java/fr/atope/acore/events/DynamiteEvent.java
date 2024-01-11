package fr.atope.acore.events;

import fr.atope.acore.ACore;
import fr.atope.acore.items.ItemManager;
import fr.leyra.objects.ItemStackBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class DynamiteEvent implements Listener {

    private long countdownTime = 3000;
    private ACore main;

    public DynamiteEvent(ACore main) {
        this.main = main;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {

        Player player = event.getPlayer();

        if (event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_BLOCK))
            return;

        if (ItemStackBuilder.checkItemsWithoutLore(player.getItemInHand(), ItemManager.getInstance().getItems().get("dynamite"))) {

            player.setItemInHand(ItemStackBuilder.removeCount(player.getItemInHand()));

            Location location = player.getLocation().add(0, 1, 0);
            ArmorStand armorStand = (ArmorStand) player.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
            armorStand.setVisible(false);
            armorStand.setGravity(true);
            armorStand.setSmall(true);
            armorStand.setMarker(false);
            armorStand.setItemInHand(new ItemStack(Material.TNT));
            Vector launchDirection = player.getLocation().getDirection().multiply(1.5);
            armorStand.setVelocity(launchDirection);

            armorStand.setCustomName("§c" + countdownTime / 1000.0);
            armorStand.setCustomNameVisible(true);

            new BukkitRunnable() {

                long timeLeft = countdownTime;

                @Override
                public void run() {
                    timeLeft -= 100;
                    if (timeLeft > 0) {
                        armorStand.setCustomName("§c" + timeLeft / 1000.0);
                    } else {
                        armorStand.remove();
                        player.getWorld().createExplosion(armorStand.getLocation().getX(), armorStand.getLocation().getY(), armorStand.getLocation().getZ(), 4, false, true); // Explosion
                        cancel();
                    }
                }
            }.runTaskTimer(main, 0, 2);

        }

    }

}
