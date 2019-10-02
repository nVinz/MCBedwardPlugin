package my.nvinz.core.vnizcore.resources;

import my.nvinz.core.vnizcore.VnizCore;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.List;

public class ResourceBuilder {

    private String name;
    private Material material;
    private Material block;
    private int timer;
    private List<Location> locations;

    private VnizCore plugin;
    public ResourceBuilder(VnizCore pl){ plugin = pl; }

    public void buildResource(){
        try {
            Resource resource = new Resource(name, material, block, timer, locations);
            plugin.resources.add(resource);
        } catch (Exception e) {
            plugin.getServer().getConsoleSender().sendMessage("Error building team: " + e);
        }
    }

    public ResourceBuilder setName(String name_){
        name = name_;
        plugin.getServer().getConsoleSender().sendMessage(" Name: " + name);
        return this;
    }

    public ResourceBuilder setMaterial(Material material_){
        material = material_;
        plugin.getServer().getConsoleSender().sendMessage(" Material: " + material);
        return this;
    }

    public ResourceBuilder setBlock(Material block_){
        block = block_;
        plugin.getServer().getConsoleSender().sendMessage(" Block: " + block);
        return this;
    }

    public ResourceBuilder setTimer(int timer_){
        timer = timer_;
        plugin.getServer().getConsoleSender().sendMessage(" Timer: " + timer);
        return this;
    }

    public ResourceBuilder setLocations(List<Location> locations_){
        locations = locations_;
        plugin.getServer().getConsoleSender().sendMessage(" Locations: " + locations);
        return this;
    }
}
