package me.nils.everhunt.menu.standard;

import me.nils.everhunt.Everhunt;
import me.nils.everhunt.data.MarketData;
import me.nils.everhunt.data.PlayerData;
import me.nils.everhunt.menu.Menu;
import me.nils.everhunt.menu.PlayerMenuUtility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class CrateMenu extends Menu {
    private ItemStack crate;

    public CrateMenu(PlayerMenuUtility playerMenuUtility, ItemStack crate) {
        super(playerMenuUtility);
        this.crate = crate;
    }

    @Override
    public String getMenuName() {
        return crate.getItemMeta().getDisplayName();
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player player = playerMenuUtility.getOwner();

        if (e.getCurrentItem().getType().equals(Material.BARRIER)) {
            if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Close")) player.closeInventory();
        }

        if (e.getCurrentItem().getType().equals(Material.GOLD_BLOCK)) {
            if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Open")) {
                PlayerData data = PlayerData.data.get(player.getUniqueId().toString());

                    if (!data.pay(1/*price*/)) return;
                    player.closeInventory();
            }
        }
    }

    @Override
    public void setMenuItems() {
        inventory.setItem(22, crate);

        inventory.setItem(31, makeItem(Material.GOLD_BLOCK, ChatColor.GOLD + "Open"));

        inventory.setItem(49, CLOSE_BUTTON);
        setFillerGlass();
    }
}
