package net.Spieleoase.Rufix.BuildBw.Commands;

import net.Spieleoase.Rufix.BuildBw.Data.HashMaps;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class stats implements CommandExecutor{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player p = (Player) sender;

        sender.sendMessage("§8§m----------------------------------------------");
        sender.sendMessage("§4Deine Stats");
        sender.sendMessage("§cSpiele§8: §e" + HashMaps.DatabaseGespielt.get(p));
        sender.sendMessage("§cVerloren§8: §e" + HashMaps.DatabaseVerloren.get(p));
        sender.sendMessage("§cSiege§8: §e" + HashMaps.DatabaseGewonnen.get(p));
        sender.sendMessage("§cKills§8: §e" + HashMaps.DatabaseKills.get(p));
        sender.sendMessage("§cTode§8: §e" + HashMaps.DatabaseTode.get(p));
        sender.sendMessage("§8§m----------------------------------------------");


        return false;
    }
}
