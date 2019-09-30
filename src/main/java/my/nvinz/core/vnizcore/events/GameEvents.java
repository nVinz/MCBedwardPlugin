package my.nvinz.core.vnizcore.events;

import my.nvinz.core.vnizcore.VnizCore;
import my.nvinz.core.vnizcore.game.Stage;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
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
    public void playerDeath(PlayerRespawnEvent event) {
        if (plugin.stageStatus.equals(Stage.Status.INGAME)) {
            event.setRespawnLocation(plugin.players_and_teams.get(event.getPlayer()).spawnPoint);
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
}
