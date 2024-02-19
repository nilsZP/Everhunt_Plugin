package me.nils.everhunt.menu.standard;

import me.nils.everhunt.menu.PaginatedMenu;
import me.nils.everhunt.menu.PlayerMenuUtility;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class MarketMenu extends PaginatedMenu {
    public MarketMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "Market Menu";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        ArrayList<ItemStack> list = new ArrayList<>();

        handlePages(player, list, e);
    }

    @Override
    public void setMenuItems() {
        addMenuBorder();
        insertContents(new ArrayList<>());
    }
}
