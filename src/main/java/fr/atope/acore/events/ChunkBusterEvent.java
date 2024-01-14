package fr.atope.acore.events;

import fr.atope.acore.ACore;
import fr.atope.acore.items.ItemManager;
import fr.atope.acore.menu.ChunkBusterMenu;
import fr.leyra.objects.ItemStackBuilder;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class ChunkBusterEvent implements Listener {

    private ACore main;

    public ChunkBusterEvent(ACore main) {
        this.main = main;
    }

    @EventHandler
    public void onChunkBuster(BlockPlaceEvent event) {
        if (event.getBlockPlaced() == null) return;
        if (event.getItemInHand() == null) return;
        if (!ItemStackBuilder.checkItemsLoreName(ItemManager.getInstance().getItems().get("chunk_buster"), event.getItemInHand()))
            return;

        if (main.getFactionManager().isInEnnemyTerritory(event.getPlayer())) {
            event.setCancelled(true);
            return;
        }

        event.getPlayer().setItemInHand(ItemStackBuilder.removeCount(event.getItemInHand()));

        if (!main.getConfigFile().getBoolean("buster-menu-confirmation")) {
            initializeChunkBuster(event.getBlockPlaced().getLocation());
            return;
        }

        event.setCancelled(true);
        ChunkBusterMenu chunkBusterMenu = new ChunkBusterMenu(event.getPlayer(), event.getBlockPlaced().getLocation());
        chunkBusterMenu.open();

    }

    public void initializeChunkBuster(Location location) {

        location.getBlock().setType(Material.getMaterial(main.getItemsConfig().getString("chunk_buster.material")));
        spawnBusterParticles(location);

        if (main.getConfigFile().getInt("delay-before-bust") != 0) {
            location.getWorld().getNearbyEntities(location, 10, 10, 10).forEach(entity -> {
                if (!(entity instanceof Player)) return;
                Player p = (Player) entity;
                p.sendMessage(main.getMessageConfig().getString("chunk-buster-initialization").replace("%delay%", main.getConfigFile().getInt("delay-before-bust") + ""));
            });
        }

        Chunk chunk = location.getChunk();

        new BukkitRunnable() {
            @Override
            public void run() {
                clearChunk(chunk);
            }
        }.runTaskLater(main, main.getConfigFile().getInt("delay-before-bust") * 20L);

    }

    private void spawnBusterParticles(Location location) {

        int delay = main.getConfigFile().getInt("buster-particles-delay") * 2;
        if (delay <= 0) {
            return;
        }
        location = location.add(0.5, 0, 0.5);
        Location finalLocation = location;
        BukkitRunnable br = new BukkitRunnable() {

            int ticks = 0;
            final Random random = new Random();

            @Override
            public void run() {

                ticks++;
                if (ticks >= delay) {
                    cancel();
                    return;
                }

                for (int i = 0; i < 100; i++) {
                    double theta = 2 * Math.PI * random.nextDouble();
                    double phi = Math.acos(2 * random.nextDouble() - 1);
                    double radius = 1.4 * Math.cbrt(random.nextDouble());

                    double x = finalLocation.getX() + radius * Math.sin(phi) * Math.cos(theta);
                    double y = finalLocation.getY() + 1 + radius * Math.cos(phi);
                    double z = finalLocation.getZ() + radius * Math.sin(phi) * Math.sin(theta);

                    main.getParticleManager().spawnColoredParticles(new Location(finalLocation.getWorld(), x, y, z), random.nextInt(255), random.nextInt(255), random.nextInt(255));
                }
            }
        };

        br.runTaskTimer(main, 0, 10);
    }

    private void clearChunk(Chunk chunk) {
        int chunkX = chunk.getX() * 16;
        int chunkZ = chunk.getZ() * 16;

        ArrayList<Block> blocks = new ArrayList<>();

        if (!main.getConfigFile().getBoolean("buster-animation")) {
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    for (int y = 0; y < 256; y++) {
                        Location current = new Location(chunk.getWorld(), chunkX + x, y, chunkZ + z);
                        if (!current.getBlock().getType().equals(Material.AIR) && !current.getBlock().getType().equals(Material.BEDROCK)) {
                            blocks.add(current.getBlock());
                        }
                    }
                }
            }
            Collections.shuffle(blocks);
            for (Block block : blocks) {
                block.setType(Material.AIR);
            }
            blocks.clear();
        } else {
            new BukkitRunnable() {

                int y = 255;
                int z1 = 0;
                int x = chunkX;
                Block block;

                @Override
                public void run() {
                    for (int x1 = 0; x1 < 16; x1++) {
                        block = chunk.getBlock(x + x1, y, z1);
                        if (!block.getType().equals(Material.AIR) && !block.getType().equals(Material.BEDROCK)) {
                            block.setType(Material.AIR);
                        }
                    }
                    z1++;
                    if(z1==16) {
                        z1=0;
                        y--;
                    }

                    if (y < 0) {
                        cancel();
                    }
                }
            }.runTaskTimer(main, 0, 1);
        }
    }

}
