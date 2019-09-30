package my.nvinz.core.vnizcore.events;

import my.nvinz.core.vnizcore.VnizCore;
import my.nvinz.core.vnizcore.game.Stage;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ChatEvents implements Listener {

    private VnizCore plugin;
    int maxPlayers;
    int startOnPlayers;
    Location lobbySpawnPoint;
    public ChatEvents(VnizCore pl){
        plugin = pl;
        maxPlayers = plugin.getConfig().getInt("max-players");
        startOnPlayers = plugin.getConfig().getInt("start-on-players");
        lobbySpawnPoint = plugin.setupLocation(plugin.getServer().getWorld(plugin.getConfig().getString("lobby.world")),
                plugin.getConfig().getString("lobby.spawn"));
    }

    @EventHandler
    public void chatCheck(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        if (plugin.players_and_teams.containsKey(player)) {
            event.setFormat(plugin.players_and_teams.get(player).chatColor + player.getName() + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + event.getMessage());
        }
        else{
            event.setFormat(ChatColor.GRAY+player.getName() + ChatColor.DARK_GRAY+": " + ChatColor.GRAY+event.getMessage());
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        if (plugin.stageStatus.equals(Stage.Status.LOBBY)) {
            event.getPlayer().teleport(lobbySpawnPoint);
            int currPlayers = plugin.getServer().getOnlinePlayers().size();
            if (currPlayers < maxPlayers)
                event.setJoinMessage(ChatColor.DARK_GRAY + "[" + ChatColor.RED + currPlayers + ChatColor.GRAY + "/" + ChatColor.RED + maxPlayers + ChatColor.DARK_GRAY + "] " +
                        ChatColor.WHITE + event.getPlayer().getName() + ChatColor.GRAY + " присоединился к игре.");
            else {
                event.setJoinMessage(ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + currPlayers + ChatColor.GRAY + "/" + ChatColor.GREEN + maxPlayers + ChatColor.DARK_GRAY + "] " +
                        ChatColor.WHITE + event.getPlayer().getName() + ChatColor.GRAY + " присоединился к игре.");
                plugin.stage.startCountdown();
            }
        }
        else {
            event.setJoinMessage("");
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        if (plugin.stageStatus.equals(Stage.Status.LOBBY) || plugin.stageStatus.equals(Stage.Status.COUNTDOWN)) {
            String currPlayers = Integer.toString(plugin.getServer().getOnlinePlayers().size() - 1);
            event.setQuitMessage(ChatColor.DARK_GRAY + "[" + ChatColor.RED + currPlayers + ChatColor.GRAY + "/" + ChatColor.RED + maxPlayers + ChatColor.DARK_GRAY + "] " +
                    ChatColor.WHITE + event.getPlayer().getName() + ChatColor.GRAY + " покинул игру.");
            plugin.stageStatus = Stage.Status.LOBBY;
        }
        else {
            event.setQuitMessage("");
        }
    }
}
