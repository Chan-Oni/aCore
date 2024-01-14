package fr.atope.acore.events;

import com.google.common.collect.Sets;
import fr.atope.acore.ACore;
import fr.atope.acore.items.ItemManager;
import fr.leyra.objects.ItemStackBuilder;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.Blocks;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

public class FarmToolEvent implements Listener {

    private ACore main;

    private final Set<Block> pickaxe = Sets.newHashSet(Blocks.ACTIVATOR_RAIL, Blocks.COAL_ORE, Blocks.COBBLESTONE, Blocks.DETECTOR_RAIL, Blocks.DIAMOND_BLOCK, Blocks.DIAMOND_ORE,
            Blocks.DOUBLE_STONE_SLAB, Blocks.GOLDEN_RAIL, Blocks.GOLD_BLOCK, Blocks.GOLD_ORE, Blocks.ICE, Blocks.IRON_BLOCK, Blocks.IRON_ORE, Blocks.EMERALD_BLOCK, Blocks.EMERALD_ORE,
            Blocks.NETHERRACK, Blocks.QUARTZ_ORE, Blocks.QUARTZ_BLOCK, Blocks.QUARTZ_STAIRS,
            Blocks.LAPIS_BLOCK, Blocks.LAPIS_ORE, Blocks.LIT_REDSTONE_ORE, Blocks.MOSSY_COBBLESTONE, Blocks.NETHERRACK, Blocks.PACKED_ICE, Blocks.RAIL,
            Blocks.REDSTONE_ORE, Blocks.SANDSTONE, Blocks.RED_SANDSTONE, Blocks.STONE, Blocks.STONE_SLAB, Blocks.OBSIDIAN, Blocks.ANVIL, Blocks.ENCHANTING_TABLE, Blocks.ENDER_CHEST,
            Blocks.BEDROCK, Blocks.GLOWSTONE, Blocks.END_STONE, Blocks.GLASS, Blocks.GLASS_PANE, Blocks.STAINED_GLASS_PANE, Blocks.STAINED_GLASS, Blocks.STAINED_HARDENED_CLAY);
    private final Set<Block> axe = Sets.newHashSet(Blocks.CRAFTING_TABLE, Blocks.PLANKS, Blocks.BOOKSHELF, Blocks.LOG, Blocks.LOG2, Blocks.CHEST, Blocks.PUMPKIN, Blocks.LIT_PUMPKIN,
            Blocks.MELON_BLOCK, Blocks.LADDER);
    private final Set<Block> shovel = Sets.newHashSet(Blocks.CLAY, Blocks.DIRT, Blocks.FARMLAND, Blocks.GRASS, Blocks.GRAVEL, Blocks.MYCELIUM, Blocks.SAND, Blocks.SNOW,
            Blocks.SNOW_LAYER, Blocks.SOUL_SAND);
    private final Set<Block> shears = Sets.newHashSet(Blocks.WEB, Blocks.REDSTONE_WIRE, Blocks.TRIPWIRE, Blocks.LEAVES, Blocks.LEAVES2, Blocks.WOOL);

    public FarmToolEvent(ACore main) {
        this.main = main;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInteract(PlayerInteractEvent event) {

        if (event.getClickedBlock() == null) return;
        Block nmsBlock = org.bukkit.craftbukkit.v1_8_R3.util.CraftMagicNumbers.getBlock(event.getClickedBlock());
        ItemStack tool = event.getPlayer().getItemInHand();
        if (tool.getItemMeta() == null) return;
        if (!tool.getItemMeta().hasDisplayName()) return;
        if (!tool.getItemMeta().getDisplayName().equals(ItemManager.getInstance().getItems().get("farmtool").getItemMeta().getDisplayName())) return;

        System.out.println(event.getAction());

        if (event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            if (pickaxe.contains(nmsBlock)) {
                tool.setType(Material.DIAMOND_PICKAXE);
            } else if (shovel.contains(nmsBlock)) {
                tool.setType(Material.DIAMOND_SPADE);
            } else if (axe.contains(nmsBlock)) {
                tool.setType(Material.DIAMOND_AXE);
            } else if (shears.contains(nmsBlock)) {
                tool.setType(Material.SHEARS);
            }

        } else if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && (event.getClickedBlock().getType().equals(Material.GRASS) || event.getClickedBlock().getType().equals(Material.DIRT))) {
            tool.setType(Material.DIAMOND_HOE);
        } else if (event.getAction().equals(Action.LEFT_CLICK_AIR)) {
            tool.setType(Material.DIAMOND_SWORD);
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {

        ItemStack tool = event.getPlayer().getItemInHand();
        if (tool.getItemMeta() == null) return;
        if (!tool.getItemMeta().hasDisplayName()) return;
        if (!tool.getItemMeta().getDisplayName().equals(ItemManager.getInstance().getItems().get("farmtool").getItemMeta().getDisplayName())) return;

        ItemStack it = new ItemStackBuilder(event.getPlayer().getItemInHand()).removeCustDura(1, main.getDurabilityPlaceholder()).make();
        event.getPlayer().setItemInHand(it);

    }

}
