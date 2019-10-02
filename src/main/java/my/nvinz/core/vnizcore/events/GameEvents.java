package my.nvinz.core.vnizcore.events;

import my.nvinz.core.vnizcore.VnizCore;
import my.nvinz.core.vnizcore.game.Stage;
import my.nvinz.core.vnizcore.teams.Team;
import org.bukkit.ChatColor;
import org.bukkit.EntityEffect;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class GameEvents implements Listener {

    private VnizCore plugin;
    public GameEvents(VnizCore pl){
        plugin = pl;
    }

    @EventHandler
    public void playerDamageByPlayer(EntityDamageByEntityEvent event){
        if (event.getEntity() instanceof Player) {
            if (event.getDamager() instanceof Player) {
                if (plugin.players.contains(((Player) event.getEntity()).getPlayer())) {
                    if (!plugin.stageStatus.equals(Stage.Status.INGAME)) {
                        event.setCancelled(true);
                    } else if (plugin.players_and_teams.get(event.getEntity()).equals(plugin.players_and_teams.get(event.getDamager()))) {
                        event.setCancelled(true);
                    } else if (((Player) event.getEntity()).getHealth() <= event.getDamage()) {
                        plugin.makeAnnouncement(
                                plugin.players_and_teams.get(((Player) event.getDamager()).getPlayer()).chatColor +
                                        ((Player) event.getDamager()).getPlayer().getName() +
                                        ChatColor.GRAY + " убил " +
                                        plugin.players_and_teams.get(((Player) event.getEntity()).getPlayer()).chatColor +
                                        ((Player) event.getEntity()).getPlayer().getName());
                        event.setCancelled(true);
                        plugin.killAndTp((Player) event.getEntity());
                    }
                }
            }
        }
    }

    @EventHandler
    public void playerDamage(EntityDamageEvent event){
        if (event.getEntity() instanceof Player){
            if (plugin.players.contains(((Player) event.getEntity()).getPlayer())) {
                if (!plugin.stageStatus.equals(Stage.Status.INGAME)) {
                    event.setCancelled(true);
                } else if (((Player) event.getEntity()).getHealth() <= event.getDamage()) {
                    plugin.makeAnnouncement(
                            plugin.players_and_teams.get(((Player) event.getEntity()).getPlayer()).chatColor +
                                    ((Player) event.getEntity()).getPlayer().getName() +
                                    ChatColor.GRAY + " умер.");
                    event.setCancelled(true);
                    plugin.killAndTp((Player) event.getEntity());
                }
            }
        }
    }

    @EventHandler
    public void playerRespawn(PlayerRespawnEvent event) {
        if (plugin.players.contains(event.getPlayer())) {
            if (plugin.stageStatus.equals(Stage.Status.INGAME)) {
                try {
                    if (plugin.players_and_teams.get(event.getPlayer()).bedStanding) {
                        event.setRespawnLocation(plugin.players_and_teams.get(event.getPlayer()).spawnPoint);
                    }
                } catch (Exception e) {
                    event.setRespawnLocation(plugin.variables.lobbySpawnPoint);
                }
            } else event.setRespawnLocation(plugin.variables.lobbySpawnPoint);
        }
    }

    @EventHandler
    public void inventoryClickEvent(InventoryClickEvent event) {
        if (plugin.players.contains(event.getWhoClicked())) {
            if (plugin.stageStatus.equals(Stage.Status.LOBBY)) {
                event.setCancelled(true);
            }
        }
    }
}
