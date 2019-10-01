package my.nvinz.core.vnizcore.teams;

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
    public int maxPlayers;
    public boolean bedStanding;
    public Material bedMaterial;

    public Team(String teamColor_, String teamName_, ChatColor chatColor_, Location spawnPoint_, Material bedMaterial_, int maxPlayers_){
        teamColor = teamColor_;
        teamName = teamName_;
        chatColor = chatColor_;
        spawnPoint = spawnPoint_;
        bedMaterial = bedMaterial_;
        maxPlayers = maxPlayers_;
        bedStanding = true;
    }

    public void tpAllToSpawn(){
        players.forEach(player -> player.teleport(spawnPoint));
    }

    public void addPlayer(Player player){
        players.add(player);
    }

    public void removePlayer(Player player){
        players.remove(player);
    }

    public boolean hasFree(){
        if (players.size() < maxPlayers) return true;
        else return false;
    }

    /*
     *  Never used
     */
    public void tpToSpawn(Player player){
        player.teleport(spawnPoint);
    }
}
