package me.nils.everhunt.listeners;

import me.nils.everhunt.data.BackpackData;
import me.nils.everhunt.managers.ItemManager;
import me.nils.everhunt.utils.Backpack;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BackpackListener implements Listener {
    @EventHandler
    public void open(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            ItemStack item = player.getInventory().getItemInMainHand();
            ItemMeta meta = item.getItemMeta();

            if (meta == null) {
                return;
            }

            String name = ChatColor.stripColor(meta.getDisplayName());

            if (!(name.contains("Backpack"))) {
                return;
            }

            ItemManager backpack = ItemManager.items.get(name);

            if (backpack == null) {
                return;
            }

            player.openInventory(Backpack.Menu(player));
        }
    }

    @EventHandler
    public void close(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        String uuid = player.getUniqueId().toString();
        if (event.getView().getTitle().equals("Backpack")) {
            Inventory menu = event.getInventory();

            ItemStack[] contents = menu.getContents();
            for (int i = 0; i < 54; i++) {
                ItemStack item = contents[i];
                
                if (item != null && item.hasItemMeta()) {
                    String name = ChatColor.stripColor(item.getItemMeta().getDisplayName());
                    int amount = item.getAmount();

                    new BackpackData(uuid, name, amount, i);
                } else {
                    BackpackData.removeData(uuid,i);
                }
            }
        }
    }
}
