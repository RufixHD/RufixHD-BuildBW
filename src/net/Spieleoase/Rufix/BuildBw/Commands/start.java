package net.Spieleoase.Rufix.BuildBw.Commands;

import net.Spieleoase.Rufix.BuildBw.Data.TextStrings;
import net.Spieleoase.Rufix.BuildBw.GameManager.Countdown;
import net.Spieleoase.Rufix.BuildBw.GameManager.GameStates;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class start implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player p = (Player) sender;
        if (p.hasPermission("buildbw.start")) {
            if (GameStates.getGameState() == GameStates.State.LOBBY) {
                    if (args.length == 1) {
                        int i = Integer.parseInt(args[0]);
                        Countdown.time = i;
                        p.sendMessage(TextStrings.Prefix + "Der Countdown wurde auf §c" + i + " §7Sekunden gesetzt§8!");
                    } else {
                        Countdown.time = 10;
                        p.sendMessage(TextStrings.Prefix + "Der Countdown wurde auf §c10 §7Sekunden gesetzt§8!");
                }
            } else {
                p.sendMessage(TextStrings.Prefix + "Diesen Befehl kannst du jetzt §cnicht §7ausführen§8!");
            }

        }
        return false;
    }
}
