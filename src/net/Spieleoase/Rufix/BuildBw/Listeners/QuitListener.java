package net.Spieleoase.Rufix.BuildBw.Listeners;

import net.Spieleoase.Rufix.BuildBw.APIs.TitleAPI;
import net.Spieleoase.Rufix.BuildBw.BuildBw;
import net.Spieleoase.Rufix.BuildBw.Data.ArrayLists;
import net.Spieleoase.Rufix.BuildBw.Data.HashMaps;
import net.Spieleoase.Rufix.BuildBw.Data.TextStrings;
import net.Spieleoase.Rufix.BuildBw.GameManager.GameStates;
import net.Spieleoase.Rufix.BuildBw.GameManager.StateManager;
import net.Spieleoase.Rufix.BuildBw.MySQL.StatsDatabase;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;

import static net.Spieleoase.Rufix.BuildBw.Data.TextStrings.Prefix;
import static net.Spieleoase.Rufix.BuildBw.Data.TextStrings.QuitMessage;

public class QuitListener implements Listener {
    @EventHandler
    private void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();

        if(GameStates.getGameState() == GameStates.State.INGAME || GameStates.getGameState() == GameStates.State.SCHUTZZEIT){
            int Verloren = HashMaps.DatabaseVerloren.get(p) + 1;
            HashMaps.DatabaseVerloren.put(p, Verloren);
        }

        for (Player all : Bukkit.getOnlinePlayers()) {
            all.sendMessage(Prefix + p.getDisplayName() + QuitMessage);
        }
        StatsDatabase.HashmapToMySQL(p);
        ArrayLists.IngamePlayers.remove(p);
        e.setQuitMessage(null);

        if (ArrayLists.IngamePlayers.size() == 1) {
            for (Player all : Bukkit.getOnlinePlayers()) {
                all.sendMessage(TextStrings.Prefix + "§9" + p.getDisplayName() + " §7ist §causgeschieden§8!");
                all.sendMessage(TextStrings.Prefix + "§9" + ArrayLists.IngamePlayers.get(0).getDisplayName() + " §7hat §eBuildBW §7gewonnen§8!");
                TitleAPI.sendTitle(all, "§6" + ArrayLists.IngamePlayers.get(0).getDisplayName(), " §7hat §eBuildBW §7gewonnen§8!");
                TitleAPI.sendActionBar(p, "§6" + ArrayLists.IngamePlayers.get(0).getDisplayName() + " §7hat §eBuildBW §7gewonnen§8!");
                all.teleport((Location) BuildBw.getInstance().getConfig().get("locations.lobby"));
                if (ArrayLists.Spectators.contains(all)) {
                    ArrayLists.Spectators.remove(all);
                }
            }
            StateManager.setEnd();
        } else {
            for (Player all : Bukkit.getOnlinePlayers()) {
                all.sendMessage(TextStrings.Prefix + "§6" + p.getDisplayName() + " §7ist §causgeschieden§8!");
            }
        }
    }
}
