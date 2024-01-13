package fr.atope.acore.events;

import fr.atope.acore.ACore;
import fr.atope.acore.items.ItemManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class ChunkBusterEvent implements Listener {

    private ACore main;

    public ChunkBusterEvent(ACore main) {
        this.main = main;
    }

    @EventHandler
    public void onChunkBuster(BlockPlaceEvent event) {
        if (event.getBlockPlaced() == null) return;
        if (event.getItemInHand() == null) return;
        if (!event.getItemInHand().equals(ItemManager.getInstance().getItems().get("chunk_buster"))) return;

        // CHECK FAC

        if (main.getFactionManager().isInClaimedTerritory(event.getPlayer())) {
            event.setCancelled(true);
            return;
        }


    }

}
