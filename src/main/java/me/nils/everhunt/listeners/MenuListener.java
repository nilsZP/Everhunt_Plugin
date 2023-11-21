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
    // TODO insert logic
    @EventHandler
    public void menuInteract(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getView().getTitle().equals("Teleport Menu")) {
            switch (Objects.requireNonNull(event.getCurrentItem()).getItemMeta().getLocalizedName()) {
                case "Village" -> {
                    player.closeInventory();
                    player.teleport(Objects.requireNonNull(TeleportData.getCoords(PlayerData.getPlayerID(player), "Village")));
                }
            }
        }
    }
}
