package my.nvinz.core.vnizcore.resources;

import org.bukkit.Location;
import org.bukkit.Material;

import java.util.List;

public class Resource {

    String name;
    Material material;
    Material block;
    int timer;
    List<Location> locations;

    public Resource(String name_, Material material_, Material block_, int timer_, List<Location> locations_) {
        name = name_;
        material = material_;
        block = block_;
        timer = timer_;
        locations = locations_;
    }

}
