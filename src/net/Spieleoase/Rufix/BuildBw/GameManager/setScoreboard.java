package net.Spieleoase.Rufix.BuildBw.GameManager;

import net.Spieleoase.Rufix.BuildBw.BuildBw;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import static net.Spieleoase.Rufix.BuildBw.BuildBw.getInstance;
import static net.Spieleoase.Rufix.BuildBw.Data.ArrayLists.IngamePlayers;
import static net.Spieleoase.Rufix.BuildBw.Data.ArrayLists.IngameSize;
import static net.Spieleoase.Rufix.BuildBw.Data.HashMaps.Scoreboards;
import static net.Spieleoase.Rufix.BuildBw.GameManager.Countdown.time;
import static net.Spieleoase.Rufix.BuildBw.GameManager.GameStates.State.*;
import static net.Spieleoase.Rufix.BuildBw.GameManager.GameStates.getGameState;
import static org.bukkit.Bukkit.*;

public class setScoreboard {

    public static void setScoreBoard(Player p) {

        Countdown.time = 60;

        Scoreboard board = getScoreboardManager().getNewScoreboard();
        Objective obj = board.registerNewObjective("aaa", "bbb");

        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.setDisplayName("§8» §e§lBuildBW");


        Team Spieler = board.registerNewTeam("Spieler");
        Spieler.setPrefix(" §8» ");
        Spieler.setSuffix("§b" + getOnlinePlayers().size() + "§8/§b" + getMaxPlayers());
        Spieler.addEntry(ChatColor.GREEN.toString());

        Team SpielerIngame = board.registerNewTeam("SpielerIngame");
        SpielerIngame.setPrefix(" §8» ");
        SpielerIngame.setSuffix("§b" + IngamePlayers.size() + "§8/§b" + IngameSize.toString().replace("[", "").replace("]", ""));
        SpielerIngame.addEntry(ChatColor.DARK_PURPLE.toString());

        Team Countdown = board.registerNewTeam("Countdown");
        Countdown.setPrefix(" §8» ");
        Countdown.setSuffix("§b" + time + " §7Sek");
        Countdown.addEntry(ChatColor.RED.toString());

        Team Timer = board.registerNewTeam("Timer");
        Timer.setPrefix(" §8» ");
        Timer.setSuffix("§b" + time + " §7Min");
        Timer.addEntry(ChatColor.BLUE.toString());

        if (getGameState() == LOBBY) {
            obj.getScore(" ").setScore(16);
            obj.getScore("§aSpieler").setScore(15);
            obj.getScore(ChatColor.GREEN.toString()).setScore(14);
            obj.getScore("  ").setScore(13);
            obj.getScore("§aMap").setScore(12);
            obj.getScore(" §8» §b" + getInstance().getConfig().getString("Mapname")).setScore(11);
            obj.getScore("   ").setScore(10);
            obj.getScore("§aWartezeit").setScore(9);
            obj.getScore(ChatColor.RED.toString()).setScore(8);
            obj.getScore("    ").setScore(7);
        } else if (getGameState() == SCHUTZZEIT) {
            obj.getScore(" ").setScore(16);
            obj.getScore("§aSpieler").setScore(15);
            obj.getScore(ChatColor.DARK_PURPLE.toString()).setScore(14);
            obj.getScore("  ").setScore(13);
            obj.getScore("§aSchutzzeit").setScore(12);
            obj.getScore(ChatColor.RED.toString()).setScore(11);
            obj.getScore("   ").setScore(10);
        } else if (getGameState() == INGAME) {
            obj.getScore(" ").setScore(16);
            obj.getScore("§aSpieler").setScore(15);
            obj.getScore(ChatColor.DARK_PURPLE.toString()).setScore(14);
            obj.getScore("  ").setScore(13);
            obj.getScore("§aSpielzeit").setScore(12);
            obj.getScore(ChatColor.BLUE.toString()).setScore(11);
            obj.getScore("   ").setScore(10);
        } else if (getGameState() == END) {
            obj.getScore(" ").setScore(16);
            obj.getScore("§aGewinner").setScore(15);
            obj.getScore(" §8» §b" + IngamePlayers.get(0).getDisplayName()).setScore(14);
            obj.getScore("  ").setScore(13);
        }
        Scoreboards.put(board, p);

        p.setScoreboard(board);
    }

    public static void updateBoard() {
         Bukkit.getScheduler().runTaskTimerAsynchronously(BuildBw.getInstance(), new Runnable() {
            @Override
            public void run() {
                for (Scoreboard board : Scoreboards.keySet()) {
                    if (getGameState() == LOBBY) {
                        board.getTeam("Spieler").setSuffix("§b" + getOnlinePlayers().size() + "§8/§b" + getMaxPlayers());
                        board.getTeam("Countdown").setSuffix("§b" + time + " §7Sek");
                    } else if (getGameState() == SCHUTZZEIT) {
                        board.getTeam("SpielerIngame").setSuffix("§b" + IngamePlayers.size() + "§8/§b" + IngameSize.toString().replace("[", "").replace("]", ""));
                        board.getTeam("Countdown").setSuffix("§b" + time + " §7Sek");
                    } else if (getGameState() == INGAME) {
                        board.getTeam("Timer").setSuffix("§b" + time + " §7Min");
                        board.getTeam("SpielerIngame").setSuffix("§b" + IngamePlayers.size() + "§8/§b" + IngameSize.toString().replace("[", "").replace("]", ""));
                    }
                }
            }
        }, 0,20);
    }
}
