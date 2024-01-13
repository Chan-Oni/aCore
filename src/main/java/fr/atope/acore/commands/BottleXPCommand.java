package fr.atope.acore.commands;

import fr.atope.acore.ACore;
import fr.leyra.commands.Command;
import fr.leyra.commands.CommandArgs;
import fr.leyra.files.ConfigFile;
import fr.leyra.objects.ItemStackBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BottleXPCommand {

    private ACore main;

    public BottleXPCommand(ACore main) {
        this.main = main;
    }

    @Command(name = "bottlexp")
    public void bottlexpCommand(CommandArgs cmd) {

        FileConfiguration conf = main.getConfig();
        ConfigFile message = main.getMessageConfig();

        if (!cmd.isPlayer()) {
            cmd.getSender().sendMessage(message.getString("only-player"));
            return;
        }

        Player p = (Player) cmd.getSender();

        if (!p.hasPermission(conf.getString("permissions.bottle-exp-use"))) {
            p.sendMessage(message.getString("no-permission"));
            return ;
        }

        if (p.getInventory().firstEmpty() == -1) {
            cmd.getSender().sendMessage(ChatColor.RED + "Votre inventaire est plein !");
            return;
        }

        if (cmd.getArgs().length < 1) {
            p.sendMessage(ChatColor.RED + "/bottlexp [amount]");
            return;
        }

        int amount;
        try {
            amount = Integer.parseInt(cmd.getArgs(0));
        } catch (NumberFormatException e) {
            p.sendMessage(ChatColor.RED + "/bottlexp [amount]");
            return;
        }

        int xp = p.getLevel();

        if (amount > xp) {
            p.sendMessage(message.getString("not-enough-xp"));
            return;
        }

        p.setLevel(p.getLevel() - amount);

        List<String> lore = new ArrayList<>();
        String id = "bottle_xp_item.";
        for (String s1 : conf.getStringList(id+"lore")) {
            lore.add(
                    ChatColor.translateAlternateColorCodes('&',
                            s1
                                    .replaceAll("%player_name%", p.getName())
                                    .replaceAll("%xp%", amount + "")));
        }

        ItemStack bottle = new ItemStackBuilder(Material.GLASS_BOTTLE)
                .setName(conf.getString(id+"name")
                        .replaceAll("%player_name%", p.getName())
                        .replaceAll("%xp%", amount + ""))
                .addLores(lore)
                .setGlow(conf.getBoolean(id+"glow"))
                .setNBTString("unstackable-id", UUID.randomUUID().toString())
                .setNBTInteger("xp", amount)
                .make();

        p.getInventory().addItem(bottle);

        p.sendMessage(message.getString("exp-success"));

    }

}
