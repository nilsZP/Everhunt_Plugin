package me.nils.everhunt.menu;

import me.nils.everhunt.Everhunt;
import me.nils.everhunt.menu.standard.AddProductMenu;
import me.nils.everhunt.menu.standard.MarketMenu;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

public class MenuListener implements Listener {
    @EventHandler
    public void onMenuClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        
        if (event.getClickedInventory() == null) return;
        
        if (event.getCurrentItem() != null && event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().displayName().toString().contains(player.getName())) {
            event.setCancelled(true);
            return;
        }
        
        if (event.getClickedInventory().getType() == InventoryType.ANVIL) {
            if (event.getInventory().getItem(0) == null) return;
            
            if (Everhunt.getPlayerMenuUtility(player).getAnvilGUI().equalsIgnoreCase("Search")) {
                String name = PlainTextComponentSerializer.plainText().serialize(event.getCurrentItem().displayName());// TODO fix not working
                Everhunt.getPlayerMenuUtility(player).setSearch(name);
                player.sendMessage(Component.text(name));

                new MarketMenu(Everhunt.getPlayerMenuUtility(player), true).open();

                return;
            }
            if (Everhunt.getPlayerMenuUtility(player).getAnvilGUI().equalsIgnoreCase("Set Price")) {
                String name = PlainTextComponentSerializer.plainText().serialize(event.getCurrentItem().displayName());
                player.sendMessage(Component.text(name));

                new AddProductMenu(Everhunt.getPlayerMenuUtility(player), Integer.parseInt(name)).open();

                return;
            }
        }

        InventoryHolder holder = event.getClickedInventory().getHolder();

        if (holder instanceof Menu menu) {
            event.setCancelled(true);

            menu.handleMenu(event);
        }
    }
}
