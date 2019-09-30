package my.nvinz.core.vnizcore.game;

import my.nvinz.core.vnizcore.VnizCore;
import org.bukkit.Location;
import org.bukkit.Material;

public class Variables {

    VnizCore plugin;
    public int maxPlayers;
    public int minPlayers;
    public Location lobbySpawnPoint;
    public Variables(VnizCore pl){
        plugin = pl;
        try {
            maxPlayers = plugin.getConfig().getInt("max-players");
            minPlayers = plugin.getConfig().getInt("min-players");
            lobbySpawnPoint = plugin.setupLocation(plugin.getServer().getWorld(plugin.getConfig().getString("lobby.world")),
                    plugin.getConfig().getString("lobby.spawn"));
        } catch (Exception e) {
            plugin.getServer().getConsoleSender().sendMessage("Error parsing config file: " + e.getMessage());
        }
    }
}
