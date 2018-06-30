package net.Spieleoase.Rufix.BuildBw.ItemManager;

import net.Spieleoase.Rufix.BuildBw.BuildBw;
import net.Spieleoase.Rufix.BuildBw.Data.ArrayLists;
import net.Spieleoase.Rufix.BuildBw.Data.TextStrings;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;

import static net.Spieleoase.Rufix.BuildBw.GameManager.GameStates.GameState;
import static net.Spieleoase.Rufix.BuildBw.GameManager.GameStates.State.END;
import static net.Spieleoase.Rufix.BuildBw.GameManager.GameStates.State.INGAME;
import static net.Spieleoase.Rufix.BuildBw.GameManager.GameStates.State.LOBBY;

public class ClickHotbar implements Listener {
    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (e.getMaterial().equals(Material.BARRIER)) {
                    p.kickPlayer(TextStrings.Prefix + "Du hast das Spiel §cverlassen§8!");
            } else if (e.getMaterial().equals(Material.COMPASS)) {
                if (GameState == INGAME) {
                    if (ArrayLists.Spectators.contains(p)) {

                        int i = 9;

                        if (Bukkit.getOnlinePlayers().size() >= 8) {
                            i = 18;
                        } else if (Bukkit.getOnlinePlayers().size() >= 17) {
                            i = 27;
                        } else if (Bukkit.getOnlinePlayers().size() >= 26) {
                            i = 36;
                        } else if (Bukkit.getOnlinePlayers().size() >= 35) {
                            i = 45;
                        } else if (Bukkit.getOnlinePlayers().size() >= 53) {
                            i = 54;
                        } else if (Bukkit.getOnlinePlayers().size() >= 62) {
                            i = 63;
                        }

                        Inventory inv = Bukkit.createInventory(null, i, "§8» §cTeleporter");

                        for (Player all : ArrayLists.IngamePlayers) {

                            ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
                            SkullMeta itemmeta = (SkullMeta) item.getItemMeta();
                            itemmeta.setOwner(all.getName());
                            itemmeta.setDisplayName("§8» §b" + all.getDisplayName());
                            item.setItemMeta(itemmeta);

                            inv.addItem(item);
                        }

                        p.openInventory(inv);
                    }
                }
            }
        }
    }

    @EventHandler
    public static void onClick(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();
        if(e.getInventory().getName().equalsIgnoreCase("§5Teleporter §7§o(Rechtsklick)")) {

            for (Player all : ArrayLists.IngamePlayers) {
                if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(all.getDisplayName())) {
                    if (ArrayLists.IngamePlayers.contains(all)) {
                        p.teleport(all);
                        p.sendMessage(TextStrings.Prefix + "§7Du schaust nun §9" + all.getName() + " §7zu§8!");
                    } else {
                        p.sendMessage(TextStrings.Prefix + "§7Dieser Spieler ist tot§8!");
                    }
                }

            }
        }
    }
}
