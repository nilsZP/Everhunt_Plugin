package me.nils.everhunt.menu.standard;

import me.nils.everhunt.data.AchievementData;
import me.nils.everhunt.data.JobData;
import me.nils.everhunt.menu.PaginatedMenu;
import me.nils.everhunt.menu.PlayerMenuUtility;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class JobMenu extends PaginatedMenu {
    public JobMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "Jobs";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();

        handlePages(player, JobData.getJobs(player),e);
    }

    @Override
    public void setMenuItems() {
        addMenuBorder();
        insertContents(JobData.getJobs(playerMenuUtility.getOwner()));
    }
}

