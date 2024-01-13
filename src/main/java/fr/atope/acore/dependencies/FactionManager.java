package fr.atope.acore.dependencies;

import cc.javajobs.factionsbridge.FactionsBridge;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Claim;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FactionsAPI;
import fr.atope.acore.ACore;
import org.bukkit.entity.Player;

public class FactionManager {

    private final ACore main = ACore.getInstance();
    private final FactionsAPI api = FactionsBridge.getFactionsAPI();

    public boolean isInEnnemyTerritory(Player player) {
        Claim chunk = api.getClaim(player.getLocation());
        if (!chunk.isClaimed()) return false;
        if(api.getFPlayer(player.getUniqueId()).getFaction().getAllClaims().contains(chunk)) return false;
        return true;
    }

    public boolean isInClaimedTerritory(Player player) {
        return api.getClaim(player.getLocation()).isClaimed();
    }

}
