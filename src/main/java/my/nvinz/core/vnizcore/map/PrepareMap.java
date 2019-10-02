package my.nvinz.core.vnizcore.map;

import my.nvinz.core.vnizcore.VnizCore;
import org.bukkit.World;
import org.bukkit.entity.Item;

public class PrepareMap {

    private VnizCore plugin;
    public PrepareMap(VnizCore pl) { plugin = pl; }

    public void clearDrops(World world){
        plugin.getServer().getConsoleSender().sendMessage("Clearing drops in world: " + world.getName());
        world.getEntities().forEach(entity -> {
            if (entity instanceof Item){
                entity.remove();
            }
        });
    }
}
