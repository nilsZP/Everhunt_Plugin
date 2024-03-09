package me.nils.everhunt.menu.admin;

import me.nils.everhunt.data.ReportData;
import me.nils.everhunt.menu.PaginatedMenu;
import me.nils.everhunt.menu.PlayerMenuUtility;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ReportMenu extends PaginatedMenu {
    private final String username;
    public ReportMenu(PlayerMenuUtility playerMenuUtility, String username) {
        super(playerMenuUtility);
        this.username = username;
    }

    @Override
    public String getMenuName() {
        return "Reports";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player player = playerMenuUtility.getOwner();

        if (username != null) {
            handlePages(player,ReportData.getReports(username),e);
        } else {
            handlePages(player,ReportData.getReports(),e);
        }
    }

    @Override
    public void setMenuItems() {
        addMenuBorder();
        if (username != null) {
            insertContents(ReportData.getReports(username));
        } else {
            insertContents(ReportData.getReports());
        }
    }
}
