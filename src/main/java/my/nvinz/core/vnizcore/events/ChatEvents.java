package my.nvinz.core.vnizcore.events;

import my.nvinz.core.vnizcore.VnizCore;
import my.nvinz.core.vnizcore.game.Stage;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.PlayerInventory;

public class ChatEvents implements Listener {

    private VnizCore plugin;
    public ChatEvents(VnizCore pl){ plugin = pl; }

    @EventHandler
    public void chatCheck(AsyncPlayerChatEvent event){
        if (plugin.players_and_teams.containsKey(event.getPlayer())) {
            event.setFormat(plugin.players_and_teams.get(event.getPlayer()).chatColor + event.getPlayer().getName() +
                    ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + event.getMessage());
        }
        else{
            event.setFormat(ChatColor.GRAY+event.getPlayer().getName() + ChatColor.DARK_GRAY+": " + ChatColor.GRAY+event.getMessage());
        }
    }

    // TODO add sound effect
    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        if (plugin.stageStatus.equals(Stage.Status.LOBBY)) {

            PlayerInventory inventory = event.getPlayer().getInventory();
            inventory.clear();
            inventory.setItem(4, plugin.items.items.get("join-item"));  // TODO not requires and on command

            plugin.players.add(event.getPlayer());      // TODO Make non-full server game
            event.getPlayer().teleport(plugin.variables.lobbySpawnPoint);
            int currPlayers = plugin.getServer().getOnlinePlayers().size();
            if (currPlayers < plugin.variables.maxPlayers)
                event.setJoinMessage(ChatColor.DARK_GRAY + "[" + ChatColor.RED + currPlayers + ChatColor.GRAY + "/" + ChatColor.RED + plugin.variables.maxPlayers + ChatColor.DARK_GRAY + "] " +
                        ChatColor.WHITE + event.getPlayer().getName() + ChatColor.GRAY + " присоединился к игре.");
            else {
                event.setJoinMessage(ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + currPlayers + ChatColor.GRAY + "/" + ChatColor.GREEN + plugin.variables.maxPlayers + ChatColor.DARK_GRAY + "] " +
                        ChatColor.WHITE + event.getPlayer().getName() + ChatColor.GRAY + " присоединился к игре.");
                plugin.stage.startCountdown();
                // TODO realize min-players
            }
        }
        else {
            event.setJoinMessage("");
        }
    }

    // TODO add sound effect
    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        if (plugin.stageStatus.equals(Stage.Status.LOBBY) || plugin.stageStatus.equals(Stage.Status.COUNTDOWN)) {
            String currPlayers = Integer.toString(plugin.getServer().getOnlinePlayers().size() - 1);
            event.setQuitMessage(ChatColor.DARK_GRAY + "[" + ChatColor.RED + currPlayers + ChatColor.GRAY + "/" + ChatColor.RED + plugin.variables.maxPlayers + ChatColor.DARK_GRAY + "] " +
                    ChatColor.WHITE + event.getPlayer().getName() + ChatColor.GRAY + " покинул игру.");
            plugin.stageStatus = Stage.Status.LOBBY;
        }
        else if (plugin.stageStatus.equals(Stage.Status.INGAME)){
            event.setQuitMessage(plugin.players_and_teams.get(event.getPlayer()).chatColor+event.getPlayer().getName() + ChatColor.GRAY+" покинул игру.");
            plugin.removePlayerFromTeam(event.getPlayer());
            plugin.isTeamLost();
        }
        else {
            event.setQuitMessage("");
        }
    }
}
