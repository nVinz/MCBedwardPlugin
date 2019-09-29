package my.nvinz.core.vnizcore.teams;

import my.nvinz.core.vnizcore.VnizCore;
import org.bukkit.ChatColor;

public class TeamBuilder {

    String teamColor;
    String teamName;
    ChatColor chatColor;
    String spawnPoint;

    private VnizCore plugin;
    public TeamBuilder(VnizCore pl){
        plugin = pl;
    }

    public void buildTeam(){
        try {
            Team team = new Team(teamColor, teamName, chatColor, spawnPoint );
            plugin.teams.add(team);
        } catch (Exception e) {
            plugin.getServer().getConsoleSender().sendMessage("Error building team: " + e);
        }
    }

    public TeamBuilder setTeamColor(String color){
        plugin.getServer().getConsoleSender().sendMessage(" Color: " + color);
        teamColor = color;
        return this;
    }

    public TeamBuilder setTeamName(String name){
        plugin.getServer().getConsoleSender().sendMessage(" Name: " + teamName);
        teamName = name;
        return this;
    }


    public TeamBuilder setChatColor(String color){
        try {
            chatColor = ChatColor.valueOf(color.toUpperCase());
            plugin.getServer().getConsoleSender().sendMessage(" Chat color: " + color);
        } catch (NullPointerException e){
            plugin.getServer().getConsoleSender().sendMessage("ERROR! Unknown team color: " + color);
        }
        return this;
    }

    public TeamBuilder setSpawnpoint(String point){
        plugin.getServer().getConsoleSender().sendMessage(" Spawn point: " + point);
        spawnPoint = point;
        return this;
    }
}
