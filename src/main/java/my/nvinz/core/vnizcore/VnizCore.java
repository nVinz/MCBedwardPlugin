package my.nvinz.core.vnizcore;

import my.nvinz.core.vnizcore.events.BlockEvents;
import my.nvinz.core.vnizcore.events.ChatEvents;
import my.nvinz.core.vnizcore.events.Commands;
import my.nvinz.core.vnizcore.events.GameEvents;
import my.nvinz.core.vnizcore.game.Items;
import my.nvinz.core.vnizcore.game.Menu;
import my.nvinz.core.vnizcore.game.Stage;
import my.nvinz.core.vnizcore.game.Variables;
import my.nvinz.core.vnizcore.teams.Team;
import my.nvinz.core.vnizcore.teams.TeamBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class VnizCore extends JavaPlugin {

    public Stage stage;
    public Stage.Status stageStatus;
    public Variables variables;
    public Items items;

    public List<Team> teams = new ArrayList<>();
    public List<Player> players = new ArrayList<>();    // Make non-full server game
    public Map<Player, Team> players_and_teams = new HashMap<>();
    public Map<Team, Material> teams_beds = new HashMap<>();

    @Override
    public void onEnable() {
        registerEvents(this);
        setCommandsExecutors(this);
        setupConfig(this);
        parseTeams(this);
        setupStage(this);
        variables =  new Variables(this);
        items = new Items(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    /*
     *  Setup plugin
     */

    void registerEvents(VnizCore plugin){
        try {
             ChatEvents chatEvents = new ChatEvents(plugin);
             BlockEvents blockEvents = new BlockEvents(plugin);
             GameEvents gameEvents = new GameEvents(plugin);
             Menu menu = new Menu();
             plugin.getServer().getPluginManager().registerEvents(blockEvents, plugin);
             plugin.getServer().getPluginManager().registerEvents(chatEvents, plugin);
             plugin.getServer().getPluginManager().registerEvents(gameEvents, plugin);
             plugin.getServer().getPluginManager().registerEvents(menu, plugin);
        } catch (Exception e) {
             plugin.getServer().getConsoleSender().sendMessage("Error registering events: " + e.getMessage());
        }
    }

    void setCommandsExecutors(VnizCore plugin){
        try {
            this.getCommand("none").setExecutor(new Commands(plugin));
            this.getCommand("teams").setExecutor(new Commands(plugin));
            this.getCommand("start").setExecutor(new Commands(plugin));
            this.getCommand("test").setExecutor(new Commands(plugin));
            this.getCommand("jointeam").setExecutor(new Commands(plugin));
        } catch (Exception e) {
            plugin.getServer().getConsoleSender().sendMessage("Error setting commands executors: " + e.getMessage());
        }

    }

    void setupConfig(VnizCore plugin){
        try {
            plugin.getConfig().options().copyDefaults(true);
            //plugin.saveConfig();
        } catch (Exception e) {
            plugin.getServer().getConsoleSender().sendMessage("Error setup config: " + e.getMessage());
        }
    }

    void parseTeams(VnizCore plugin){
        try {
            Map<String, Object> teams_cfg = plugin.getConfig().getConfigurationSection("teams").getValues(false);
            teams_cfg.forEach((team_cfg, obj) -> {

                World world = plugin.getServer().getWorld(plugin.getConfig().getString("arena.world"));
                plugin.getServer().getConsoleSender().sendMessage("Building team with parameters: ");

                TeamBuilder teamBuilder = new TeamBuilder(plugin);
                teamBuilder.setTeamColor(team_cfg)
                        .setTeamName(plugin.getConfig().getString("teams." + team_cfg + ".name"))
                        .setChatColor(team_cfg)
                        .setSpawnPoint(plugin.getConfig().getString("teams." + team_cfg + ".spawn"), world)
                        .setBedMaterial(team_cfg)
                        .setMaxPlayers(plugin.getConfig().getInt("teams." + team_cfg + ".max-players"));

                // Check if bed is staying
                teamBuilder.buildTeam();
            });
        } catch (Exception e) {
            plugin.getServer().getConsoleSender().sendMessage("Error parsing teams: " + e.getMessage());
        }
    }

    void setupStage(VnizCore plugin){
        stage = new Stage(plugin);
        stageStatus = Stage.Status.LOBBY;
    }

    /*
     *  TOOLS
     */

    /*
     *  Tells message to everyone on server
     */
    @Deprecated
    public void makeAnnouncement(String message){
        for (Player players: this.getServer().getOnlinePlayers()){
            players.sendMessage(message);
        }
    }

    /*
     *  Tells message to team players
     */
    public void makeTeamAnnouncement(Team team, String message){
        team.players.forEach(player -> {
            player.sendMessage(message);
        });
    }

    public void addPlayerToTeam(Player player, Team team){
        team.addPlayer(player);
        players_and_teams.put(player, team);
        player.sendMessage(ChatColor.GRAY+"Вы присоединились к команде " +
                players_and_teams.get(player).chatColor+players_and_teams.get(player).teamName);
    }

    public void removePlayerFromTeam(Player player){
        try {
            players_and_teams.get(player).removePlayer(player);
            players_and_teams.remove(player);
        } catch (NullPointerException e) {}
    }

    /*
     *  Build double[]
     *  [0] X, [1] Y, [2] Z
     *  From String (X, Y, Z)
     */
    public double[] parseLocation(String location){
        double[] coords = {0.0, 0.0, 0.0};
        StringTokenizer st = new StringTokenizer(location.replace(',', ' '));
        while (st.hasMoreTokens()) {
            coords[0] = Double.parseDouble(st.nextToken());     // X
            coords[1] = Double.parseDouble(st.nextToken());     // Y
            coords[2] = Double.parseDouble(st.nextToken());     // Z
        }
        return coords;
    }

    /*
     *  Build Location from
     *  World & String (X, Y, Z)
     */
    public Location setupLocation(World world, String position) {
        double[] coords = parseLocation(position);
        Location location = new Location(world, coords[0], coords[1], coords[2]);
        return location;
    }

    public void isTeamLost(Player player){
        if (!players_and_teams.get(player).isAlive(1)) {
            teams.remove(players_and_teams.get(player));
            makeAnnouncement(ChatColor.GRAY + "Команда " +
                    players_and_teams.get(player).chatColor + players_and_teams.get(player).teamName +
                    (ChatColor.GRAY + " проиграла."));
        }
        checkWinner();
        // change stage
    }

    public void checkWinner(){
        if (teams.size() == 1){
            makeAnnouncement(ChatColor.GREEN+"Победила команда " + teams.get(0).chatColor + teams.get(0).teamName + ChatColor.GREEN+"!");
        }
        // change stage
    }
}
