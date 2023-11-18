package me.nils.everhunt.listeners;

import me.nils.everhunt.data.PlayerData;
import me.nils.everhunt.data.TeleportData;
import me.nils.everhunt.utils.Menu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MenuListener implements Listener {
    // TODO insert logic
    @EventHandler
    public void menuInteract(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getClickedInventory() == Menu.createTeleportMenu(player)) {
            switch (event.getCurrentItem().getItemMeta().getLocalizedName()) {
                case "Village" -> {
                    player.closeInventory();
                    player.teleport(TeleportData.getCoords(PlayerData.getPlayerID(player),"Village"));
                }
            }
        }
    }
}
