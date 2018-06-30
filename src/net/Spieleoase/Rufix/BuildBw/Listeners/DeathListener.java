package net.Spieleoase.Rufix.BuildBw.Listeners;

import net.Spieleoase.Rufix.BuildBw.Data.ArrayLists;
import net.Spieleoase.Rufix.BuildBw.Data.HashMaps;
import net.Spieleoase.Rufix.BuildBw.Data.TextStrings;
import net.Spieleoase.Rufix.BuildBw.GameManager.GameStates;
import net.Spieleoase.Rufix.BuildBw.GameManager.setSpectator;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.util.Vector;

public class DeathListener implements Listener {
    @EventHandler
    public static void onDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        Player killer = e.getEntity().getKiller();

        if(p instanceof Player) {
            int Tode = HashMaps.DatabaseTode.get(p) + 1;
            HashMaps.DatabaseTode.put(p, Tode);
        }

        if(killer instanceof Player) {
            int KillsK = HashMaps.DatabaseKills.get(killer) + 1;
            HashMaps.DatabaseKills.put(killer, KillsK);
        }

        p.setHealth(20);

        if (ArrayLists.IngamePlayers.contains(p)) {
            ArrayLists.IngamePlayers.remove(p);
        }


        if (GameStates.getGameState() == GameStates.State.INGAME) {
            if (e.getEntity() instanceof Player) {
                if (killer != null && killer instanceof Player) {
                    e.setDeathMessage(TextStrings.Prefix + "§c" + p.getDisplayName() + " §7wurde von §a" + killer.getDisplayName() + " §7getötet§8!");
                } else {
                    e.setDeathMessage(TextStrings.Prefix + "§a" + p.getDisplayName() + " §7ist gestorben§8!");
                }
            }


            if (ArrayLists.IngamePlayers.size() >= 2) {
                Vector v = new Vector(0, 10, 0);
                p.setVelocity(v);
            }
            setSpectator.setSpec(p);
        }
    }
}
