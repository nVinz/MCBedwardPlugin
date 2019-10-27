package my.nvinz.core.vnizcore.UI;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import my.nvinz.core.vnizcore.VnizCore;
import net.minecraft.server.v1_13_R2.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_13_R2.CraftServer;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class Tab {
    public List<EntityPlayer> tabEntityPlayers;

    VnizCore plugin;
    public Tab(VnizCore pl){
        tabEntityPlayers = new ArrayList<>();
        plugin = pl;
    }

    public void tabUpdatePlayers(Player player){
        tabRemovePlayers(player);
        int j = 0;
        plugin.players.forEach( p-> {
            if (plugin.players_and_teams.containsKey(p)){
                tabEntityPlayers.add(tabCreatePlayers("0"+j, plugin.players_and_teams.get(p).chatColor+p.getName(), "INVALID TEXTURE", "INVALID TEXTURE"));
            }
            else{
                tabEntityPlayers.add(tabCreatePlayers("0"+j, ChatColor.WHITE+p.getName(), "INVALID TEXTURE", "INVALID TEXTURE"));
            }
        });
        tabAddPlayers(player, tabEntityPlayers);
    }

    public void tabUpdateAllPlayers(){
        plugin.players.forEach(this::tabRemovePlayers);
        tabEntityPlayers.clear();

        int j = 0;
        plugin.players.forEach( p-> {
            if (plugin.players_and_teams.containsKey(p)){
                tabEntityPlayers.add(tabCreatePlayers("0"+j, plugin.players_and_teams.get(p).chatColor+p.getName(), "INVALID TEXTURE", "INVALID TEXTURE"));
            }
            else{
                tabEntityPlayers.add(tabCreatePlayers("0"+j, ChatColor.GRAY+p.getName(), "INVALID TEXTURE", "INVALID TEXTURE"));
            }
        });

        plugin.players.forEach(player -> tabAddPlayers(player, tabEntityPlayers));
    }

    public void tabRemovePlayers(Player player) {
        Collection<? extends Player> playersBukkit = Bukkit.getOnlinePlayers();
        EntityPlayer[] playersNMS = new EntityPlayer[playersBukkit.size() + tabEntityPlayers.size()];
        int current = 0;
        for (Player players : playersBukkit) {
            playersNMS[current] = ((CraftPlayer) players).getHandle();
            current++;
        }
        for (EntityPlayer players : tabEntityPlayers) {
            playersNMS[current] = players;
            current++;
        }
        PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, playersNMS);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }

    public void tabRestorePlayers(Player player) {
        tabRemovePlayers(player);
        Collection<? extends Player> playersBukkit = Bukkit.getOnlinePlayers();
        EntityPlayer[] playersNMS = new EntityPlayer[playersBukkit.size()];
        int current = 0;
        for (Player players : playersBukkit) {
            playersNMS[current] = ((CraftPlayer) players).getHandle();
            current++;
        }
        tabAddPlayers(player, playersNMS);
    }

    public EntityPlayer tabCreatePlayers(String name, String listName, String texture, String signature) {
        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer worldserver = server.getWorldServer(DimensionManager.a(0));
        PlayerInteractManager playerinteractmanager = new PlayerInteractManager(worldserver);
        GameProfile profile = new GameProfile(UUID.randomUUID(), name);
        profile.getProperties().put("textures", new Property("textures", texture, signature));
        EntityPlayer player = new EntityPlayer(server, worldserver, profile, playerinteractmanager);
        player.listName = new ChatComponentText(listName);
        return player;
    }

    public void tabAddPlayers(Player player, List<EntityPlayer> createdPlayers) {
        PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, createdPlayers);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }

    public void tabAddPlayers(Player player, EntityPlayer... createdPlayers) {
        PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, createdPlayers);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }
}
