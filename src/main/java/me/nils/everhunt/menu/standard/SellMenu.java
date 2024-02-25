package me.nils.everhunt.menu.standard;

import me.nils.everhunt.Everhunt;
import me.nils.everhunt.data.MarketData;
import me.nils.everhunt.data.PlayerData;
import me.nils.everhunt.managers.*;
import me.nils.everhunt.menu.Menu;
import me.nils.everhunt.menu.PaginatedMenu;
import me.nils.everhunt.menu.PlayerMenuUtility;
import me.nils.everhunt.utils.Condition;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class SellMenu extends PaginatedMenu {
    public SellMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "Your Products";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        ArrayList<ItemStack> list = MarketData.getYourProducts(playerMenuUtility.getOwner());
        String item = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());

        handlePages(player, list, e);

        if (e.getCurrentItem().getType().equals(Material.OAK_BUTTON)) {
            if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Add product")) {
                new AddProductMenu(Everhunt.getPlayerMenuUtility(player), 1).open();
            }
        }

        if (MarketData.data.get(player.getName() + " " + item) != null) {
            MarketData data = MarketData.data.get(player.getName() + " " + item);

            if (data.isSold()) {
                PlayerData playerData = PlayerData.data.get(player.getUniqueId().toString());
                if (!playerData.addCoins(data.getPrice())) return;
                data.setCollected(true);
                inventory.remove(e.getCurrentItem());
            }
        }
    }

    @Override
    public void setMenuItems() {
        addMenuBorder();
        inventory.setItem(45, ADD_BUTTON);
        insertContents(MarketData.getYourProducts(playerMenuUtility.getOwner()));
    }
}
