package net.Spieleoase.Rufix.BuildBw.GameManager;

import net.Spieleoase.Rufix.BuildBw.APIs.TitleAPI;
import net.Spieleoase.Rufix.BuildBw.BuildBw;
import net.Spieleoase.Rufix.BuildBw.Data.ArrayLists;
import net.Spieleoase.Rufix.BuildBw.Data.TextStrings;
import net.Spieleoase.Rufix.BuildBw.ItemManager.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class setSpectator {
    public static void setSpec(Player p) {
        ArrayLists.Spectators.add(p);

        p.setAllowFlight(true);
        p.setFlying(true);

        ItemCreator.setInv(p, 8, Material.BARRIER, "§8» §cVerlassen");
        ItemCreator.setInv(p, 0, Material.COMPASS, "§8» §cNavigator");

        for (Player all : Bukkit.getOnlinePlayers()) {
            if (!ArrayLists.Spectators.contains(all)) {
                all.hidePlayer(p);
            }
            if (ArrayLists.Spectators.contains(p)) {
                p.showPlayer(all);
            }
        }

        p.sendMessage(TextStrings.Prefix + "§7Du bist nun Spectator§8!");

        if (ArrayLists.IngamePlayers.size() == 1) {
            for (Player all : Bukkit.getOnlinePlayers()) {
                all.sendMessage(TextStrings.Prefix + "§9" + p.getDisplayName() + " §7ist §causgeschieden§8!");
                all.sendMessage(TextStrings.Prefix + "§9" + ArrayLists.IngamePlayers.get(0).getDisplayName() + " §7hat §eBuildBW §7gewonnen§8!");
                TitleAPI.sendTitle(all, "§6" + ArrayLists.IngamePlayers.get(0).getDisplayName(), " §7hat §eBuildBW §7gewonnen§8!");
                TitleAPI.sendActionBar(p, "§6" + ArrayLists.IngamePlayers.get(0).getDisplayName() + " §7hat §eBuildBW §7gewonnen§8!");
            }

            StateManager.setEnd();
        } else {
            for (Player all : Bukkit.getOnlinePlayers()) {
                all.sendMessage(TextStrings.Prefix + "§9" + p.getDisplayName() + " §7ist §causgeschieden§8!");
            }

            Bukkit.getScheduler().runTaskLaterAsynchronously(BuildBw.getInstance(), new Runnable() {
                @Override
                public void run() {
                    if (GameStates.getGameState() == GameStates.State.INGAME) {
                        p.teleport((Location) BuildBw.getInstance().getConfig().get("locations.spectator"));
                    }
                }
            }, 20);

            TitleAPI.sendTitle(p, "§7Du bist §causgeschieden§8!", "");
            TitleAPI.sendActionBar(p, "§7Du bist §causgeschieden§8!");
        }
    }

    public static void setNoSpec(Player p) {
        p.setAllowFlight(false);
        p.setFlying(false);
        for (Player all : Bukkit.getOnlinePlayers()) {
            p.showPlayer(all);
        }
        p.getInventory().clear();
        ItemCreator.setInv(p, 8, Material.BARRIER, "§8» §cVerlassen");
        p.teleport((Location) BuildBw.getInstance().getConfig().get("locations.lobby"));
    }
}
