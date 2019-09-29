package my.nvinz.core.vnizcore;

import my.nvinz.core.vnizcore.events.BlockEvents;
import my.nvinz.core.vnizcore.events.ChatEvents;
import my.nvinz.core.vnizcore.events.Commands;
import my.nvinz.core.vnizcore.game.Stage;
import my.nvinz.core.vnizcore.teams.Team;
import my.nvinz.core.vnizcore.teams.TeamBuilder;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class VnizCore extends JavaPlugin {

    public ChatEvents chatEvents = new ChatEvents(this);
    public BlockEvents blockEvents = new BlockEvents(this);
    public Stage stage = new Stage(this);
    public Stage.Status status;

    public List<Team> teams = new ArrayList<Team>();
    public Map<String, Team> colors_and_teams = new HashMap<>();
    public Team blueTeam;
    public Team redTeam;
    public Team yellowTeam;
    public Team greenTeam;

    public Map<Player, Team> players_and_teams = new HashMap<>();

    @Override
    public void onEnable() {
        registerEvents(this);
        setCommandsExecutors(this);

        this.getConfig().options().copyDefaults(true);
        this.saveConfig();

        /*if (this.getConfig().getInt("teams") *
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
        }*/

        /*List<String> spawnPositions = this.getConfig().getStringList("team-spawns");
        ListIterator<String> spawnPositionsIt = spawnPositions.listIterator();
        ListIterator<Team> teamsIt = teams.listIterator();
        while (spawnPositionsIt.hasNext()) {
            teamsIt.next().setSpawnPositions(spawnPositionsIt.next());
        }*/

        status = Stage.Status.LOBBY;

        Map<String, Object> teams_cfg = this.getConfig().getConfigurationSection("teams2").getValues(false);
        teams_cfg.forEach( (team_cfg, obj) -> {
            String teamName = this.getConfig().getString("teams2."+team_cfg+".name");
            String spawnPoint = this.getConfig().getString("teams2."+team_cfg+".spawn");

            this.getServer().getConsoleSender().sendMessage("Building team with parameters:");

            TeamBuilder teamBuilder = new TeamBuilder(this);
            teamBuilder.setTeamColor(team_cfg)
            .setTeamName(teamName)
            .setChatColor(team_cfg)
            .setSpawnpoint(spawnPoint);

            teamBuilder.buildTeam();
        });
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    void registerEvents(VnizCore plugin){
        this.getServer().getPluginManager().registerEvents(blockEvents, plugin);
        this.getServer().getPluginManager().registerEvents(chatEvents, plugin);

    }

    void setCommandsExecutors(VnizCore plugin){
        this.getCommand("none").setExecutor(new Commands(plugin));
        this.getCommand("teams").setExecutor(new Commands(plugin));
        this.getCommand("start").setExecutor(new Commands(plugin));
        this.getCommand("test").setExecutor(new Commands(plugin));
        this.getCommand("jointeam").setExecutor(new Commands(plugin));
    }
}
