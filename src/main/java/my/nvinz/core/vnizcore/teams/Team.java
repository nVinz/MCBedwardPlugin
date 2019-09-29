package my.nvinz.core.vnizcore.teams;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.StringTokenizer;

public class Team {

    public String teamColor;
    public String teamName;
    public ChatColor chatColor;
    public int spawnPositionX, spawnPositionY, spawnPositionZ;
    public List<Player> players = new ArrayList<Player>();

    public Team(String color, String teamName, ChatColor chatColor_, String spawnPoint){
        chatColor = chatColor_;
        teamColor = color;
    }

    public void tpAllToSpawn(){
        /*ListIterator<Player> players_ = players.listIterator();
        while (players_.hasNext()){
            players_.next().teleport(new Location(players_.next().getWorld(),spawnPositionX, spawnPositionY, spawnPositionZ));
        }*/
        players.forEach(player -> player.teleport(new Location(player.getWorld(),spawnPositionX, spawnPositionY, spawnPositionZ)));
    }

    public void tpToSpawn(Player player){
        player.teleport(new Location(player.getWorld(),spawnPositionX, spawnPositionY, spawnPositionZ));
    }

    public void addPlayer(Player player){
        players.add(player);
    }

    public void removePlayer(Player player){
        players.remove(player);
    }

    public void setSpawnPositions(String positions){
        String spawnPosition = positions.replace(',',' ');
        StringTokenizer st = new StringTokenizer(spawnPosition);
        while (st.hasMoreTokens()) {
            spawnPositionX = Integer.parseInt(st.nextToken());
            spawnPositionY = Integer.parseInt(st.nextToken());
            spawnPositionZ = Integer.parseInt(st.nextToken());
        }
    }
}
