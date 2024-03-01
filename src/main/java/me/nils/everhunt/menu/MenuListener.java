package me.nils.everhunt.menu;

import me.nils.everhunt.Everhunt;
import me.nils.everhunt.menu.standard.AddProductMenu;
import me.nils.everhunt.menu.standard.MarketMenu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.inventory.InventoryHolder;

public class MenuListener implements Listener {
    @EventHandler
    public void onMenuClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (event.getClickedInventory() == null) return;

        if (event.getCurrentItem() != null && event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().displayName().toString().contains(player.getName())) {
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
    public void onTextEnter(PlayerChatEvent e) {
        Player player = e.getPlayer();

        PlayerMenuUtility playerMenuUtility = Everhunt.getPlayerMenuUtility(player);
        if (playerMenuUtility.isInput()) {
            if (playerMenuUtility.getInputType().equalsIgnoreCase("Search")) {
                String search = e.getMessage();
                playerMenuUtility.setSearch(search);

                new MarketMenu(Everhunt.getPlayerMenuUtility(player), true).open();

                playerMenuUtility.setInput(false);

                e.getRecipients().clear();
                e.getRecipients().add(player);

                return;
            }

            if (playerMenuUtility.getInputType().equalsIgnoreCase("Set Price")) {
                int price = Integer.parseInt(e.getMessage());

                new AddProductMenu(playerMenuUtility, price).open();

                playerMenuUtility.setInput(false);

                e.getRecipients().clear();

                return;
            }
        }
    }
}
