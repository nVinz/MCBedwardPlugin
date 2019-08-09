package my.nvinz.core.vnizcore;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.StringTokenizer;

public final class VnizCore extends JavaPlugin {

    public ChatEvents chatEvents = new ChatEvents(this);
    public Stage stage = new Stage(this);

    public List<Team> teams = new ArrayList<Team>();
    public Team blueTeam;
    public Team redTeam;
    public Team yellowTeam;
    public Team greenTeam;


    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(chatEvents, this);

        this.getConfig().options().copyDefaults(true);
        this.saveConfig();

        if (this.getConfig().getInt("teams") *
                this.getConfig().getInt("team-players") !=
                this.getConfig().getInt("max-players")){
            getServer().getConsoleSender().sendMessage("ERROR! Teams and players are not matching!");
        }

        switch (this.getConfig().getInt("teams")){
            case 2:
                blueTeam = new Team("Синие");
                redTeam = new Team("Красные");
                teams.add(blueTeam);
                teams.add(redTeam);
                break;
            case 4:
                blueTeam = new Team("Синие");
                redTeam = new Team("Красные");
                yellowTeam = new Team("Желтые");
                greenTeam = new Team("Зеленые");
                teams.add(blueTeam);
                teams.add(redTeam);
                teams.add(yellowTeam);
                teams.add(greenTeam);
                break;
        }

        List<String> spawnPositions = this.getConfig().getStringList("team-spawns");
        ListIterator<String> spawnPositionsIt = spawnPositions.listIterator();
        ListIterator<Team> teamsIt = teams.listIterator();
        while (spawnPositionsIt.hasNext()) {
            teamsIt.next().setSpawnPositions(spawnPositionsIt.next());
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
