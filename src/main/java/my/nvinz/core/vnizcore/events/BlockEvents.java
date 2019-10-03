package my.nvinz.core.vnizcore.events;

import my.nvinz.core.vnizcore.VnizCore;
import my.nvinz.core.vnizcore.game.Stage;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockEvents implements Listener {

    private VnizCore plugin;
    public BlockEvents(VnizCore pl){
        plugin = pl;
    }

    // TODO only placed blocks
    @EventHandler
    public void onBreak(BlockBreakEvent event){
        if (plugin.players.contains(event.getPlayer())) {
            if (plugin.stageStatus.equals(Stage.Status.INGAME)) {
                if (!plugin.variables.allowedMaterials.contains(event.getBlock().getType())) {
                    event.setCancelled(true);
                }
                if (event.getBlock().getBlockData() instanceof org.bukkit.block.data.type.Bed) {
                    plugin.teams.forEach(team -> {
                        if (event.getBlock().getType().equals(team.bedMaterial)) {
                            if (team.bedStanding) {
                                plugin.playSound(Sound.ENTITY_ENDER_DRAGON_GROWL);
                                plugin.makeAnnouncement(ChatColor.GRAY + "Кровать команды " + team.chatColor + team.teamName + ChatColor.GRAY + " разрушена.");
                                plugin.makeTeamAnnouncement(team, ChatColor.RED + "Внимание! Кровать вашей команды была уничтожена, теперь у вас осталасть только одна жизнь!");
                                team.bedStanding = false;
                            }
                        }
                    });
                }
            } else {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event){
        if (plugin.players.contains(event.getPlayer())) {
            if (plugin.stageStatus.equals(Stage.Status.INGAME)) {
                if (!plugin.variables.allowedMaterials.contains(event.getBlock().getType())) {
                    event.setCancelled(true);
                }
            } else {
                event.setCancelled(true);
            }
        }
    }
}
