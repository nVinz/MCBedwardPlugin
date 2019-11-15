package my.nvinz.core.vnizcore.events;

import my.nvinz.core.vnizcore.VnizCore;
import my.nvinz.core.vnizcore.map.Map;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.lang.reflect.Field;

public class Commands implements CommandExecutor {

    private VnizCore plugin;
    public Commands(VnizCore pl){
        plugin = pl;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)) {
            sender.sendMessage("Эта командя только для игроков!");
            return false;
        }

        Player player = (Player) sender;

        if (command.getName().equalsIgnoreCase("Vniz")){
            if (args.length == 0){
                player.sendMessage(ChatColor.GRAY+"Vniz");
                return false;
            }
            switch (args[0]) {
                case "none":
                    try {
                        player.sendMessage(ChatColor.GRAY+"Вы покинули команду " + plugin.players_and_teams.get(player).chatColor+plugin.players_and_teams.get(player).teamColor);
                        plugin.removePlayerFromTeam(player);
                    } catch (NullPointerException e){
                        player.sendMessage(ChatColor.GRAY+"Вы не в команде.");
                    }
                    break;
                case "team":
                    if (args.length == 2) {
                        if (plugin.players.contains(player)) {
                            plugin.teams.forEach(team -> {
                                if (team.teamColor.equalsIgnoreCase(args[1])) {
                                    if (team.hasFree()) {
                                        try {
                                            plugin.removePlayerFromTeam(player);
                                            plugin.addPlayerToTeam(player, team);
                                        } catch (NullPointerException e) {
                                            player.sendMessage(ChatColor.RED + "Неизвестная команда.");
                                        }
                                    } else {
                                        player.sendMessage(ChatColor.RED + "В команде нет мест.");
                                    }
                                }
                            });
                        }
                        else {
                            player.sendMessage(ChatColor.RED + "Вы не находитесь в лобби.");
                        }
                    }
                    //plugin.tab.tabUpdateAllPlayers();
                    break;
                case "teams":
                    plugin.teams.forEach(team -> {
                        player.sendMessage(team.chatColor + team.teamName + ": " + team.players.toString());
                    });
                    break;
                case "start":
                    plugin.stage.startCountdown();
                    break;
                case "join":
                    plugin.joinPlayer(player);
                    break;
                case "leave":
                    plugin.leavePlayer(player);
                    break;
                case "test":

                    setHeaderAndFooter(player, "1", "2");

                    break;

                case "map":
                    try {
                        Map map = new Map(plugin);
                        map.map();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    break;
                case "reload":
                    plugin.reloadConfig(plugin);
                    player.sendMessage(ChatColor.GREEN+"Конфиг перезагружен.");
                    break;
            }
        }
        return true;
    }

    public void setHeaderAndFooter(Player player, String header, String footer) {
        try {
            Object headerJson = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + ChatColor.GOLD + "Welcome to the Cakeland server" + "\"}");
            Object footerJson = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"Hi\",\"color\":\"dark_aqua\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Our awesome server!\"}]}}}");
            Object packet = getNMSClass("PacketPlayOutPlayerListHeaderFooter").getConstructor(getNMSClass("IChatBaseComponent")).newInstance(headerJson);

            Field footerField = packet.getClass().getDeclaredField("b");
            footerField.setAccessible(true);
            footerField.set(packet, footerJson);

            Object entityPlayer = player.getClass().getMethod("getHandle").invoke(player);
            Object playerConnection = entityPlayer.getClass().getField("playerConnection").get(entityPlayer);

            playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Class<?> getNMSClass(String name) throws ClassNotFoundException {
        return Class.forName("net.minecraft.server." + Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3] + "." + name);
    }
}
