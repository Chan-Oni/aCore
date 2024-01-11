package fr.atope.acore.commands;

import fr.atope.acore.ACore;
import fr.leyra.commands.Command;
import fr.leyra.commands.CommandArgs;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;

public class ChatCommands {

    private ACore main;

    @Getter @Setter
    private static boolean chatDisabled = false;

    public ChatCommands(ACore main) {
        this.main = main;
    }

    @Command(name = "setchat", aliases = "chat")
    public void chatcommands(CommandArgs cmd) {
        String permission = main.getConfig().getString("permissions.chat-command-use");
        String prefix = main.getPrefix();

        if (!cmd.getSender().hasPermission(permission)) {
            return;
        }

        if (cmd.getArgs().length < 1) {
            cmd.getSender().sendMessage(prefix + ChatColor.RED + "/chat on/off");
            return;
        }

        String statusMessage = isChatDisabled() ? ChatColor.RED + "désactivé" + ChatColor.GRAY : ChatColor.GREEN + "activé" + ChatColor.GRAY;

        if ((cmd.getArgs(0).equals("off") && isChatDisabled()) || (cmd.getArgs(0).equals("on") && !isChatDisabled())) {
            cmd.getSender().sendMessage(prefix + ChatColor.RED + " Le chat est déjà " + statusMessage + ".");
        } else {
            setChatDisabled(!isChatDisabled());
            statusMessage = isChatDisabled() ? ChatColor.RED + "désactivé" + ChatColor.GRAY : ChatColor.GREEN + "activé" + ChatColor.GRAY;
            cmd.getSender().sendMessage(prefix + ChatColor.GRAY + " Le chat est désormais " + statusMessage + ".");
        }
    }

}
