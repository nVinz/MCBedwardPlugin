package my.nvinz.core.vnizcore.teams;

import my.nvinz.core.vnizcore.VnizCore;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

public class TeamBuilder {

    String teamColor;
    String teamName;
    ChatColor chatColor;
    Location spawnPoint;
    int maxPlayers;
    Material bedMaterial;

    private VnizCore plugin;
    public TeamBuilder(VnizCore pl){ plugin = pl; }

    public void buildTeam(){
        try {
            Team team = new Team(teamColor, teamName, chatColor, spawnPoint, bedMaterial, maxPlayers);
            plugin.teams.add(team);
            plugin.teams_beds.put(team, bedMaterial);
        } catch (Exception e) {
            plugin.getServer().getConsoleSender().sendMessage("Error building team: " + e);
        }
    }

    public TeamBuilder setTeamColor(String color){
        teamColor = color;
        plugin.getServer().getConsoleSender().sendMessage(" Color: " + teamColor);
        return this;
    }

    public TeamBuilder setTeamName(String name){
        teamName = name;
        plugin.getServer().getConsoleSender().sendMessage(" Name: " + teamName);
        return this;
    }

    public TeamBuilder setChatColor(String color){
        try {
            chatColor = ChatColor.valueOf(color.toUpperCase());
            plugin.getServer().getConsoleSender().sendMessage(" Chat color: " + chatColor + color);
        } catch (NullPointerException e){
            plugin.getServer().getConsoleSender().sendMessage("ERROR! Parsing team color: " + color);
        }
        return this;
    }

    public TeamBuilder setSpawnPoint(String point, World world){
        try {
            spawnPoint = plugin.setupLocation(world, point);
            plugin.getServer().getConsoleSender().sendMessage(" Spawn point: " + spawnPoint);
        } catch (Exception e) {
            plugin.getServer().getConsoleSender().sendMessage("ERROR! Parsing spawnpoint: " + spawnPoint);
        }
        return this;
    }

    public TeamBuilder setMaxPlayers(int players){
        try {
            maxPlayers = players;
            plugin.getServer().getConsoleSender().sendMessage(" Max players: " + maxPlayers);
        } catch (Exception e) {
            plugin.getServer().getConsoleSender().sendMessage("ERROR! Parsing max players: " + spawnPoint);
        }
        return this;
    }

    public TeamBuilder setBedMaterial(String color){
        bedMaterial = Material.valueOf(color.toUpperCase() + "_BED");
        plugin.getServer().getConsoleSender().sendMessage(" Bed material: " + bedMaterial);
        return this;
    }
}
