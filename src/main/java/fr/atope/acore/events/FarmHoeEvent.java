package fr.atope.acore.events;

import fr.atope.acore.ACore;
import fr.atope.acore.items.ItemManager;
import fr.leyra.objects.ItemStackBuilder;
import fr.leyra.utils.Randomness;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class FarmHoeEvent implements Listener {

    private final ACore main;

    public FarmHoeEvent(ACore main) {

        this.main = main;

    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void playerBreakBlock(BlockBreakEvent event) {

        Player p = event.getPlayer();
        Block b = event.getBlock();
        if (!main.getWorldGuardManager().canBuild(event.getPlayer(), event.getBlock().getLocation())) return;
        ItemStack it = event.getPlayer().getItemInHand();
        if (!ItemStackBuilder.checkItemsWithoutLore(it, ItemManager.getInstance().getItems().get("farm_hoe"))) return;

        if (b.getType() == null) return;

        event.setCancelled(true);

        Material m = b.getType();
        if (m == null) return;

        int radius = 1;

        if(getSeedType(m) == null) return;

        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                Block bR = b.getRelative(x, 0, z);
                if (bR == null) return;
                if (!isCropMature(bR)) {
                    continue;
                }
                if(bR.getType().equals(Material.CROPS) && !main.getConfig().getBoolean("farm-hoe-drops-seed")) {
                    p.getWorld().dropItemNaturally(bR.getLocation(), new ItemStack(Material.WHEAT, new Randomness().getRandomInt(1, 3)));
                } else {
                    bR.breakNaturally();
                }
                if ((!m.equals(Material.NETHER_WARTS) && !bR.getRelative(0, -1, 0).getType().equals(Material.SOIL))
                        || (m.equals(Material.NETHER_WARTS) && !bR.getRelative(0, -1, 0).getType().equals(Material.SOUL_SAND)))
                    continue;
                bR.setType(m);
            }
        }

        it = new ItemStackBuilder(it).removeCustDura(1, main.getDurabilityPlaceholder()).make();
        p.setItemInHand(it);

    }

    private Material getSeedType(Material cropType) {
        switch (cropType) {
            case CROPS:
                return Material.WHEAT;
            case CARROT:
                return Material.CARROT_ITEM;
            case POTATO:
                return Material.POTATO_ITEM;
            case NETHER_WARTS:
                return Material.NETHER_WARTS;
            default:
                return null;
        }
    }

    public boolean isCropMature(Block block) {
        Material type = block.getType();
        if (type == Material.CARROT || type == Material.POTATO || type == Material.CROPS) {
            byte data = block.getData();
            return data == 7;
        } else if (type == Material.NETHER_WARTS) {
            byte data = block.getData();
            return data == 3;
        }
        return false;
    }

}
