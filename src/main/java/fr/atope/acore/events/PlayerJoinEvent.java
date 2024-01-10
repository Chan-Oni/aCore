package fr.atope.acore.events;

import fr.atope.acore.ACore;
import fr.atope.acore.commands.VisionCommand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerJoinEvent implements Listener {

    @EventHandler
    public void visionJoin(org.bukkit.event.player.PlayerJoinEvent event) {

        boolean k = VisionCommand.getNightVision().computeIfAbsent(event.getPlayer().getUniqueId(), key -> false);
        if (k) {
            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0, true));
            event.getPlayer().sendMessage(ACore.getInstance().getConfigManager().getConfigFile("messages.yml").getString("vision-active"));
        } else {
            event.getPlayer().removePotionEffect(PotionEffectType.NIGHT_VISION);
            event.getPlayer().sendMessage(ACore.getInstance().getConfigManager().getConfigFile("messages.yml").getString("vision-non-active"));
        }

    }

}
