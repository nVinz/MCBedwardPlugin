package my.nvinz.core.vnizcore.game;

import my.nvinz.core.vnizcore.VnizCore;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class Menu implements /*InventoryHolder,*/ Listener {

    @EventHandler
    public void onRightClick(PlayerInteractEvent event){
        if (event.getItem().getType().equals(Material.BOOK)){
            event.getPlayer().sendMessage("Menu");
        }
        /*if(event.getPlayer().getItemInHand().getType() == Material.BLAZE_POWDER){
            Fireball fire = p.getWorld().spawn(event.getPlayer().getLocation(), Fireball.class);
            fire.setShooter(p);
        }*/
    }


   /* private final Inventory inventory;
    private VnizCore plugin;

    public Menu(VnizCore plug, Player player) {
        plugin = plug;
        InventoryHolder holder = player;
        inventory = plugin.getServer().createInventory(holder, 9, "Menu");
        //inv = Bukkit.createInventory(this, 9, "Example");
    }

    public void initializeItems() {
        inventory.addItem(createGuiItem(Material.DIAMOND_SWORD,
                "Example Sword", "§aFirst line of the lore", "§bSecond line of the lore"));
        //inv.addItem(createGuiItem(Material.DIAMOND_SWORD, "Example Sword", "§aFirst line of the lore", "§bSecond line of the lore"));
        //inv.addItem(createGuiItem(Material.IRON_HELMET, "§bExample Helmet", "§aFirst line of the lore", "§bSecond line of the lore"));
    }

    private ItemStack createGuiItem(Material material, String name, String...lore) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        ArrayList<String> metalore = new ArrayList<String>();

        for(String lorecomments : lore) {
            metalore.add(lorecomments);
        }
        meta.setLore(metalore);
        item.setItemMeta(meta);
        return item;
    }

    public void openInventory(Player p) {
        p.openInventory(inventory);
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }*/

    /*public void Menu(Player player){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                player.openInventory(new InventoryView() {
                    @Override
                    public Inventory getTopInventory() {
                        return null;
                    }

                    @Override
                    public Inventory getBottomInventory() {
                        return null;
                    }

                    @Override
                    public HumanEntity getPlayer() {
                        return null;
                    }

                    @Override
                    public InventoryType getType() {
                        return null;
                    }
                });
            }
        });
        thread.start();
    }*/
}
