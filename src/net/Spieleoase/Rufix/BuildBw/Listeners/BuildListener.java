package net.Spieleoase.Rufix.BuildBw.Listeners;

import net.Spieleoase.Rufix.BuildBw.BuildBw;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BuildListener implements Listener {
    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if (BuildBw.getInstance().getConfig().getString("SetupMode") == "false") {
            Player p = e.getPlayer();
            final Block block = e.getBlockPlaced();
            if (block.getType().equals(Material.SANDSTONE)) {
                Bukkit.getScheduler().scheduleSyncDelayedTask(BuildBw.getInstance(), new Runnable() {
                    public void run() {
                        block.setType(Material.REDSTONE_BLOCK);
                        Bukkit.getScheduler().scheduleSyncDelayedTask(BuildBw.getInstance(), new Runnable() {
                            public void run() {
                                block.setType(Material.AIR);
                            }
                        }, 100L);
                    }
                }, 100L);
            } else {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public static void onBreak(BlockBreakEvent e) {
        if (BuildBw.getInstance().getConfig().getString("SetupMode") == "false") {
            e.setCancelled(true);
        }
    }
}
