package my.nvinz.core.vnizcore;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;
import java.util.ListIterator;

public class ChatEvents implements Listener {

    private VnizCore plugin;
    public ChatEvents(VnizCore pl){
        plugin = pl;
    }

    @EventHandler
    public void chatCheck(AsyncPlayerChatEvent event){
        String message = event.getMessage();
        Player player = event.getPlayer();

        event.setFormat(ChatColor.GRAY+player.getName() + ChatColor.DARK_GRAY+": " + ChatColor.GRAY+event.getMessage());

        if (message.contains("b")){
            plugin.blueTeam.addPlayer(player);
            player.sendMessage(ChatColor.GRAY+"Вы присоединились к команде " + ChatColor.BLUE+"синих");
        }
        else if (message.contains("r")){
            plugin.redTeam.addPlayer(player);
            player.sendMessage(ChatColor.GRAY+"Вы присоединились к команде " + ChatColor.RED+"красных");
        }

        ListIterator<Player> blueIt = plugin.blueTeam.players.listIterator();
        ListIterator<Player> redIt = plugin.redTeam.players.listIterator();
        if (blueIt.next().equals(player)){
            event.setFormat(ChatColor.BLUE+player.getName() + ChatColor.DARK_GRAY+": " + ChatColor.GRAY+event.getMessage());
        }

        else if (redIt.next().equals(player)){
            event.setFormat(ChatColor.RED+player.getName() + ChatColor.DARK_GRAY+": " + ChatColor.GRAY+event.getMessage());
        }

        /*boolean enabled = plugin.getConfig().getBoolean("banned-words-enabled");
        int incedents = plugin.getConfig().getInt("incidents." + player.getUniqueId().toString());
        List<String> words = plugin.getConfig().getStringList("banned-words");
        if(enabled){
            for (String banned: words){
                if (message.contains(banned)){
                    event.setCancelled(true);
                    player.sendMessage(ChatColor.RED + "Осуждаем.");

                    if (incedents != 0){
                        incedents++;
                        plugin.getConfig().set("incidents." + player.getName(), incedents);
                        plugin.saveConfig();
                    }
                    else{
                        plugin.getConfig().set("incidents." + player.getName(), 1);
                        plugin.saveConfig();
                    }
                }
            }
        }*/
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        String currPlayers = Integer.toString(plugin.getServer().getOnlinePlayers().size());
        String maxPlayers = Integer.toString(plugin.getConfig().getInt("max-players"));

        if (Integer.parseInt(currPlayers) < Integer.parseInt(maxPlayers))
            event.setJoinMessage(ChatColor.DARK_GRAY+"[" + ChatColor.RED+currPlayers + ChatColor.GRAY+"/" + ChatColor.RED+maxPlayers + ChatColor.DARK_GRAY+"] " +
                    ChatColor.WHITE+player.getName() + ChatColor.GRAY+" присоединился к игре.");
        else {
            event.setJoinMessage(ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + currPlayers + ChatColor.GRAY + "/" + ChatColor.GREEN + maxPlayers + ChatColor.DARK_GRAY + "] " +
                    ChatColor.WHITE + player.getName() + ChatColor.GRAY + " присоединился к игре.");
            plugin.stage.startCountdown();
        }

    }
    /*    String joinMessage = "";
        Player player = event.getPlayer();
        if (player.isOp()){
            joinMessage = ChatColor. GREEN+"> " + ChatColor.WHITE+player.getDisplayName() + ChatColor.GREEN+" зашел на сервер.";
        }
        event.setJoinMessage(joinMessage);

        if (!player.hasPlayedBefore()){
            for (Player players: plugin.getServer().getOnlinePlayers()){
                players.sendMessage(ChatColor. GREEN+"> " + ChatColor.WHITE+player.getDisplayName() + ChatColor.GREEN+" первый раз зашел на сервер!");
            }
        }
    }*/

    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        Player player = event.getPlayer();
        String currPlayers = Integer.toString(plugin.getServer().getOnlinePlayers().size() - 1);
        String maxPlayers = Integer.toString(plugin.getConfig().getInt("max-players"));

        event.setQuitMessage(ChatColor.DARK_GRAY+"[" + ChatColor.RED+currPlayers + ChatColor.GRAY+"/" + ChatColor.RED+maxPlayers + ChatColor.DARK_GRAY+"] " +
                ChatColor.WHITE+player.getName() + ChatColor.GRAY+" покинул игру.");
    }
}
