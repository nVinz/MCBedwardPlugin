package my.nvinz.core.vnizcore.teams;

import my.nvinz.core.vnizcore.VnizCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Team {

    public String teamColor;
    public String teamName;
    public ChatColor chatColor;
    public Location spawnPoint;
    public List<Player> players = new ArrayList<>();
    private int maxPlayers;
    public boolean bedStanding;
    public Material bedMaterial;
    private VnizCore plugin;
    Team(VnizCore pl, String teamColor_, String teamName_, ChatColor chatColor_, Location spawnPoint_, Material bedMaterial_, int maxPlayers_){
        plugin = pl;
        teamColor = teamColor_;
        teamName = teamName_;
        chatColor = chatColor_;
        spawnPoint = spawnPoint_;
        bedMaterial = bedMaterial_;
        maxPlayers = maxPlayers_;
        bedStanding = true;
    }

    public void tpAllToSpawn(){
        try {
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> players.forEach(player -> player.teleport(spawnPoint)));
        } catch (Exception ignored) {}
    }

    public void addPlayer(Player player){
        players.add(player);
    }

    public void removePlayer(Player player){
        players.remove(player);
    }

    public boolean hasFree(){
        return players.size() < maxPlayers;
    }
}
