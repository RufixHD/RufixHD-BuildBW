package net.Spieleoase.Rufix.BuildBw.GameManager;

import net.Spieleoase.Rufix.BuildBw.BuildBw;
import net.Spieleoase.Rufix.BuildBw.Data.ArrayLists;
import net.Spieleoase.Rufix.BuildBw.Data.HashMaps;
import net.Spieleoase.Rufix.BuildBw.MySQL.StatsDatabase;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class StateManager implements Listener {
    public static int i = 1;

    @EventHandler
    public static void onJoin(PlayerJoinEvent e) {
        if (GameStates.GameState == GameStates.State.LOBBY) {
            if (Bukkit.getOnlinePlayers().size() >= 2) {
                Countdown.time = 60;
                Countdown.StartCountdown();
            }
        }
    }

    @EventHandler
    public static void onQuit(PlayerQuitEvent e) {
        if (GameStates.GameState == GameStates.State.LOBBY) {
            if (Countdown.count = true) {
                if (Bukkit.getOnlinePlayers().size() <= 2) {
                    Countdown.StopCountdown();
                    Countdown.time = 60;
                }
            }
        }
    }

    public static void setSchutzzeit() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        GameStates.setGameState(GameStates.State.SCHUTZZEIT);
        ArrayLists.IngameSize.add(Bukkit.getOnlinePlayers().size());
        for (Player all : Bukkit.getOnlinePlayers()) {
            ArrayLists.IngamePlayers.add(all);
            setScoreboard.setScoreBoard(all);
            int Gespielt = HashMaps.DatabaseGespielt.get(all) + 1;
            HashMaps.DatabaseGespielt.put(all, Gespielt);
            setSlots(Bukkit.getMaxPlayers() * 2);
            all.getInventory().clear();
            all.teleport((Location) BuildBw.getInstance().getConfig().get("Spawn." + i));
            i++;
        }
        Countdown.time = 10;
        Countdown.StartCountdown();
    }

    public static void setIngame() {
        GameStates.setGameState(GameStates.State.INGAME);
        for (Player all : Bukkit.getOnlinePlayers()) {
            setScoreboard.setScoreBoard(all);
        }
        Countdown.time = 0;
        Countdown.StartTimer();
    }

    public static void setEnd() {
        GameStates.setGameState(GameStates.State.END);

        for (Player all : Bukkit.getOnlinePlayers()) {
            setScoreboard.setScoreBoard(all);
            setSpectator.setNoSpec(all);

            if(all != ArrayLists.IngamePlayers.get(0).getPlayer()) {
                int Verloren = HashMaps.DatabaseVerloren.get(all) + 1;
                HashMaps.DatabaseVerloren.put(all, Verloren);
            } else {
                int Gewonnen = HashMaps.DatabaseGewonnen.get(ArrayLists.IngamePlayers.get(0).getPlayer()) + 1;
                HashMaps.DatabaseGewonnen.put(ArrayLists.IngamePlayers.get(0).getPlayer(), Gewonnen);
            }

            Bukkit.getScheduler().runTaskLater(BuildBw.getInstance(), new Runnable() {
                @Override
                public void run() {
                    StatsDatabase.HashmapToMySQL(all);
                }
            },40);
        }
        Countdown.time = 15;
        Countdown.StartCountdown();
    }

    public static void setSlots(int Slots) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        Object playerList = Bukkit.getServer().getClass().getDeclaredMethod("getHandle", new Class[0]).invoke(Bukkit.getServer(), new Object[0]);
        Field maxPlayersField = playerList.getClass().getSuperclass().getDeclaredField("maxPlayers");
        maxPlayersField.setAccessible(true);
        maxPlayersField.set(playerList, Slots);
    }
}
