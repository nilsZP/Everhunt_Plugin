package me.nils.everhunt.listeners;

import me.nils.everhunt.data.CostNPCData;
import me.nils.everhunt.data.PlayerData;
import me.nils.everhunt.data.TeleportData;
import me.nils.everhunt.managers.ItemManager;
import me.nils.everhunt.utils.Menu;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;

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

        if (event.getView().getTitle().equals("Sell Menu")) {
            CostNPCData data = CostNPCData.data.get(event.getCurrentItem().displayName().toString());

            PlayerData pData = PlayerData.data.get(uuid);

            pData.pay(data.getCost());
        }
    }

    @EventHandler
    public void menuDrag(InventoryDragEvent event) {
        Player player = (Player) event.getWhoClicked();
        String uuid = player.getUniqueId().toString();
        if (event.getView().getTitle().equals("Sell Menu")) {
            if (!event.getCursor().hasItemMeta()) {
                event.setCancelled(true);
                return;
            }
            if (event.getCursor().getItemMeta().getLocalizedName().equals("sell")) {
                if (!event.getOldCursor().hasItemMeta()) {
                    event.setCancelled(true);
                    return;
                }
                ItemManager item = ItemManager.items.get(ChatColor.stripColor(event.getCursor().getItemMeta().getDisplayName()));
                if (item == null) {
                    event.setCancelled(true);
                    return;
                }

                player.getInventory().remove(event.getOldCursor());
                PlayerData.data.get(uuid).addCoins(item.getValue());
            }
        }
    }
}
