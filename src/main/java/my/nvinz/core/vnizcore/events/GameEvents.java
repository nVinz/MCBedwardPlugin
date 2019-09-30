package my.nvinz.core.vnizcore.events;

import my.nvinz.core.vnizcore.VnizCore;
import my.nvinz.core.vnizcore.game.Stage;
import org.bukkit.ChatColor;
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
    public void playerHitByPlayer(EntityDamageByEntityEvent event){
        if (plugin.stageStatus.equals(Stage.Status.LOBBY)){
            event.setCancelled(true);
        }
        else if (event.getEntity().getType().equals(EntityType.PLAYER) &&
            event.getDamager().getType().equals(EntityType.PLAYER)){
            if (plugin.players_and_teams.get(event.getEntity()).equals(plugin.players_and_teams.get(event.getDamager()))){
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void playerDamage(EntityDamageEvent event){
        if (event.getEntity() instanceof Player){
            if (plugin.stageStatus.equals(Stage.Status.LOBBY)){
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void playerRespawn(PlayerRespawnEvent event) {
        if (plugin.stageStatus.equals(Stage.Status.INGAME)) {
            try {
                if (plugin.players_and_teams.get(event.getPlayer()).bedStanding) {
                    event.setRespawnLocation(plugin.players_and_teams.get(event.getPlayer()).spawnPoint);
                }
            } catch (Exception e) {
                event.setRespawnLocation(plugin.variables.lobbySpawnPoint);
            }
        }
        else event.setRespawnLocation(plugin.variables.lobbySpawnPoint);
    }

    @EventHandler
    public void playerDeath(PlayerDeathEvent event){
        event.setDeathMessage(
                plugin.players_and_teams.get(event.getEntity().getKiller()).chatColor+event.getEntity().getKiller().getName() +
                ChatColor.GRAY + " убил " +
                plugin.players_and_teams.get(event.getEntity()).chatColor+event.getEntity().getName());

        try {
            if (!plugin.players_and_teams.get(event.getEntity()).bedStanding) {
                event.getEntity().sendMessage(ChatColor.RED + "Вы выбыли из игры.");
                plugin.makeAnnouncement(plugin.players_and_teams.get(event.getEntity()).chatColor + event.getEntity().getName() + ChatColor.GRAY + " выбыл из игры.");
            }
            plugin.isTeamLost(event.getEntity());
        } finally {
            plugin.removePlayerFromTeam(event.getEntity());
        }
    }

    @EventHandler
    public void InventoryClickEvent(InventoryClickEvent event) {
        if (plugin.stageStatus.equals(Stage.Status.LOBBY)) {
            event.setCancelled(true);
        }
    }
}
