package my.nvinz.core.vnizcore.game;

import my.nvinz.core.vnizcore.VnizCore;
import my.nvinz.core.vnizcore.teams.Team;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ListIterator;

public class Stage{

    VnizCore plugin;
    public enum Status {
        LOBBY,
        COUNTDOWN,
        INGAME,
        AFTERGAME
    }
    public Stage(VnizCore pl){
        plugin = pl;
    }

    public void startCountdown(){
        plugin.stageStatus = Status.COUNTDOWN;
        int countdownSeconds = plugin.getConfig().getInt("countdown");
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = countdownSeconds; i >= 0; i--) {
                    try {
                        if (plugin.stageStatus == Status.COUNTDOWN) {
                            plugin.makeAnnouncement(ChatColor.GRAY + "Игра начнется через " + ChatColor.GREEN + i);
                        }
                        else {
                            plugin.makeAnnouncement(ChatColor.RED + "Недостаточно игроков для начала игры.");
                            return;
                        }
                        Thread.sleep(1000);
                    } catch (InterruptedException e) { }
                }
                for (Player players: plugin.getServer().getOnlinePlayers()){
                    players.sendMessage(ChatColor.GREEN+"Игра начинается!");
                }

                plugin.getServer().getOnlinePlayers().forEach(player -> {
                    if (plugin.players_and_teams.get(player) == null){
                        plugin.teams.forEach(team -> {
                            if (team.hasFree() && !plugin.players_and_teams.containsKey(player)){
                                plugin.addPlayerToTeam(player, team);
                            }
                        });
                    }
                });

                inGame();
            }
        });
        thread.start();
    }

    public void inGame(){
        plugin.stageStatus = Status.INGAME;
        /*plugin.teams.forEach(team -> {
            team.tpAllToSpawn();
            //n.clearAllInventory();
        });*/
        plugin.players_and_teams.forEach( (player, team) -> {
            player.getInventory().clear();
            team.tpAllToSpawn();
        });
    }

    public void inAftergame(){
        plugin.stageStatus = Status.AFTERGAME;
        plugin.players.forEach( player -> {
            player.teleport(plugin.variables.lobbySpawnPoint);      // Make exit to world
        });
    }
}
