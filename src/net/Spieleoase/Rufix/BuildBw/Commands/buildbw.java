package net.Spieleoase.Rufix.BuildBw.Commands;

import net.Spieleoase.Rufix.BuildBw.BuildBw;
import net.Spieleoase.Rufix.BuildBw.Data.TextStrings;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class buildbw implements CommandExecutor{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if(BuildBw.getInstance().getConfig().getString("SetupMode") == "true"){
                if (args.length == 1 && args[0].equalsIgnoreCase("setlobby")) {
                    BuildBw.getInstance().getConfig().set("locations.lobby", p.getLocation());
                    BuildBw.getInstance().saveConfig();
                    p.sendMessage(TextStrings.Prefix + "Du hast den §cLobbySpawn §7gesetzt§8!");
                } else if (args.length == 1 && args[0].equalsIgnoreCase("setspec")) {
                    BuildBw.getInstance().getConfig().set("locations.spectator", p.getLocation());
                    BuildBw.getInstance().saveConfig();
                    p.sendMessage(TextStrings.Prefix + "Du hast den §cSpectatorSpawn §7gesetzt§8!");
                } else if (args.length == 2 && args[0].equalsIgnoreCase("setspawn") && args[1].equalsIgnoreCase(args[1])) {
                    try {
                        int i = Integer.parseInt(args[1]);
                        BuildBw.getInstance().getConfig().set("Spawn." + i, p.getLocation());
                        BuildBw.getInstance().saveConfig();
                        p.sendMessage(TextStrings.Prefix + "Du hast den §cSpawn " + i + " §7gesetzt§8!");
                    } catch (NumberFormatException e) {
                        p.sendMessage(TextStrings.Prefix + "§cDas muss eine Zahl sein§8!");
                    }
                } else if(args.length == 2 && args[0].equalsIgnoreCase("setmapname") && args[1].equalsIgnoreCase(args[1])) {
                    BuildBw.getInstance().getConfig().set("Mapname", args[1]);
                    BuildBw.getInstance().saveConfig();
                    p.sendMessage(TextStrings.Prefix + "§7Du hast die Map §c" + args[1] + " §7genannt§8.");
                } else if(args.length == 2 && args[0].equalsIgnoreCase("tpmap") && args[1].equalsIgnoreCase(args[1])) {
                    p.teleport(Bukkit.getWorld(String.valueOf(BuildBw.getInstance().getConfig().get("Mapname"))).getSpawnLocation());
                    p.sendMessage(TextStrings.Prefix + "§7Du wurdest zur Map §e" + args[1] + " §7teleportiert§8!");
                } else {
                    p.sendMessage(TextStrings.Prefix + "§7Benutze§8: §c/buildbe setlobby");
                    p.sendMessage(TextStrings.Prefix + "§7Benutze§8: §c/buildbe setspec");
                    p.sendMessage(TextStrings.Prefix + "§7Benutze§8: §c/buildbe setmapname STRING");
                    p.sendMessage(TextStrings.Prefix + "§7Benutze§8: §c/buildbe setspawn INT");
                }
            } else {
                p.sendMessage(TextStrings.Prefix + "Du musst den §cSetupMode §7auf §atrue §7stellen§8!");
            }
        }
        return true;
    }
}
