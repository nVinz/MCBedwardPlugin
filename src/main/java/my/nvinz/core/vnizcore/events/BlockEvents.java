package my.nvinz.core.vnizcore.events;

import my.nvinz.core.vnizcore.VnizCore;
import my.nvinz.core.vnizcore.game.Stage;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockEvents implements Listener {

    private VnizCore plugin;
    public BlockEvents(VnizCore pl){
        plugin = pl;
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event){
        if (plugin.stageStatus.equals(Stage.Status.LOBBY)){
            event.setCancelled(true);
        }
        else if (plugin.stageStatus.equals(Stage.Status.INGAME)){
            if (event.getBlock().equals(Material.GRASS_BLOCK)){
                event.setCancelled(true);
            }
            if (event.getBlock().getBlockData() instanceof org.bukkit.block.data.type.Bed) {
                event.getPlayer().sendMessage(String.valueOf(event.getBlock()));
                plugin.teams_beds.forEach( (team, bed) -> {
                    if (event.getBlock().equals(bed)){
                        plugin.makeAnnouncement("Кровать команды " + team.chatColor+team.teamName + " разрушена.");
                    }
                });
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event){
        if (plugin.stageStatus.equals(Stage.Status.LOBBY)){
            event.setCancelled(true);
        }
    }
}
