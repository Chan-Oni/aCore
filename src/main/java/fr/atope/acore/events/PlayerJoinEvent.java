package fr.atope.acore.events;

import fr.atope.acore.ACore;
import fr.atope.acore.commands.VisionCommand;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerJoinEvent implements Listener {

    @Getter
    private static Player latestPlayer;
    private ACore main;

    public PlayerJoinEvent(ACore main) {
        this.main = main;
    }

    @EventHandler
    public void playerJoin(org.bukkit.event.player.PlayerJoinEvent event) {

        boolean k = VisionCommand.getNightVision().computeIfAbsent(event.getPlayer().getUniqueId(), key -> false);
        if (k) {
            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0, true));
            event.getPlayer().sendMessage(ACore.getInstance().getConfigManager().getConfigFile("messages.yml").getString("vision-active"));
        } else {
            event.getPlayer().removePotionEffect(PotionEffectType.NIGHT_VISION);
            event.getPlayer().sendMessage(ACore.getInstance().getConfigManager().getConfigFile("messages.yml").getString("vision-non-active"));
        }

        if (event.getPlayer().hasPlayedBefore()) return;

        latestPlayer = event.getPlayer();
        if(main.getConfig().getInt("welcome-delay") == 0) return;
        new BukkitRunnable() {
            @Override
            public void run() {
                latestPlayer = null;
            }
        }.runTaskLater(main, main.getConfig().getInt("welcome-delay") * 20L);

    }

}
