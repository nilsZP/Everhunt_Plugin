package me.nils.everhunt.menu.standard;

import me.nils.everhunt.menu.Menu;
import me.nils.everhunt.menu.PlayerMenuUtility;
import org.bukkit.event.inventory.InventoryClickEvent;

public class AddProductMenu extends Menu {
    public AddProductMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "Add Product";
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
        // TODO finish this menu
    }
}
