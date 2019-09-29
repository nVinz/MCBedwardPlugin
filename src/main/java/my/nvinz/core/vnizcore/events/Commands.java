package my.nvinz.core.vnizcore.events;

import my.nvinz.core.vnizcore.VnizCore;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

public class Commands implements CommandExecutor {

    private VnizCore plugin;
    public Commands(VnizCore pl){
        plugin = pl;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)) {
            sender.sendMessage("Эта командя только дял игроков!");
            return false;
        }

        Player player = (Player) sender;

        switch (command.getName()) {
            case "none":
                try {
                    player.sendMessage(ChatColor.GRAY+"Вы покинули команду " + plugin.players_and_teams.get(player).chatColor+plugin.players_and_teams.get(player).teamColor);
                    leaveTeam(player);
                } catch (NullPointerException e){
                    player.sendMessage(ChatColor.GRAY+"Вы не в команде.");
                }
                break;
            case "jointeam":
                if (args.length == 1){
                    leaveTeam(player);

                    plugin.teams.forEach(team -> {
                        if (team.teamColor.equalsIgnoreCase(args[0])){
                            team.addPlayer(player);
                            plugin.players_and_teams.put(player, team);
                            return;
                        }
                    });

                    player.sendMessage(ChatColor.GRAY+"Вы присоединились к команде " + plugin.players_and_teams.get(player).chatColor+plugin.players_and_teams.get(player).teamName);
                }
                break;
            case "teams":
                plugin.teams.forEach(team -> player.sendMessage(team.teamColor + ": " + team.players.toString()));
                break;
            case "start":
                plugin.stage.startCountdown();
                break;
            case "test":
                Map<String, Object> str = plugin.getConfig().getConfigurationSection("teams2").getValues(false);
                str.forEach( (team, obj) ->  player.sendMessage(team));
                player.sendMessage(ChatColor.valueOf("RED2") + "1");
                break;
        }

        return true;
    }

    private void leaveTeam(Player player){
        try {
            plugin.players_and_teams.get(player).removePlayer(player);
            plugin.players_and_teams.remove(player);
        } catch (NullPointerException e) {}
    }
}
