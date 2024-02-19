package me.nils.everhunt.menu.standard;

import me.nils.everhunt.menu.Menu;
import me.nils.everhunt.menu.PlayerMenuUtility;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ProductMenu extends Menu {
    public ProductMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "Product Menu";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        // add logic
    }

    @Override
    public void setMenuItems() {
        // make menu
        setFillerGlass();
    }
}
