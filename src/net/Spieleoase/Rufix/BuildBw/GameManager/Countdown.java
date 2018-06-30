package net.Spieleoase.Rufix.BuildBw.GameManager;

import net.Spieleoase.Rufix.BuildBw.BuildBw;
import net.Spieleoase.Rufix.BuildBw.Data.TextStrings;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public class Countdown {
    public static int time;
    public static int task;
    public static int task2;
    public static boolean count = false;

    public static void StartCountdown() {
        if (count == false) {
            count = true;
            task = Bukkit.getScheduler().scheduleSyncRepeatingTask(BuildBw.getInstance(), new Runnable() {
                @Override
                public void run() {
                    if (time-- <= 0) {
                        time--;
                        Bukkit.getScheduler().cancelTask(task);
                        count = false;
                        if (GameStates.getGameState() == GameStates.State.LOBBY) {
                            try {
                                StateManager.setSchutzzeit();
                            } catch (NoSuchMethodException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (NoSuchFieldException e) {
                                e.printStackTrace();
                            }
                        } else if (GameStates.getGameState() == GameStates.State.SCHUTZZEIT) {
                            StateManager.setIngame();
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                all.setLevel(0);
                            }
                        } else if (GameStates.getGameState() == GameStates.State.END) {
                            Bukkit.getServer().shutdown();
                        }
                    }
                    if (GameStates.getGameState() == GameStates.State.LOBBY) {
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            if (time == 10 || time == 5 || time == 4 || time == 3 || time == 2 || time == 1 ) all.sendMessage(TextStrings.Prefix + "Das Spiel startet in §c" + time + " §7Sekunden§8!");
                            if (time == 0 ) all.sendMessage(TextStrings.Prefix + "Das Spiel startet §cjetzt§8!");
                            all.setLevel(time);
                            all.setExp(0);
                        }
                    } else if (GameStates.getGameState() == GameStates.State.SCHUTZZEIT) {
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            if (time == 10 || time == 5 || time == 4 || time == 3 || time == 2 || time == 1 ) all.sendMessage(TextStrings.Prefix + "Die Schutzzeit ist in §c" + time + " §7Sekunden vorbei§8!");
                            if (time == 0 ) all.sendMessage(TextStrings.Prefix + "Die Schutzzeit ist §cjetzt §7vorbei§8!");
                        }
                    } else if (GameStates.getGameState() == GameStates.State.END) {
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            if (time == 10 || time == 5 || time == 4 || time == 3 || time == 2 || time == 1 ) all.sendMessage(TextStrings.Prefix + "Der Server restartet in §c" + time + " §7Sekunden§8!");
                            if (time == 0 ) all.sendMessage(TextStrings.Prefix + "Der Server restartet §cjetzt§8!");
                        }
                    }
                }

            }, 0, 20);
        }
    }

    public static void StartTimer() {
        if (!Bukkit.getScheduler().isCurrentlyRunning(task2)) {
            task2 = Bukkit.getScheduler().scheduleSyncRepeatingTask(BuildBw.getInstance(), new Runnable() {

                @Override
                public void run() {
                    time++;
                }
            }, 0, 1200);
        }
    }

    public static void StopCountdown() {
        if (count == true) {
            Bukkit.getScheduler().cancelTask(task);
            count = false;
        }
    }

    public static void StopTimer() {
        if (count == true) {
            Bukkit.getScheduler().cancelTask(task2);
            count = false;
        }
    }
}
