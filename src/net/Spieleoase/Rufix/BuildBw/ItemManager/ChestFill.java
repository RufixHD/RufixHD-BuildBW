package net.Spieleoase.Rufix.BuildBw.ItemManager;

import net.Spieleoase.Rufix.BuildBw.Data.ArrayLists;
import net.Spieleoase.Rufix.BuildBw.Data.HashMaps;
import net.Spieleoase.Rufix.BuildBw.GameManager.GameStates;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import java.util.Random;

public class ChestFill implements Listener {
    @EventHandler
    public static void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        if (GameStates.getGameState() == GameStates.State.INGAME || GameStates.getGameState() == GameStates.State.SCHUTZZEIT) {
            System.out.println(1);

            if (ArrayLists.Spectators.contains(p)) {
                e.setCancelled(true);
                return;
            }

            if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                System.out.println(2);
                if (e.getClickedBlock().getType() == Material.CHEST) {
                    System.out.println(3);
                    Chest chest = (Chest) e.getClickedBlock().getState();
                    Location loc = chest.getLocation();
                    Random rdm = new Random();
                    Inventory inv = chest.getInventory();

                    if (!ArrayLists.chest.contains(loc)) {
                        inv.clear();
                        ArrayLists.chest.add(loc);
                        for (int i = 0; i < 40; i++) {
                            int Prozent = rdm.nextInt(100);
                            int zuf = rdm.nextInt(65);
                            int slot = rdm.nextInt(26);
                            if (Prozent >= 0 && Prozent <= 50) {
                                switch (zuf) {
                                    case 0:
                                        ItemCreator.setInv(inv, Material.LEATHER_BOOTS, slot);
                                        break;
                                    case 1:
                                        ItemCreator.setInv(inv, Material.LEATHER_LEGGINGS, slot);
                                        break;
                                    case 2:
                                        ItemCreator.setInv(inv, Material.LEATHER_CHESTPLATE, slot);
                                        break;
                                    case 3:
                                        ItemCreator.setInv(inv, Material.LEATHER_HELMET, slot);
                                        break;
                                    case 4:
                                        ItemCreator.setInv(inv, Material.GOLD_SWORD, slot);
                                        break;
                                    case 5:
                                        ItemCreator.setInv(inv, Material.APPLE, slot, 5);
                                        break;
                                    case 6:
                                        ItemCreator.setInv(inv, Material.SANDSTONE, slot, 10);
                                        break;
                                }
                            } else if (Prozent >= 50 && Prozent <= 80) {
                                switch (zuf) {
                                    case 0:
                                        ItemCreator.setInv(inv, Material.LEATHER_BOOTS, slot, Enchantment.PROTECTION_ENVIRONMENTAL, 1, Enchantment.DURABILITY, 1);
                                        break;
                                    case 1:
                                        ItemCreator.setInv(inv, Material.LEATHER_LEGGINGS, slot, Enchantment.PROTECTION_ENVIRONMENTAL, 1, Enchantment.DURABILITY, 1);
                                        break;
                                    case 2:
                                        ItemCreator.setInv(inv, Material.LEATHER_HELMET, slot, Enchantment.PROTECTION_ENVIRONMENTAL, 1, Enchantment.DURABILITY, 1);
                                        break;
                                    case 3:
                                        ItemCreator.setInv(inv, Material.CHAINMAIL_CHESTPLATE, slot);
                                        break;
                                    case 4:
                                        ItemCreator.setInv(inv, Material.STICK, slot, Enchantment.KNOCKBACK, 1);
                                        break;
                                    case 5:
                                        ItemCreator.setInv(inv, Material.COOKED_BEEF, slot, 3);
                                        break;
                                    case 6:
                                        ItemCreator.setInv(inv, Material.GOLD_SWORD, slot, Enchantment.DAMAGE_ALL, 1, Enchantment.DURABILITY, 1);
                                        break;
                                }
                            } else if (Prozent >= 80 && Prozent <= 100) {
                                switch (zuf) {
                                    case 0:
                                        ItemCreator.setInv(inv, Material.CHAINMAIL_CHESTPLATE, slot, Enchantment.PROTECTION_ENVIRONMENTAL, 1, Enchantment.DURABILITY, 1);
                                        break;
                                }
                            }
                        }
                        if (HashMaps.ChestFillCheck.containsKey(Material.STICK)) {
                            if (HashMaps.ChestFillCheck.get(Material.STICK) <= 2) {
                                int nummber = HashMaps.ChestFillCheck.get(Material.STICK) + 1;
                                HashMaps.ChestFillCheck.put(Material.STICK, nummber);
                                ItemCreator.addInv(inv, Material.STICK, Enchantment.KNOCKBACK, 2);
                            } else if (HashMaps.ChestFillCheck.containsKey(Material.GOLDEN_APPLE)) {
                                if (HashMaps.ChestFillCheck.get(Material.GOLDEN_APPLE) <= 2) {
                                    int nummber = HashMaps.ChestFillCheck.get(Material.GOLDEN_APPLE) + 1;
                                    HashMaps.ChestFillCheck.put(Material.GOLDEN_APPLE, nummber);
                                    ItemCreator.addInv(inv, Material.GOLDEN_APPLE);
                                } else if (HashMaps.ChestFillCheck.containsKey(Material.FISHING_ROD)) {
                                    if (HashMaps.ChestFillCheck.get(Material.FISHING_ROD) <= 2) {
                                        int nummber = HashMaps.ChestFillCheck.get(Material.FISHING_ROD) + 1;
                                        HashMaps.ChestFillCheck.put(Material.FISHING_ROD, nummber);
                                        ItemCreator.addInv(inv, Material.FISHING_ROD);
                                    }
                                } else {
                                    HashMaps.ChestFillCheck.put(Material.FISHING_ROD, 1);
                                    ItemCreator.addInv(inv, Material.FISHING_ROD);
                                }
                            } else {
                                HashMaps.ChestFillCheck.put(Material.GOLDEN_APPLE, 1);
                                ItemCreator.addInv(inv, Material.GOLDEN_APPLE);
                            }
                        } else {
                            HashMaps.ChestFillCheck.put(Material.STICK, 1);
                            ItemCreator.addInv(inv, Material.STICK, Enchantment.KNOCKBACK, 2);
                        }
                    }
                }
            }
        }
    }
}
