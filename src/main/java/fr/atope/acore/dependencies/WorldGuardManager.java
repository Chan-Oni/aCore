package fr.atope.acore.dependencies;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import fr.atope.acore.ACore;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class WorldGuardManager {

    private ACore main = ACore.getInstance();

    public boolean canBuild(Player player, Location location) {

        if (player.isOp()) return true;

        LocalPlayer lplayer = WorldGuardPlugin.inst().wrapPlayer(player);

        RegionContainer container = main.getWorldGuard().getPlatform().getRegionContainer();
        RegionQuery query = container.createQuery();

        boolean canBypass = WorldGuard.getInstance().getPlatform().getSessionManager().hasBypass(lplayer, lplayer.getWorld());
        if (canBypass) return true;

        return query.testState(new com.sk89q.worldedit.util.Location(lplayer.getWorld(), location.getX(), location.getY(), location.getZ()), lplayer, Flags.BLOCK_BREAK);

    }

}
