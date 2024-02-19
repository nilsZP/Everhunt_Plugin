package me.nils.everhunt.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;

public class MenuListener implements Listener {
    @EventHandler
    public void onMenuClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (event.getClickedInventory() == null) return;

        if (event.getCurrentItem().displayName().toString().contains(player.getName())) {
            event.setCancelled(true);
            return;
        }

        InventoryHolder holder = event.getClickedInventory().getHolder();

        if (holder instanceof me.nils.everhunt.menu.Menu menu) {
            event.setCancelled(true);

            if (event.getCurrentItem() == null) return;

            menu.handleMenu(event);
        }
    }
}
