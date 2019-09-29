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
        if (plugin.stage.equals(Stage.Status.LOBBY)){
            event.setCancelled(true);
        }
        else{
            Material block = event.getBlock().getType();
            if (block.equals(Material.GRASS_BLOCK)){
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event){
        if (plugin.stage.equals(Stage.Status.LOBBY)){
            event.setCancelled(true);
        }
    }
}
