package net.Spieleoase.Rufix.BuildBw.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WeatherListener implements Listener{
    @EventHandler
    public static void WeatherChange(WeatherChangeEvent e){
        e.setCancelled(true);
    }
}
