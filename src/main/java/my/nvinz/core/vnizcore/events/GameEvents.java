package my.nvinz.core.vnizcore.events;

import my.nvinz.core.vnizcore.VnizCore;
import my.nvinz.core.vnizcore.game.Stage;
import net.minecraft.server.v1_13_R2.*;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_13_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
                                        Objects.requireNonNull(
                                                ((Player) event.getDamager()).getPlayer()).getName() +
                                        ChatColor.GRAY + " убил " +
                                        plugin.players_and_teams.get(((Player) event.getEntity()).getPlayer()).chatColor +
                                        Objects.requireNonNull(
                                                ((Player) event.getEntity()).getPlayer()).getName());
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
                                    Objects.requireNonNull(
                                            ((Player) event.getEntity()).getPlayer())
                                    .getName() +ChatColor.GRAY + " умер.");
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

   /* @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {

        final Player player = event.getPlayer();
        final int visibleDistance = plugin.getServer().getViewDistance() * 16;

        // Fix the visibility issue one tick later
        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
                // Refresh nearby clients
                updateEntities(getPlayersWithin(player, visibleDistance));

                System.out.println("Applying fix ... " + visibleDistance);
            }
        }, 15);
    }


    public void updateEntities(List<Player> observers) {

        // Refresh every single player
        for (Player player : observers) {
            updateEntity(player, observers);
        }
    }

   /* @SuppressWarnings("unchecked")
    public void updateEntity(Entity entity, List<Player> observers) {

        World world = entity.getWorld();
        WorldServer worldServer = ((CraftWorld) world).getHandle();

        EntityTracker tracker = worldServer.tracker;
        EntityTrackerEntry entry = tracker.trackedEntities
                .get(entity.getEntityId());

        List<EntityPlayer> nmsPlayers = getNmsPlayers(observers);

        // Force Minecraft to resend packets to the affected clients
        entry.trackedPlayers.removeAll(nmsPlayers);
        //entry.scanPlayers(nmsPlayers);
    }

    private List<EntityPlayer> getNmsPlayers(List<Player> players) {
        List<EntityPlayer> nsmPlayers = new ArrayList<EntityPlayer>();

        for (Player bukkitPlayer : players) {
            CraftPlayer craftPlayer = (CraftPlayer) bukkitPlayer;
            nsmPlayers.add(craftPlayer.getHandle());
        }

        return nsmPlayers;
    }

    private List<Player> getPlayersWithin(Player player, int distance) {
        List<Player> res = new ArrayList<Player>();
        int d2 = distance * distance;

        for (Player p : plugin.getServer().getOnlinePlayers()) {
            if (p.getWorld() == player.getWorld()
                    && p.getLocation().distanceSquared(player.getLocation()) <= d2) {
                res.add(p);
            }
        }

        return res;
    }*/


    // TODO Disable craft
}
