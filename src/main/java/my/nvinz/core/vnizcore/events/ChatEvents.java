package my.nvinz.core.vnizcore.events;

import my.nvinz.core.vnizcore.VnizCore;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ChatEvents implements Listener {

    private VnizCore plugin;
    String maxPlayers;

    public ChatEvents(VnizCore pl){
        plugin = pl;
         maxPlayers = Integer.toString(plugin.getConfig().getInt("max-players"));
    }

    @EventHandler
    public void chatCheck(AsyncPlayerChatEvent event){
        String message = event.getMessage();
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
        Player player = event.getPlayer();
        String currPlayers = Integer.toString(plugin.getServer().getOnlinePlayers().size());

        if (Integer.parseInt(currPlayers) < Integer.parseInt(maxPlayers))
            event.setJoinMessage(ChatColor.DARK_GRAY+"[" + ChatColor.RED+currPlayers + ChatColor.GRAY+"/" + ChatColor.RED+maxPlayers + ChatColor.DARK_GRAY+"] " +
                    ChatColor.WHITE+player.getName() + ChatColor.GRAY+" присоединился к игре.");
        else {
            event.setJoinMessage(ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + currPlayers + ChatColor.GRAY + "/" + ChatColor.GREEN + maxPlayers + ChatColor.DARK_GRAY + "] " +
                    ChatColor.WHITE + player.getName() + ChatColor.GRAY + " присоединился к игре.");
        }

    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        Player player = event.getPlayer();
        String currPlayers = Integer.toString(plugin.getServer().getOnlinePlayers().size() - 1);

        event.setQuitMessage(ChatColor.DARK_GRAY+"[" + ChatColor.RED+currPlayers + ChatColor.GRAY+"/" + ChatColor.RED+maxPlayers + ChatColor.DARK_GRAY+"] " +
                ChatColor.WHITE+player.getName() + ChatColor.GRAY+" покинул игру.");
    }
}
