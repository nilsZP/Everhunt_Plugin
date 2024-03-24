package me.nils.everhunt.menu.standard;

import me.nils.everhunt.Everhunt;
import me.nils.everhunt.items.Items;
import me.nils.everhunt.menu.Menu;
import me.nils.everhunt.menu.PaginatedMenu;
import me.nils.everhunt.menu.PlayerMenuUtility;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;

public class CratesMenu extends PaginatedMenu {
    public CratesMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "Crates";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player player = playerMenuUtility.getOwner();

        handlePages(player,Everhunt.items.getCrates(), e);

        if (e.getCurrentItem().hasItemMeta()) {
            if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).contains("Crate")) {
                new CrateMenu(playerMenuUtility,e.getCurrentItem()).open();
            }
        }
    }

    @Override
    public void setMenuItems() {
        addMenuBorder();
        insertContents(Everhunt.items.getCrates());
    }
}
