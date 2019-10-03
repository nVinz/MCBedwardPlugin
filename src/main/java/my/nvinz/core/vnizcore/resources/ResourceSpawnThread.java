package my.nvinz.core.vnizcore.resources;

import my.nvinz.core.vnizcore.VnizCore;
import my.nvinz.core.vnizcore.game.Stage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Objects;

public class ResourceSpawnThread extends Thread {

    private Material material;
    private List<Location> locations;
    private int timer;
    private VnizCore plugin;
    public ResourceSpawnThread(VnizCore pl, Resource resource){
        plugin = pl;
        material = resource.material;
        locations = resource.locations;
        timer = resource.timer;
    }

    public void run(){
        try {
            while (plugin.stageStatus.equals(Stage.Status.INGAME)) {
                locations.forEach(this::spawnItem);
                Thread.sleep(1000 * timer);
            }
        } catch (InterruptedException e) {
            plugin.getServer().getConsoleSender().sendMessage("Error in item spawning thread: " + e);
        }
    }

    private void spawnItem(Location location){
        try {
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> Objects.requireNonNull(
                    location.getWorld()).dropItem(location, new ItemStack(material)));
        } catch (Exception ignored) {}
    }
}
