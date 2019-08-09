package my.nvinz.core.vnizcore;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.StringTokenizer;

public class Team {

    public String teamColor;
    public int spawnPositionX, spawnPositionY, spawnPositionZ;
    public List<Player> players = new ArrayList<Player>();

    public Team(String color){
        teamColor = color;
    }

    public void tpAllToSpawn(){
        ListIterator<Player> players_ = players.listIterator();
        while (players_.hasNext()){
            players_.next().teleport(new Location(players_.next().getWorld(),spawnPositionX, spawnPositionY, spawnPositionZ));
        }
    }

    public void tpToSpawn(Player player){
        player.teleport(new Location(player.getWorld(),spawnPositionX, spawnPositionY, spawnPositionZ));
    }

    public void addPlayer(Player player){
        players.add(player);
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
