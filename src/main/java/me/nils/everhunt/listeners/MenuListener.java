package me.nils.everhunt.listeners;

import me.nils.everhunt.data.TeleportData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Objects;

public class MenuListener implements Listener {
    @EventHandler
    public void menuInteract(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        String uuid = player.getUniqueId().toString();
        if (event.getView().getTitle().equals("Teleport Menu")) {
            player.closeInventory();
            player.teleport(Objects.requireNonNull(TeleportData.getCoords(uuid, Objects.requireNonNull(event.getCurrentItem()).getItemMeta().getLocalizedName())));
        }
    }
}
