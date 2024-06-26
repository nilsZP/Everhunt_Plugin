package me.nils.everhunt.menu.standard;

import me.nils.everhunt.Everhunt;
import me.nils.everhunt.data.MarketData;
import me.nils.everhunt.menu.Menu;
import me.nils.everhunt.menu.PlayerMenuUtility;
import me.nils.everhunt.utils.Chat;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class AddProductMenu extends Menu {
    private int price;
    public AddProductMenu(PlayerMenuUtility playerMenuUtility, int price) {
        super(playerMenuUtility);
        this.price = price;
    }

    @Override
    public String getMenuName() {
        return "Add Product";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();

        if (e.getCurrentItem().getType().equals(Material.BARRIER)) {
            if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Close")) player.closeInventory();
            playerMenuUtility.setItemStack(inventory.getItem(22));
            return;
        }

        if (e.getCurrentItem().getType().equals(Material.OAK_SIGN)) {
            if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Set Price")) {
                playerMenuUtility.setInput(true);
                playerMenuUtility.setInputType("Set Price");
                playerMenuUtility.setItemStack(inventory.getItem(22));
                player.closeInventory();
                Chat.guide(player,"Type your price in chat");

                return;
            }
        }

        if (e.getCurrentItem().getType().equals(Material.OAK_BUTTON)) {
            if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Add Product")) {
                if (inventory.getItem(22) == null || inventory.getItem(22).getItemMeta() == null) return;
                String item = ChatColor.stripColor(inventory.getItem(22).getItemMeta().getDisplayName());
                int amount = inventory.getItem(22).getAmount();
                new MarketData(player.getName(),item,amount,price,false,false);
                new SellMenu(Everhunt.getPlayerMenuUtility(player)).open();
                playerMenuUtility.setItemStack(null);

                return;
            }
        }

        if (e.getSlot() == 22) {
            if (inventory.getItem(22) != null) {
                player.getInventory().addItem(inventory.getItem(22));
                inventory.setItem(22,new ItemStack(Material.AIR));
                return;
            }
        }
    }

    @Override
    public void setMenuItems() {
        inventory.setItem(32,PRICE_SIGN);
        inventory.setItem(49,CLOSE_BUTTON);
        inventory.setItem(30,ADD_BUTTON);
        setFillerGlass();
        if (playerMenuUtility.getItemStack() == null) {
            inventory.setItem(22,new ItemStack(Material.AIR));
        } else {
            inventory.setItem(22,playerMenuUtility.getItemStack());
        }
    }
}
