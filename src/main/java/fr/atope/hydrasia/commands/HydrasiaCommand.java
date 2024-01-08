package fr.atope.hydrasia.commands;

import fr.atope.hydrasia.HydrasiaItems;
import fr.atope.hydrasia.items.ItemManager;
import fr.atope.hydrasia.menu.HydrasiaMenu;
import fr.leyra.commands.Command;
import fr.leyra.commands.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class HydrasiaCommand {

    private HydrasiaItems main;

    public HydrasiaCommand(HydrasiaItems main) {
        this.main = main;
    }

    @Command(name = "hydrasia")
    public void hydrasiaCommand(CommandArgs cmd) {

        if (!cmd.getSender().hasPermission(main.getConfig().getString("permissions.hydrasia-command"))) return;

        if (cmd.getArgs().length == 0) {
            if (!cmd.isPlayer()) return;
            HydrasiaMenu menu = new HydrasiaMenu(cmd.getPlayer());
            menu.open();
        }

    }

    @Command(name = "hydrasia.give")
    public void hydrasiaGive(CommandArgs cmd) {

        if (!cmd.getSender().hasPermission(main.getConfig().getString("permissions.hydrasia-command"))) return;

        // /hydrasia give %player% %item% %amount%
        if (cmd.getArgs().length < 3) {
            cmd.getSender().sendMessage(ChatColor.RED + "/hydrasia give %player% %item% %amount%");
            return;
        }

        Player target = Bukkit.getPlayer(cmd.getArgs(0));
        if (target == null || !target.isOnline()) {
            cmd.getSender().sendMessage(ChatColor.RED + "Joueur introuvable.");
            return;
        }

        int amount;
        try {
            amount = Integer.parseInt(cmd.getArgs(2));
        } catch (NumberFormatException e) {
            cmd.getSender().sendMessage(ChatColor.RED + "/hydrasia give %player% %item% %amount%");
            return;
        }

        ItemStack item = ItemManager.getINSTANCE().items.get(cmd.getArgs(1));
        if (item == null) {
            cmd.getSender().sendMessage(ChatColor.RED + "Item inconnu, voici la liste disponible : ");
            ItemManager.getINSTANCE().items.forEach((s, itemStack) -> {
                cmd.getSender().sendMessage(ChatColor.RED + s);
            });
            return;
        }

        if (target.getInventory().firstEmpty() == -1) {
            cmd.getSender().sendMessage(ChatColor.RED + "Le joueur désigné a un inventaire plein !");
            return;
        }

        for (int i = 0; i < amount; i++) {
            target.getInventory().addItem(item);
        }

    }

}
