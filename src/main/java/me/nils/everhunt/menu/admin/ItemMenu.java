package me.nils.everhunt.menu.admin;

import me.nils.everhunt.Everhunt;
import me.nils.everhunt.items.Items;
import me.nils.everhunt.menu.Menu;
import me.nils.everhunt.menu.PlayerMenuUtility;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ItemMenu extends Menu {
    public ItemMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "Admin Items";
    }

    @Override
    public int getSlots() {
        int size = Everhunt.items.getItems().length;
        while (size%9 != 0) {
            size++;
        }

        return size;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {

    }

    @Override
    public void setMenuItems() {
        inventory.setContents(Everhunt.items.getItems());
    }
}
