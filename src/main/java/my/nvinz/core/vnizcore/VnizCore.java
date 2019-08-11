package my.nvinz.core.vnizcore;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public final class VnizCore extends JavaPlugin {

    public Events events;
    public Stage stage;

    public List<Team> teams;
    public Team blueTeam;
    public Team redTeam;
    public Team yellowTeam;
    public Team greenTeam;

    public int playersMaxCfg;
    public int teamsCfg;

    @Override
    public void onEnable() {
        events = new Events(this);
        stage = new Stage(this);
        teams = new ArrayList<>();
        playersMaxCfg = this.getConfig().getInt("max-players");
        teamsCfg = this.getConfig().getInt("teams");

        this.getServer().getPluginManager().registerEvents(events, this);
        this.getCommand("start").setExecutor(new Commands(this));
        this.getCommand("cmd").setExecutor(new Commands(this));

        this.getConfig().options().copyDefaults(true);
        this.saveConfig();

        if (teamsCfg *
                this.getConfig().getInt("team-players") !=
                this.getConfig().getInt("max-players")){
            getServer().getConsoleSender().sendMessage("ERROR! Teams and players are not matching!");
        }

        switch (teamsCfg){
            case 2:
                create2teams();
                break;
            case 4:
                create4teams();
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
        events = null;
        stage = null;
        teams.clear();
        blueTeam = null;
        redTeam = null;
        yellowTeam = null;
        greenTeam = null;
    }

    public void create2teams(){
        blueTeam = new Team("Синие", playersMaxCfg);
        redTeam = new Team("Красные", playersMaxCfg);
        teams.add(blueTeam);
        teams.add(redTeam);
    }

    public void create4teams(){
        blueTeam = new Team("Синие", playersMaxCfg);
        redTeam = new Team("Красные", playersMaxCfg);
        yellowTeam = new Team("Желтые", playersMaxCfg);
        greenTeam = new Team("Зеленые", playersMaxCfg);
        teams.add(blueTeam);
        teams.add(redTeam);
        teams.add(yellowTeam);
        teams.add(greenTeam);
    }
}
