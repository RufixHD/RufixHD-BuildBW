package net.Spieleoase.Rufix.BuildBw.Listeners;

import net.Spieleoase.Rufix.BuildBw.Data.ArrayLists;
import net.Spieleoase.Rufix.BuildBw.GameManager.GameStates;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageListener implements Listener{
    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if(e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();

            if (GameStates.getGameState() == GameStates.State.LOBBY || GameStates.getGameState() == GameStates.State.SCHUTZZEIT || GameStates.getGameState() == GameStates.State.END) {
                e.setCancelled(true);
            } else {
                if(ArrayLists.Spectators.contains(p)) {
                    e.setCancelled(true);
                }
            }
        }
    }
}
