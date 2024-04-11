package me.nils.everhunt.menu.standard;

import me.nils.everhunt.Everhunt;
import me.nils.everhunt.data.MarketData;
import me.nils.everhunt.data.PlayerData;
import me.nils.everhunt.menu.Menu;
import me.nils.everhunt.menu.PlayerMenuUtility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class ProductMenu extends Menu {
    private final ItemStack itemStack;
    public ProductMenu(PlayerMenuUtility playerMenuUtility, ItemStack itemStack) {
        super(playerMenuUtility);
        this.itemStack = itemStack;
    }

    @Override
    public String getMenuName() {
        return "Product Menu";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        String seller = itemStack.getItemMeta().getPersistentDataContainer().get(Everhunt.getKey(), PersistentDataType.STRING);
        String item = ChatColor.stripColor(itemStack.getItemMeta().getDisplayName());
        MarketData marketData = MarketData.data.get(ChatColor.stripColor(seller + " " + item));

        if (e.getCurrentItem().getType().equals(Material.BARRIER)) {
            if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Close")) player.closeInventory();
        }

        if (e.getCurrentItem().getType().equals(Material.GOLD_BLOCK)) {
            if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Buy")) {
                PlayerData data = PlayerData.data.get(player.getUniqueId().toString());
                if (marketData.isSold() || marketData.isCollected()) {
                    new ProductMenu(Everhunt.getPlayerMenuUtility(player),itemStack).open();
                } else {
                    if (!data.pay(marketData.getPrice())) return;
                    player.getInventory().addItem(itemStack);
                    marketData.setSold(true);
                    player.closeInventory();
                }
            }
        }


    }

    @Override
    public void setMenuItems() {
        inventory.setItem(22,itemStack);

        String seller = itemStack.getItemMeta().getPersistentDataContainer().get(Everhunt.getKey(), PersistentDataType.STRING);
        String item = itemStack.getItemMeta().getDisplayName();

        if (MarketData.data.get(ChatColor.stripColor(seller + " " + item)).isSold() || MarketData.data.get(ChatColor.stripColor(seller + " " + item)).isCollected()) {
            inventory.setItem(31,SOLD_ICON);
        } else {
            inventory.setItem(31,BUY_BUTTON);
        }

        inventory.setItem(49,CLOSE_BUTTON);
        setFillerGlass();
    }
}
