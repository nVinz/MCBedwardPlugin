package my.nvinz.core.vnizcore.game;

import my.nvinz.core.vnizcore.VnizCore;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class Items {

    private VnizCore plugin;
    public Map<String, ItemStack> items;
    public Items(VnizCore pl){
        plugin = pl;
        items = new HashMap<>();
        Map<String, Object> items_cfg = Objects.requireNonNull(
                plugin.getConfig().getConfigurationSection("items")).getValues(false);
        items_cfg.forEach( (item_cfg, obj) -> {
            addItem(item_cfg,
                    plugin.getConfig().getString("items."+item_cfg+".name"),
                    plugin.getConfig().getStringList("items."+item_cfg+".lore"));
        });
    }

    public void addItem(String configName, String displayName, List<String> lore){
        String itemName = Objects.requireNonNull(
                plugin.getConfig().getString("items." + configName + ".item-id")).toUpperCase();
        ItemStack itemStack = new ItemStack(Material.getMaterial(itemName), 1);{
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));
            List<String> itemLore = new ArrayList<>();
            lore.forEach(string -> {
                itemLore.add(ChatColor.translateAlternateColorCodes('&', string));
            });
            itemMeta.setLore(itemLore);
            itemStack.setItemMeta(itemMeta);
            items.put(configName, itemStack);
        }
    }

}
