package me.nils.everhunt.menu;

import me.nils.everhunt.Everhunt;
import me.nils.everhunt.menu.standard.AddProductMenu;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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

        if (holder instanceof Menu menu) {
            event.setCancelled(true);

            if (event.getCurrentItem() == null) return;

            menu.handleMenu(event);
        }
    }

    @EventHandler
    public void onTextMenuOpen(InventoryOpenEvent e) {
        if (e.getInventory().getType() == InventoryType.ANVIL) {
            e.getInventory().setItem(0,new ItemStack(Material.PAPER));
        }
    }

    @EventHandler
    public void onTextEnter(PrepareAnvilEvent event) {
        Player player = (Player) event.getInventory().getViewers().get(0);
        if(event.getResult() != null && event.getResult().hasItemMeta() && !event.getInventory().getRenameText().isEmpty()){
            String name = event.getResult().displayName().toString();

            new AddProductMenu(Everhunt.getPlayerMenuUtility(player),Integer.parseInt(name));
        }
    }
}
