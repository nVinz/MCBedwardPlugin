package my.nvinz.core.vnizcore.game;

import my.nvinz.core.vnizcore.VnizCore;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Variables {

    VnizCore plugin;
    public int maxPlayers;
    public int minPlayers;
    public Location lobbySpawnPoint;
    public Location lobbyExitPoint;
    public World arenaWorld;
    public List<Material> allowedMaterials = new ArrayList<>();
    public Variables(VnizCore pl){
        plugin = pl;
        try {
            maxPlayers = plugin.getConfig().getInt("max-players");
            minPlayers = plugin.getConfig().getInt("min-players");
            lobbySpawnPoint = plugin.setupLocation(plugin.getServer().getWorld(
                    Objects.requireNonNull(plugin.getConfig().getString("lobby.world"))),
                    plugin.getConfig().getString("lobby.spawn"));
            lobbyExitPoint = plugin.setupLocation(plugin.getServer().getWorld(
                    Objects.requireNonNull(plugin.getConfig().getString("exit.world"))),
                    plugin.getConfig().getString("exit.spawn"));
            arenaWorld = plugin.getServer().getWorld(Objects.requireNonNull(
                    plugin.getConfig().getString("arena.world")));
            List<String> allowedMaterialsList = plugin.getConfig().getStringList("allowed-blocks");
            allowedMaterialsList.forEach(material -> {
                allowedMaterials.add(Material.getMaterial(material));
            });
        } catch (Exception e) {
            plugin.getServer().getConsoleSender().sendMessage("Error parsing config file: " + e.getMessage());
        }
    }
}
