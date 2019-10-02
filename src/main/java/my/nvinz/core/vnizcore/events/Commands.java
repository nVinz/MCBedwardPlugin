package my.nvinz.core.vnizcore.events;

import my.nvinz.core.vnizcore.VnizCore;
import my.nvinz.core.vnizcore.game.Stage;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import java.util.Map;

public class Commands implements CommandExecutor {

    private VnizCore plugin;
    public Commands(VnizCore pl){
        plugin = pl;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)) {
            sender.sendMessage("Эта командя только для игроков!");
            return false;
        }

        Player player = (Player) sender;

        if (command.getName().equalsIgnoreCase("Vniz")){
            if (args.length == 0){
                player.sendMessage(ChatColor.GRAY+"Vniz");
                return false;
            }
            switch (args[0]) {
                case "none":
                    try {
                        player.sendMessage(ChatColor.GRAY+"Вы покинули команду " + plugin.players_and_teams.get(player).chatColor+plugin.players_and_teams.get(player).teamColor);
                        plugin.removePlayerFromTeam(player);
                    } catch (NullPointerException e){
                        player.sendMessage(ChatColor.GRAY+"Вы не в команде.");
                    }
                    break;
                case "team":
                    if (args.length == 2) {
                        if (plugin.players.contains(player)) {
                            plugin.teams.forEach(team -> {
                                if (team.teamColor.equalsIgnoreCase(args[1])) {
                                    if (team.hasFree()) {
                                        try {
                                            plugin.removePlayerFromTeam(player);
                                            plugin.addPlayerToTeam(player, team);
                                            return;
                                        } catch (NullPointerException e) {
                                            player.sendMessage(ChatColor.RED + "Неизвестная команда.");
                                        }
                                    } else {
                                        player.sendMessage(ChatColor.RED + "В команде нет мест.");
                                    }
                                }
                            });
                        }
                        else {
                            player.sendMessage(ChatColor.RED + "Вы не находитесь в лобби.");
                        }
                    }
                    break;
                case "teams":
                    plugin.teams.forEach(team -> {
                        player.sendMessage(team.chatColor + team.teamName + ": " + team.players.toString());
                    });
                    break;
                case "start":
                    plugin.stage.startCountdown();
                    break;
                case "join":
                    plugin.joinPlayer(player);
                    break;
                case "leave":
                    plugin.leavePlayer(player);
                    break;
                case "test":
                    plugin.stageStatus = Stage.Status.AFTERGAME;
                    PlayerInventory inventory = player.getInventory();
                    inventory.clear();
                    inventory.setItem(4, plugin.items.items.get("select-team-item"));
                    break;
            }
        }



        return true;
    }

}
