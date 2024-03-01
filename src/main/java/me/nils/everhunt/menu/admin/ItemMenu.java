package me.nils.everhunt.menu.admin;

import me.nils.everhunt.Everhunt;
import me.nils.everhunt.menu.PaginatedMenu;
import me.nils.everhunt.menu.PlayerMenuUtility;
import me.nils.everhunt.utils.Condition;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ItemMenu extends PaginatedMenu {
    public ItemMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "Admin Items";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();

        handlePages(player,Everhunt.items.getAll(), e);

        if (Condition.isCustom(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()))) {
            player.getInventory().addItem(e.getCurrentItem());
        }
    }

    @Override
    public void setMenuItems() {
        addMenuBorder();
        insertContents(Everhunt.items.getAll());
    }
}
