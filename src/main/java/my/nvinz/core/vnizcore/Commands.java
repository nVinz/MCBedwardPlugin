package my.nvinz.core.vnizcore;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ListIterator;

public class Commands implements CommandExecutor {

    VnizCore plugin;
    Commands(VnizCore pl){
        plugin = pl;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("Эта командя только для игроков!");
            return false;
        }
        Player player = (Player) sender;

        if (command.getName().equalsIgnoreCase("start")){
            /*ListIterator<Team> teamsIt = plugin.teams.listIterator();
            while (teamsIt.hasNext()){
                teamsIt.next().tpAllToSpawn();
            }*/
            plugin.stage.inGame();
        }
        else if (command.getName().equalsIgnoreCase("cmd")){
            plugin.teams.forEach((team) -> {
                player.sendMessage(team.teamColor + " - " + team.players.toString());
            });
        }

        return true;
    }
}
