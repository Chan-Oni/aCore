package fr.atope.acore.commands;

import fr.atope.acore.ACore;
import fr.leyra.commands.Command;
import fr.leyra.commands.CommandArgs;
import fr.leyra.gui.Menu;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class TrashCommand implements Listener {

    private ACore main;

    public TrashCommand(ACore main) {
        this.main = main;
    }

    @Command(name = "trash", aliases = {"poubelle", "bin"})
    public void trashCommand(CommandArgs cmd) {

        if(!cmd.isPlayer()) {
            cmd.getSender().sendMessage(main.getMessageConfig().getString("only-player"));
            return;
        }

        if(!cmd.getSender().hasPermission(main.getConfigFile().getString("permissions.trash-use"))) {
            cmd.getSender().sendMessage(main.getMessageConfig().getString("no-permission"));
        }

        Menu trashMenu = new Menu(cmd.getPlayer()) {
            @Override
            public String getMenuName() {
                return ChatColor.translateAlternateColorCodes('&', main.getConfigFile().getString("trash-menu-name"));
            }

            @Override
            public int getSlots() {
                return 54;
            }

            @Override
            public void handleMenu(InventoryClickEvent inventoryClickEvent) {

            }

            @Override
            public void closeMenu(InventoryCloseEvent inventoryCloseEvent) {

            }

            @Override
            public void setMenuItems() {

            }
        };

        trashMenu.open();

    }

}
