package me.nils.everhunt.menu.standard;

import me.nils.everhunt.data.AchievementData;
import me.nils.everhunt.menu.PaginatedMenu;
import me.nils.everhunt.menu.PlayerMenuUtility;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class AchievementMenu extends PaginatedMenu {
    public AchievementMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "Achievements";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();

        handlePages(player, AchievementData.getAchievements(player),e);
    }

    @Override
    public void setMenuItems() {
        addMenuBorder();
        insertContents(AchievementData.getAchievements(playerMenuUtility.getOwner()));
    }
}
