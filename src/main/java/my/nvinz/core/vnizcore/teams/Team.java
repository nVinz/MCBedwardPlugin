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
    public int currPlayers;
    public boolean bedStanding;
    public Material bedMaterial;

    public Team(String teamColor_, String teamName_, ChatColor chatColor_, Location spawnPoint_, Material bedMaterial_, int maxPlayers_){
        teamColor = teamColor_;
        teamName = teamName_;
        chatColor = chatColor_;
        spawnPoint = spawnPoint_;
        bedMaterial = bedMaterial_;
        maxPlayers = maxPlayers_;
        currPlayers = 0;
        bedStanding = true;
    }

    public void tpAllToSpawn(){
        players.forEach(player -> player.teleport(spawnPoint));
    }

    public void addPlayer(Player player){
        players.add(player);
        currPlayers += 1;
    }

    public void removePlayer(Player player){
        players.remove(player);
        currPlayers -= 1;
    }

    public boolean hasFree(){
        if (currPlayers < maxPlayers) return true;
        else return false;
    }

    /*
     *  flag to check was function call before or after death
     *  0 - before
     *  1 - after
     */
    public boolean isAlive(int flag){
        if (currPlayers - flag == 0) return false;
        else return true;
    }

    /*
     *  Never used
     */
    public void tpToSpawn(Player player){
        player.teleport(spawnPoint);
    }
}
