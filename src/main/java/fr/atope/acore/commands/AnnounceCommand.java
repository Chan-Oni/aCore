package fr.atope.acore.commands;

import fr.atope.acore.ACore;
import fr.leyra.commands.Command;
import fr.leyra.commands.CommandArgs;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class AnnounceCommand {

    @Command(name = "annonce")
    public void announceCommand(CommandArgs cmd) {
        StringBuilder message = new StringBuilder();
        for (String part : cmd.getArgs()) {
            if (!message.toString().equals("")) message.append(" ");
            message.append(part);
        }
        Bukkit.getServer().broadcastMessage(ACore.getInstance().getPrefix() + ChatColor.translateAlternateColorCodes('&', message + ""));

    }

    @Command(name = "annoncebar")
    public void announceBarCommand(CommandArgs cmd) {
        StringBuilder message = new StringBuilder();
        for (String part : cmd.getArgs()) {
            if (!message.toString().equals("")) message.append(" ");
            message.append(part);
        }
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            sendActionText(onlinePlayer, ChatColor.translateAlternateColorCodes('&', message + ""));
        }

    }

    public void sendActionText(Player player, String message) {
        PacketPlayOutChat packet = new PacketPlayOutChat(new ChatComponentText(message), (byte) 2);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }

}
