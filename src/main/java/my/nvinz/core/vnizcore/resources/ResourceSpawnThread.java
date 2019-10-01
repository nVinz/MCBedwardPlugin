package my.nvinz.core.vnizcore.resources;

import my.nvinz.core.vnizcore.VnizCore;
import my.nvinz.core.vnizcore.game.Stage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ResourceSpawnThread extends Thread {

    private String name;
    private Material material;
    private List<Location> locations;
    private int timer;
    private VnizCore plugin;
    public ResourceSpawnThread(VnizCore pl, Resource resource){
        plugin = pl;
        name = resource.material.toString();
        material = resource.material;
        locations = resource.locations;
        timer = resource.timer;
    }

    public void run(){
        try {
            while (plugin.stageStatus.equals(Stage.Status.INGAME)) {
                locations.forEach(location -> {
                    spawnItem(location);
                });
                Thread.sleep(1000 * timer);
            }
        } catch (InterruptedException e) {
            plugin.getServer().getConsoleSender().sendMessage("Error in item spawning thread: " + e);
        }
    }

    private void spawnItem(Location location){
        try {
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                @Override
                public void run() {
                    location.getWorld().dropItem(location, new ItemStack(material));
                }
            });
        } catch (Exception e) {}
    }
}
