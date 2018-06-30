package net.Spieleoase.Rufix.BuildBw.ItemManager;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemCreator {

    public static ItemStack Item(Material material) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack Item(Material material, int anzahl) {
        ItemStack item = new ItemStack(material, anzahl);
        ItemMeta meta = item.getItemMeta();
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack Item(Material material, String displayname) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayname);
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack Item(Material material, Enchantment enchant1, int enchantnum1) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(enchant1, enchantnum1, true);
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack Item(Material material, Enchantment enchant1, int enchantnum1, Enchantment enchant2, int enchantnum2) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(enchant1, enchantnum1, true);
        meta.addEnchant(enchant2, enchantnum2, true);
        item.setItemMeta(meta);

        return item;
    }

    public static void setInv(Player p, int slot, Material item, String display) {
        p.getInventory().setItem(slot, Item(item, display));
    }

    public static void setInv(Inventory inv, Material item, int slot) {
        if (inv.getItem(slot) == null) {
            inv.setItem(slot, Item(item));
        }
    }

    public static void setInv(Inventory inv, Material item, int slot, int anzahl) {
        if (inv.getItem(slot) == null) {
            inv.setItem(slot, Item(item, anzahl));
        }
    }

    public static void setInv(Inventory inv, Material item, int slot, Enchantment enchant1, int enchantnum1) {
        if (inv.getItem(slot) == null) {
            inv.setItem(slot, Item(item, enchant1, enchantnum1));
        }
    }

    public static void setInv(Inventory inv, Material item, int slot, Enchantment enchant1, int enchantnum1, Enchantment enchant2, int enchantnum2) {
        if (inv.getItem(slot) == null) {
            inv.setItem(slot, Item(item, enchant1, enchantnum1, enchant2, enchantnum2));
        }
    }

    public static void addInv(Inventory inv, Material item) {
        inv.addItem(Item(item));
    }

    public static void addInv(Inventory inv, Material item, Enchantment enchant1, int enchantnum1) {
        inv.addItem(Item(item, enchant1, enchantnum1));
    }
}
