package net.Spieleoase.Rufix.BuildBw.Listeners;

import net.Spieleoase.Rufix.BuildBw.BuildBw;
import net.Spieleoase.Rufix.BuildBw.GameManager.GameStates;
import net.Spieleoase.Rufix.BuildBw.GameManager.setScoreboard;
import net.Spieleoase.Rufix.BuildBw.GameManager.setSpectator;
import net.Spieleoase.Rufix.BuildBw.ItemManager.ItemCreator;
import net.Spieleoase.Rufix.BuildBw.MySQL.StatsDatabase;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static net.Spieleoase.Rufix.BuildBw.Data.TextStrings.JoinMessage;
import static net.Spieleoase.Rufix.BuildBw.Data.TextStrings.Prefix;

public class JoinListener implements Listener{

    @EventHandler
    private void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();

        e.setJoinMessage(Prefix + p.getDisplayName() + JoinMessage);

        p.setMaxHealth(20);
        p.setHealth(20);
        p.setFoodLevel(20);
        p.setLevel(0);
        p.setExp(0);
        p.getInventory().clear();
        p.getInventory().setArmorContents(null);

        if(GameStates.GameState == GameStates.State.LOBBY || GameStates.GameState == GameStates.State.END){
            p.setGameMode(GameMode.SURVIVAL);
            ItemCreator.setInv(p, 8, Material.BARRIER, "§8» §cVerlassen");
            p.teleport((Location) BuildBw.getInstance().getConfig().get("locations.lobby"));
        } else if(GameStates.GameState == GameStates.State.INGAME || GameStates.GameState == GameStates.State.SCHUTZZEIT) {
            ItemCreator.setInv(p, 8, Material.BARRIER, "§8» §cVerlassen");
            setScoreboard.setScoreBoard(p);
            setSpectator.setSpec(p);
        }

        StatsDatabase.MySQLToHashmap(p);

        setScoreboard.setScoreBoard(p);
    }
}
