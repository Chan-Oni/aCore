package fr.atope.acore.commands;

import fr.atope.acore.ACore;
import fr.leyra.commands.Command;
import fr.leyra.commands.CommandArgs;
import fr.leyra.files.ConfigFile;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.UUID;

public class VisionCommand {

    private ACore main;

    private static final HashMap<UUID, Boolean> night_vision = new HashMap<>();

    public VisionCommand(ACore main) {
        this.main = main;
    }

    @Command(name = "nightvision", aliases = "vision")
    public void visionCommand(CommandArgs cmd) {

        ConfigFile msg = main.getMessageConfig();

        if (!cmd.isPlayer()) {
            cmd.getSender().sendMessage(msg.getString("only-player"));
            return;
        }

        Player p = (Player) cmd.getSender();
        if (!p.hasPermission(main.getConfig().getString("permissions.vision-use"))) {
            p.sendMessage(msg.getString("no-permission"));
            return;
        }
        boolean k = night_vision.computeIfAbsent(p.getUniqueId(), uuid -> false);
        if (!k) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0, true));
            night_vision.put(p.getUniqueId(), true);
            p.sendMessage(msg.getString("vision-active"));
        } else {
            p.removePotionEffect(PotionEffectType.NIGHT_VISION);
            night_vision.put(p.getUniqueId(), false);
            p.sendMessage(msg.getString("vision-non-active"));
        }

    }

    public static HashMap<UUID, Boolean> getNightVision() {
        return night_vision;
    }

}