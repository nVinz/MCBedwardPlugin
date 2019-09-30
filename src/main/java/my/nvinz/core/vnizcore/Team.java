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
    public double spawnPositionX, spawnPositionY, spawnPositionZ;
    public List<Player> players;
    public int playersMax, playersCurr;

    public Team(String color, int playersMax_) {
        teamColor = color;
        playersMax = playersMax_;
        playersCurr = 0;

        players = new ArrayList<>();
    }

    public void tpAllToSpawn(){
        players.forEach((n) -> n.sendMessage("Teleporting..."));
        players.forEach((n) -> n.teleport(new Location(n.getWorld(),spawnPositionX, spawnPositionY, spawnPositionZ)));
    }

    public void tpToSpawn(Player player){
        player.teleport(new Location(player.getWorld(),spawnPositionX, spawnPositionY, spawnPositionZ));
    }

    public void clearAllInventory(){
        players.forEach((p) -> p.getInventory().clear());
    }

    public void clearInventory(Player player){
        player.getInventory().clear();
    }

    public boolean isFull(){
        if (playersCurr < playersMax) { return false; }
        else { return true; }
    }

    public void addPlayer(Player player){
        players.add(player);
        playersCurr++;
    }

    public void removePlayer(Player player){
        players.remove(player);
        playersCurr--;
    }

    public void setSpawnPositions(String positions){
        String spawnPosition = positions.replace(',',' ');
        StringTokenizer st = new StringTokenizer(spawnPosition);
        while (st.hasMoreTokens()) {
            spawnPositionX = Double.valueOf(st.nextToken());
            spawnPositionY = Double.valueOf(st.nextToken());
            spawnPositionZ = Double.valueOf(st.nextToken()); 
        }
    }
}
