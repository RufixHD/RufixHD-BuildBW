package net.Spieleoase.Rufix.BuildBw.MySQL;

import net.Spieleoase.Rufix.BuildBw.BuildBw;
import net.Spieleoase.Rufix.BuildBw.Data.HashMaps;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.*;

public class StatsDatabase {
    public static Connection con;

    private static String DatabaseHost = BuildBw.getInstance().getConfig().getString("DatabaseHost");
    private static String DatabaseName = BuildBw.getInstance().getConfig().getString("DatabaseName");
    private static String DatabaseUser = BuildBw.getInstance().getConfig().getString("DatabaseUser");
    private static String DatabasePassword = BuildBw.getInstance().getConfig().getString("DatabasePassword");
    private static int DatabasePort = BuildBw.getInstance().getConfig().getInt("DatabasePort");

    public static boolean isConnect() {
        return (con != null);
    }

    public static void connect() {
        Bukkit.getScheduler().runTaskAsynchronously(BuildBw.getInstance(), new Runnable() {
            @Override
            public void run() {
                if(!isConnect()) {
                    try {
                        con = DriverManager.getConnection("jdbc:mysql://" + DatabaseHost + ":" + DatabasePort+ "/" + DatabaseName + "?autoReconnect=true", DatabaseUser , DatabasePassword);
                        System.out.println("[MySQL] Verbinndung erfolgreich!");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public static void createTable() {
        Bukkit.getScheduler().runTaskLaterAsynchronously(BuildBw.getInstance(), new Runnable() {
            @Override
            public void run() {
                try {
                    con.prepareStatement("CREATE TABLE IF NOT EXISTS TODE (UUID VARCHAR(100), TODE INT(16))").executeUpdate();
                    con.prepareStatement("CREATE TABLE IF NOT EXISTS KILLS (UUID VARCHAR(100), KILLS INT(16))").executeUpdate();
                    con.prepareStatement("CREATE TABLE IF NOT EXISTS GESPIELT (UUID VARCHAR(100), GESPIELT INT(16))").executeUpdate();
                    con.prepareStatement("CREATE TABLE IF NOT EXISTS GEWONNEN (UUID VARCHAR(100), GEWONNEN INT(16))").executeUpdate();
                    con.prepareStatement("CREATE TABLE IF NOT EXISTS VERLOREN (UUID VARCHAR(100), VERLOREN INT(16))").executeUpdate();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        },40);
    }

    public static int getStats(Player p, String Tabelle) {
        try {
            PreparedStatement ps = con.prepareStatement("SELECT " + Tabelle + " FROM " + Tabelle + " WHERE UUID = ?");
            ps.setString(1, p.getUniqueId().toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return (rs.getInt(Tabelle));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static void setStats(Player p, int Anzahl, String Tabelle) {
        Bukkit.getScheduler().runTaskAsynchronously(BuildBw.getInstance(), new Runnable() {
            @Override
            public void run() {
                if (getStats(p, Tabelle) == -1) {
                    try {
                        PreparedStatement ps = con.prepareStatement("INSERT INTO " + Tabelle + " (UUID," + Tabelle + ") VALUES (?,?)");
                        ps.setString(1, p.getUniqueId().toString());
                        ps.setInt(2, Anzahl);
                        ps.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        PreparedStatement ps = con.prepareStatement("UPDATE " + Tabelle + " SET " + Tabelle + " = ? WHERE UUID = ?");
                        ps.setString(2, p.getUniqueId().toString());
                        ps.setInt(1, Anzahl);
                        ps.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public static void HashmapToMySQL(Player p) {
        if (HashMaps.DatabaseGespielt.containsKey(p)) {
            setStats(p, HashMaps.DatabaseGespielt.get(p), "GESPIELT");
        }
        if (HashMaps.DatabaseVerloren.containsKey(p)) {
            setStats(p, HashMaps.DatabaseVerloren.get(p), "VERLOREN");
        }
        if (HashMaps.DatabaseGewonnen.containsKey(p)) {
            setStats(p, HashMaps.DatabaseGewonnen.get(p), "GEWONNEN");
        }
        if (HashMaps.DatabaseKills.containsKey(p)) {
            setStats(p, HashMaps.DatabaseKills.get(p), "KILLS");
        }
        if (HashMaps.DatabaseTode.containsKey(p)) {
            setStats(p, HashMaps.DatabaseTode.get(p), "TODE");
        }
    }

    public static void MySQLToHashmap(Player p){
        if(getStats(p, "TODE" ) != -1){
            HashMaps.DatabaseTode.put(p, getStats(p, "TODE" ));
        } else {
            setStats(p, 0,"TODE");
            HashMaps.DatabaseTode.put(p, 0);
        }
        if(getStats(p, "KILLS" ) != -1){
            HashMaps.DatabaseKills.put(p, getStats(p, "KILLS" ));
        } else {
            setStats(p, 0,"KILLS");
            HashMaps.DatabaseKills.put(p, 0);
        }
        if(getStats(p, "VERLOREN" ) != -1){
            HashMaps.DatabaseVerloren.put(p, getStats(p, "VERLOREN" ));
        } else {
            setStats(p, 0,"VERLOREN");
            HashMaps.DatabaseVerloren.put(p, 0);
        }
        if(getStats(p, "GEWONNEN" ) != -1){
            HashMaps.DatabaseGewonnen.put(p, getStats(p, "GEWONNEN" ));
        } else {
            setStats(p, 0,"GEWONNEN");
            HashMaps.DatabaseGewonnen.put(p, 0);
        }
        if(getStats(p, "GESPIELT" ) != -1){
            HashMaps.DatabaseGespielt.put(p, getStats(p, "GESPIELT" ));
        } else {
            setStats(p, 0,"GESPIELT");
            HashMaps.DatabaseGespielt.put(p, 0);
        }
    }
}

