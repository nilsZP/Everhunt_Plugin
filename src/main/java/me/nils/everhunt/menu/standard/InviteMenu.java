package me.nils.everhunt.menu.standard;

import me.nils.everhunt.data.GuildData;
import me.nils.everhunt.menu.Menu;
import me.nils.everhunt.menu.PlayerMenuUtility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InviteMenu extends Menu {
    private final String guild;
    public InviteMenu(PlayerMenuUtility playerMenuUtility, String guild) {
        super(playerMenuUtility);
        this.guild = guild;
    }

    @Override
    public String getMenuName() {
        return "You've been invited to";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player player = playerMenuUtility.getOwner();

        if (e.getCurrentItem().getType().equals(Material.BARRIER)) {
            if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Close")) player.closeInventory();
        }

        if (e.getCurrentItem().getType().equals(Material.GOLD_BLOCK)) {
            if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Accept")) new GuildData(player,guild);
        }
    }

    @Override
    public void setMenuItems() {
        inventory.setItem(22,makeItem(Material.GREEN_BANNER,guild));
        inventory.setItem(40,makeItem(Material.GOLD_BLOCK, ChatColor.GOLD + "Accept"));
        inventory.setItem(49,CLOSE_BUTTON);

        setFillerGlass();
    }
}
