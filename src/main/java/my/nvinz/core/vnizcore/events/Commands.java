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

        switch (command.getName()) {
            case "none":
                try {
                    player.sendMessage(ChatColor.GRAY+"Вы покинули команду " + plugin.players_and_teams.get(player).chatColor+plugin.players_and_teams.get(player).teamColor);
                    plugin.removePlayerFromTeam(player);
                } catch (NullPointerException e){
                    player.sendMessage(ChatColor.GRAY+"Вы не в команде.");
                }
                break;
            case "jointeam":
                if (args.length == 1){
                    plugin.removePlayerFromTeam(player);

                    plugin.teams.forEach(team -> {
                        if (team.teamColor.equalsIgnoreCase(args[0])){
                            if (team.hasFree()) {
                                try {
                                    plugin.addPlayerToTeam(player, team);
                                    return;
                                } catch (NullPointerException e) {
                                    player.sendMessage(ChatColor.RED + "Неизвестная команда.");
                                }
                            }
                            else {
                                player.sendMessage(ChatColor.RED + "В команде нет мест.");
                            }
                        }
                    });
                }
                break;
            case "teams":
                plugin.teams.forEach(team -> {
                    player.sendMessage(team.chatColor + team.teamName + ": " + team.players.toString());
                    if (team.players.isEmpty()){
                        plugin.makeAnnouncement(ChatColor.GRAY + "Команда " +
                                team.chatColor +
                                team.teamName +
                                (ChatColor.LIGHT_PURPLE + " проиграла."));
                    }
                });
                break;
            case "start":
                plugin.stage.startCountdown();
                break;
            case "test":
                plugin.stageStatus = Stage.Status.AFTERGAME;
                PlayerInventory inventory = player.getInventory();
                inventory.clear();
                inventory.setItem(4, plugin.items.items.get("select-team-item"));
                break;
        }

        return true;
    }

}
