package me.nils.everhunt.listeners;

import me.nils.everhunt.data.PlayerData;
import me.nils.everhunt.data.TeleportData;
import me.nils.everhunt.managers.ItemManager;
import me.nils.everhunt.utils.Menu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class MenuListener implements Listener {
    @EventHandler
    public void menuInteract(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getCurrentItem().displayName().toString().contains(player.getName())) {
            event.setCancelled(true);
            return;
        }
        String uuid = player.getUniqueId().toString();
        if (event.getView().getTitle().equals("Teleport Menu")) {
            player.closeInventory();
            player.teleport(Objects.requireNonNull(TeleportData.getCoords(uuid, Objects.requireNonNull(event.getCurrentItem()).getItemMeta().getLocalizedName())));
        }
    }
}
