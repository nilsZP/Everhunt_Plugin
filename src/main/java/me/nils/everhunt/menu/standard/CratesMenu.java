package me.nils.everhunt.menu.standard;

import me.nils.everhunt.menu.Menu;
import me.nils.everhunt.menu.PaginatedMenu;
import me.nils.everhunt.menu.PlayerMenuUtility;
import org.bukkit.event.inventory.InventoryClickEvent;

public class CratesMenu extends PaginatedMenu {
    private int level;
    public CratesMenu(PlayerMenuUtility playerMenuUtility,int level) {
        super(playerMenuUtility);
        this.level = level;
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

    }

    @Override
    public void setMenuItems() {
        addMenuBorder();
        // insertContents
    }
}
