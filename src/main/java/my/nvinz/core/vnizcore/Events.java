package my.nvinz.core.vnizcore;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.ListIterator;

public class Events implements Listener {

    private VnizCore plugin;
    public Events(VnizCore pl){
        plugin = pl;
    }

    @EventHandler
    public void chatCheck(AsyncPlayerChatEvent event){
        String message = event.getMessage();
        Player player = event.getPlayer();

        event.setFormat(ChatColor.GRAY+player.getName() + ChatColor.DARK_GRAY+": " + ChatColor.GRAY+event.getMessage());

        if (message.contains("b")){
            if (plugin.blueTeam.isFull()) {
                player.sendMessage(ChatColor.RED+"Команда заполнена!");
                return;
            }
            else {
                /*ListIterator<Player> teamPlayers = plugin.blueTeam.players.listIterator();
                while (teamPlayers.hasNext()){
                    if (teamPlayers.equals(player)){
                        player.sendMessage(ChatColor.RED+"Вы уже в этой команде!");
                        return;
                    }
                }*/
                plugin.blueTeam.players.forEach((p) -> {
                    if (p.getName().equals(player.getName())) {
                        player.sendMessage(ChatColor.RED+"Вы уже в этой команде!");
                        return;
                    }
                });
                ListIterator<Team> teamsIt = plugin.teams.listIterator();
                while (teamsIt.hasNext()){
                    if (teamsIt.next().players.contains(player)){
                        teamsIt.next().removePlayer(player);
                    }
                }
                plugin.blueTeam.addPlayer(player);
                player.sendMessage(ChatColor.GRAY+"Вы присоединились к команде " + ChatColor.BLUE+"синих");
            }
            event.setCancelled(true);
        }
        else if (message.contains("r")){
            if (plugin.redTeam.isFull()) {
                player.sendMessage(ChatColor.RED+"Команда заполнена!");
                return;
            }
            else {
                /*ListIterator<Player> teamPlayers = plugin.redTeam.players.listIterator();
                while (teamPlayers.hasNext()){
                    if (teamPlayers.equals(player)){
                        player.sendMessage(ChatColor.RED+"Вы уже в этой команде!");
                        return;
                    }
                }*/
                plugin.redTeam.players.forEach((p) -> {
                    if (p.getName().equals(player.getName())) {
                        player.sendMessage(ChatColor.RED+"Вы уже в этой команде!");
                        return;
                    }
                });
                ListIterator<Team> teamsIt = plugin.teams.listIterator();
                while (teamsIt.hasNext()){
                    if (teamsIt.next().players.contains(player)){
                        teamsIt.next().removePlayer(player);
                    }
                }
                plugin.redTeam.addPlayer(player);
                player.sendMessage(ChatColor.GRAY + "Вы присоединились к команде " + ChatColor.RED + "красных");
            }
            event.setCancelled(true);
        }
        else if (message.contains("n")){
            plugin.teams.forEach((team) -> {
                team.players.forEach((p) -> {
                    if (p.getName().equals(player.getName())){
                        team.removePlayer(player);
                    }
                });
            });
            player.sendMessage(ChatColor.GRAY + "Вы покинули команду");
            event.setCancelled(true);
        }

        ListIterator<Player> blueIt = plugin.blueTeam.players.listIterator();
        ListIterator<Player> redIt = plugin.redTeam.players.listIterator();
        while (blueIt.hasNext()) {
            if (blueIt.next().getName().equals(player.getName())) {
                event.setFormat(ChatColor.BLUE + player.getName() + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + event.getMessage());
            }
        }

        while (redIt.hasNext()) {
            if (redIt.next().getName().equals(player.getName())) {
                event.setFormat(ChatColor.RED + player.getName() + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + event.getMessage());
            }
        }

        /*boolean enabled = plugin.getConfig().getBoolean("banned-words-enabled");
        int incedents = plugin.getConfig().getInt("incidents." + player.getUniqueId().toString());
        List<String> words = plugin.getConfig().getStringList("banned-words");
        if(enabled){
            for (String banned: words){
                if (message.contains(banned)){
                    event.setCancelled(true);
                    player.sendMessage(ChatColor.RED + "Осуждаем.");

                    if (incedents != 0){
                        incedents++;
                        plugin.getConfig().set("incidents." + player.getName(), incedents);
                        plugin.saveConfig();
                    }
                    else{
                        plugin.getConfig().set("incidents." + player.getName(), 1);
                        plugin.saveConfig();
                    }
                }
            }
        }*/
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        PlayerInventory inventory = player.getInventory();
        String currPlayers = Integer.toString(plugin.getServer().getOnlinePlayers().size());
        String maxPlayers = Integer.toString(plugin.getConfig().getInt("max-players"));

        if (Integer.parseInt(currPlayers) < Integer.parseInt(maxPlayers))
            event.setJoinMessage(ChatColor.DARK_GRAY+"[" + ChatColor.RED+currPlayers + ChatColor.GRAY+"/" + ChatColor.RED+maxPlayers + ChatColor.DARK_GRAY+"] " +
                    ChatColor.WHITE+player.getName() + ChatColor.GRAY+" присоединился к игре.");
        else {
            event.setJoinMessage(ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + currPlayers + ChatColor.GRAY + "/" + ChatColor.GREEN + maxPlayers + ChatColor.DARK_GRAY + "] " +
                    ChatColor.WHITE + player.getName() + ChatColor.GRAY + " присоединился к игре.");
            plugin.stage.startCountdown();
        }

        player.teleport(new Location(player.getWorld(), -226.5, 64, -86.5));

        inventory.clear();
        ItemStack menuItem = new ItemStack(Material.BOOK);
        ItemMeta menuItemMeta = menuItem.getItemMeta();
        menuItemMeta.setDisplayName(ChatColor.AQUA+"Выбор команды");
        inventory.setItem(4, menuItem);
    }
    /*    String joinMessage = "";
        Player player = event.getPlayer();
        if (player.isOp()){
            joinMessage = ChatColor. GREEN+"> " + ChatColor.WHITE+player.getDisplayName() + ChatColor.GREEN+" зашел на сервер.";
        }
        event.setJoinMessage(joinMessage);

        if (!player.hasPlayedBefore()){
            for (Player players: plugin.getServer().getOnlinePlayers()){
                players.sendMessage(ChatColor. GREEN+"> " + ChatColor.WHITE+player.getDisplayName() + ChatColor.GREEN+" первый раз зашел на сервер!");
            }
        }
    }*/

    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        Player player = event.getPlayer();
        PlayerInventory inventory = player.getInventory();
        String currPlayers = Integer.toString(plugin.getServer().getOnlinePlayers().size() - 1);
        String maxPlayers = Integer.toString(plugin.getConfig().getInt("max-players"));

        event.setQuitMessage(ChatColor.DARK_GRAY+"[" + ChatColor.RED+currPlayers + ChatColor.GRAY+"/" + ChatColor.RED+maxPlayers + ChatColor.DARK_GRAY+"] " +
                ChatColor.WHITE+player.getName() + ChatColor.GRAY+" покинул игру.");

        plugin.teams.forEach((team) -> {
            team.players.forEach((p) -> {
                if (p.getName().equals(player.getName())){
                    team.removePlayer(player);
                }
            });
        });

        inventory.clear();
    }

    @EventHandler
    public void onMenuOpen(PlayerInteractEvent event){
        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_BLOCK)){
            Player player = event.getPlayer();
            if(player.getItemInHand().getType() == Material.BOOK){
                player.sendMessage("Menu");
            }
        }
    }
}
