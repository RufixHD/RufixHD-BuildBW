package net.Spieleoase.Rufix.BuildBw;

import net.Spieleoase.Rufix.BuildBw.Commands.buildbw;
import net.Spieleoase.Rufix.BuildBw.Commands.start;
import net.Spieleoase.Rufix.BuildBw.Commands.stats;
import net.Spieleoase.Rufix.BuildBw.GameManager.Countdown;
import net.Spieleoase.Rufix.BuildBw.GameManager.GameStates;
import net.Spieleoase.Rufix.BuildBw.GameManager.StateManager;
import net.Spieleoase.Rufix.BuildBw.GameManager.setScoreboard;
import net.Spieleoase.Rufix.BuildBw.ItemManager.ChestFill;
import net.Spieleoase.Rufix.BuildBw.ItemManager.ClickHotbar;
import net.Spieleoase.Rufix.BuildBw.Listeners.*;
import net.Spieleoase.Rufix.BuildBw.MySQL.StatsDatabase;
import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import static net.Spieleoase.Rufix.BuildBw.GameManager.GameStates.State.LOBBY;

public class BuildBw extends JavaPlugin{

    private static BuildBw instance;

    @Override
    public void onEnable() {
        loadMap();

        instance = this;

        Countdown.count = false;

        registerCommands();
        registerListener();

        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        StatsDatabase.connect();
        StatsDatabase.createTable();

        GameStates.setGameState(LOBBY);

        setScoreboard.updateBoard();
    }

    @Override
    public void onDisable() {

    }

    private void registerCommands(){
        this.getServer().getPluginCommand("buildbw").setExecutor(new buildbw());
        this.getServer().getPluginCommand("stats").setExecutor(new stats());
        this.getServer().getPluginCommand("start").setExecutor(new start());
    }

    private void registerListener(){
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new JoinListener(), this);
        pm.registerEvents(new QuitListener(), this);
        pm.registerEvents(new ClickHotbar(), this);
        pm.registerEvents(new StateManager(), this);
        pm.registerEvents(new DeathListener(), this);
        pm.registerEvents(new DamageListener(), this);
        pm.registerEvents(new BuildListener(), this);
        pm.registerEvents(new ChestFill(), this);
        pm.registerEvents(new WeatherListener(), this);
        pm.registerEvents(new FoodListener(), this);
    }

    public static void loadMap() {
        String map ="world2";
        if(map != null) {
            Bukkit.createWorld(new WorldCreator(map).generateStructures(false));
            Bukkit.getWorlds().add(Bukkit.getWorld(map));
        }
    }

    public static BuildBw getInstance() {
        return instance;
    }
}
