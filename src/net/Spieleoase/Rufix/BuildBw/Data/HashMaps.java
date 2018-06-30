package net.Spieleoase.Rufix.BuildBw.Data;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import java.util.HashMap;

public class HashMaps {
    public static HashMap<Player, Integer> DatabaseKills = new HashMap<>();
    public static HashMap<Player, Integer> DatabaseTode = new HashMap<>();
    public static HashMap<Player, Integer> DatabaseVerloren = new HashMap<>();
    public static HashMap<Player, Integer> DatabaseGespielt = new HashMap<>();
    public static HashMap<Player, Integer> DatabaseGewonnen = new HashMap<>();
    public static HashMap<Scoreboard, Player> Scoreboards = new HashMap<>();
    public static HashMap<Material, Integer> ChestFillCheck = new HashMap<>();
}
