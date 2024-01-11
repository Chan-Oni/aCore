package fr.atope.acore.commands;

import fr.atope.acore.ACore;
import fr.atope.acore.items.ItemManager;
import fr.atope.acore.menu.AMenu;
import fr.leyra.commands.Command;
import fr.leyra.commands.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class AItemsCommand {

    private ACore main;

    public AItemsCommand(ACore main) {
        this.main = main;
    }

    @Command(name = "aitems", aliases = "aitem")
    public void itemsCommand(CommandArgs cmd) {

        if (!cmd.getSender().hasPermission(main.getConfig().getString("permissions.items-command"))) return;

        if (cmd.getArgs().length == 0) {
            if (!cmd.isPlayer()) return;
            AMenu menu = new AMenu(cmd.getPlayer());
            menu.open();
        }

    }

    @Command(name = "aitems.give", aliases = "aitem.give", usage = "aitems give [player] [item_id] [<amount>]")
    public void itemsGiveCommand(CommandArgs cmd) {

        if (!cmd.getSender().hasPermission(main.getConfig().getString("permissions.items-command"))) return;

        if (cmd.getArgs().length < 3) {
            cmd.getSender().sendMessage(ChatColor.RED + "/aitems give %player% %item% %amount%");
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
            cmd.getSender().sendMessage(ChatColor.RED + "/aitems give %player% %item% %amount%");
            return;
        }

        ItemStack item = ItemManager.getInstance().getItems().get(cmd.getArgs(1));
        if (item == null) {
            cmd.getSender().sendMessage(ChatColor.RED + "Item inconnu, voici la liste disponible : ");
            ItemManager.getInstance().getItems().forEach((s, itemStack) -> cmd.getSender().sendMessage(ChatColor.RED + s));
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

    @Command(name = "aitems.list", aliases = "aitem.list")
    public void itemsListCommand(CommandArgs cmd) {

        if(!cmd.getSender().hasPermission(main.getConfig().getString("permissions.items-list"))) return;

        cmd.getSender().sendMessage(ChatColor.GRAY + "Voici la liste des items disponibles : ");
        ItemManager.getInstance().getItems().forEach((s, itemStack) -> cmd.getSender().sendMessage(ChatColor.GRAY + "- " + ChatColor.WHITE + s));

    }

}
