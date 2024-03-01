package me.nils.everhunt.menu.standard;

import me.nils.everhunt.Everhunt;
import me.nils.everhunt.data.MarketData;
import me.nils.everhunt.menu.PaginatedMenu;
import me.nils.everhunt.menu.PlayerMenuUtility;
import me.nils.everhunt.utils.Chat;
import me.nils.everhunt.utils.Condition;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class MarketMenu extends PaginatedMenu {
    private boolean result;
    public MarketMenu(PlayerMenuUtility playerMenuUtility, boolean result) {
        super(playerMenuUtility);
        this.result = result;
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
        ArrayList<ItemStack> list = MarketData.getAllProducts();

        handlePages(player, list, e);

        if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Your products")) {
            new SellMenu(Everhunt.getPlayerMenuUtility(player)).open();
        }

        if (e.getCurrentItem().getType().equals(Material.OAK_SIGN)) {
            if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Search")) {
                playerMenuUtility.setInput(true);
                playerMenuUtility.setInputType("Search");
                player.closeInventory();
                Chat.guide(player,"Type your search prompt in chat");
            }
        }

        if (Condition.isCustom(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()))) {
            new ProductMenu(Everhunt.getPlayerMenuUtility(player),e.getCurrentItem()).open();
        }
    }

    @Override
    public void setMenuItems() {
        addMenuBorder();
        inventory.setItem(45,MarketData.getOwnProductsHead(playerMenuUtility.getOwner()));
        inventory.setItem(9,SEARCH_ICON);
        if (result) {
            insertContents(MarketData.getSearchResults(playerMenuUtility.getSearch()));
        } else {
            insertContents(MarketData.getAllProducts());
        }
    }
}
