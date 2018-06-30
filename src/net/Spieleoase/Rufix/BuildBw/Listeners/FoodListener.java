package net.Spieleoase.Rufix.BuildBw.Listeners;

import net.Spieleoase.Rufix.BuildBw.GameManager.GameStates;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class FoodListener implements Listener{
    @EventHandler
    public static void onFoodChange(FoodLevelChangeEvent e){
        if(GameStates.getGameState() != GameStates.State.INGAME){
            e.setCancelled(true);
        }
    }
}
