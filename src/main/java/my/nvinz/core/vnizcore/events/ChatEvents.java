package my.nvinz.core.vnizcore.events;

import my.nvinz.core.vnizcore.VnizCore;
import my.nvinz.core.vnizcore.game.Stage;
import org.bukkit.ChatColor;
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
        if (plugin.players.contains(event.getPlayer())) {
            event.getRecipients().clear();
            event.getRecipients().addAll(plugin.players);
            if (plugin.players_and_teams.containsKey(event.getPlayer())) {
                event.setFormat(plugin.players_and_teams.get(event.getPlayer()).chatColor + event.getPlayer().getName() +
                        ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + event.getMessage());
            } else {
                event.setFormat(ChatColor.GRAY + event.getPlayer().getName() + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + event.getMessage());
            }
        }
        else{
            event.getRecipients().removeAll(plugin.players);
        }
    }

    // TODO add sound effect
    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        if (plugin.stageStatus.equals(Stage.Status.LOBBY)) {
            // TODO on command
            PlayerInventory inventory = event.getPlayer().getInventory();
            inventory.clear();
            if (plugin.getConfig().getBoolean("items.join-item.give-on-server-join")){
                inventory.setItem(4, plugin.items.items.get("join-item"));
            }
        }
        if (plugin.inventories.containsKey(event.getPlayer().getName())) {
            plugin.restorePlayerInventory(event.getPlayer());
            //plugin.inventories.remove(event.getPlayer());
            event.getPlayer().getInventory().addItem(
                    (plugin.inventories.get(event.getPlayer().getName()).getContents()));
        }
        plugin.tab.tabUpdateAllPlayers();
    }

    // TODO add sound effect
    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        if (plugin.players.contains(event.getPlayer())){
            plugin.inventories.put(event.getPlayer().getName(), event.getPlayer().getInventory());
            plugin.leavePlayer(event.getPlayer());
        }
        plugin.tab.tabUpdateAllPlayers();
    }
}
