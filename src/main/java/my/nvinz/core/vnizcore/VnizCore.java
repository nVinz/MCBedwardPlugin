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

        parseTeams(this);

        status = Stage.Status.LOBBY;
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

    void parseTeams(VnizCore plugin){
        Map<String, Object> teams_cfg = plugin.getConfig().getConfigurationSection("teams2").getValues(false);
        teams_cfg.forEach( (team_cfg, obj) -> {
            String teamName = plugin.getConfig().getString("teams2."+team_cfg+".name");
            String spawnPoint = plugin.getConfig().getString("teams2."+team_cfg+".spawn");

            plugin.getServer().getConsoleSender().sendMessage("Building team with parameters:");

            TeamBuilder teamBuilder = new TeamBuilder(plugin);
            teamBuilder.setTeamColor(team_cfg)
                    .setTeamName(teamName)
                    .setChatColor(team_cfg)
                    .setSpawnpoint(spawnPoint);

            teamBuilder.buildTeam();
        });
    }
}
