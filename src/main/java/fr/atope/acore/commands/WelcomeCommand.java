package fr.atope.acore.commands;

import fr.atope.acore.ACore;
import fr.atope.acore.events.PlayerJoinEvent;
import fr.leyra.commands.Command;
import fr.leyra.commands.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class WelcomeCommand {

    ACore main;

    public WelcomeCommand(ACore main) {
        this.main = main;
    }

    @Command(name="bienvenue", aliases = {"bvn", "welcome"})
    public void welcomeCommand(CommandArgs cmd) {
        if(!cmd.isPlayer()) {
            cmd.getSender().sendMessage(main.getMessageConfig().getString("only-player"));
            return;
        }
        Player player = PlayerJoinEvent.getLatestPlayer();
        if(player == null) {
            cmd.getSender().sendMessage(main.getMessageConfig().getString("no-one-to-welcome"));
            return;
        }
        if(player.equals(cmd.getPlayer())) {
            cmd.getSender().sendMessage(main.getMessageConfig().getString("cannot-welcome-itself"));
            return;
        }

        Bukkit.broadcastMessage(main.getConfigManager().getConfigFile("messages.yml").getString("welcome-message")
                .replaceAll("%player%", cmd.getPlayer().getName())
                .replaceAll("%new_player%", player.getName()));
    }

}
