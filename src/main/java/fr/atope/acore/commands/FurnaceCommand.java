package fr.atope.acore.commands;

import fr.atope.acore.ACore;
import fr.leyra.commands.Command;
import fr.leyra.commands.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.Iterator;

public class FurnaceCommand {

    private final ACore main;

    public FurnaceCommand(ACore main) {
        this.main = main;
    }

    @Command(name = "furnace", description = "Fais cuire vos ressources")
    public void onCommand(CommandArgs cmd) {

        if (!cmd.isPlayer()) {
            cmd.getSender().sendMessage(main.getMessageConfig().getString("only-player"));
            return;
        }

        if (!cmd.getSender().hasPermission(main.getConfigFile().getString("permissions.trash-use"))) {
            cmd.getSender().sendMessage(main.getMessageConfig().getString("no-permission"));
        }

        Player player = cmd.getPlayer();

        ItemStack result = null;
        final ItemStack baseItem = player.getItemInHand();

        final Iterator<Recipe> i = Bukkit.recipeIterator();
        while (i.hasNext()) {
            Recipe recipe = i.next();
            if (!(recipe instanceof FurnaceRecipe)) continue;
            FurnaceRecipe furnaceRecipe = (FurnaceRecipe) recipe;
            if (furnaceRecipe.getInput().getType() != baseItem.getType()) continue;
            result = furnaceRecipe.getResult();
            result.setAmount(baseItem.getAmount());
            break;
        }
        if (result != null) {
            player.sendMessage(replaceAll(main.getMessageConfig().getString("furnace-correct"), baseItem));
            player.setItemInHand(result);
        } else {
            player.sendMessage(replaceAll(main.getMessageConfig().getString("furnace-incorrect"), baseItem));
        }

    }

    public String replaceAll(String string, ItemStack item) {
        string = string
                .replaceAll("%amount%", "" + item.getAmount())
                .replaceAll("%item%", item.getType().name().toLowerCase().replaceAll("_", " "));
        return string;

    }

}
