package my.nvinz.core.vnizcore;

import org.bukkit.ChatColor;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

public class Menu {

    public void Menu(Player player){
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
    }
}
