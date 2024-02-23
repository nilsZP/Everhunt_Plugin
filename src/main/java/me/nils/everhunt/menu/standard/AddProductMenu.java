package me.nils.everhunt.menu.standard;

import me.nils.everhunt.Everhunt;
import me.nils.everhunt.data.CostNPCData;
import me.nils.everhunt.data.MarketData;
import me.nils.everhunt.data.PlayerData;
import me.nils.everhunt.menu.Menu;
import me.nils.everhunt.menu.PlayerMenuUtility;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
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
            playerMenuUtility.setItem(null);
        }

        if (!inventory.contains(e.getCursor())) {
            inventory.setItem(22,e.getCursor());
            playerMenuUtility.setItem(e.getCursor());
        }

        if (e.getCurrentItem().getType().equals(Material.OAK_SIGN)) {
            if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Set Price")) {
                Inventory search = Bukkit.createInventory(player, InventoryType.ANVIL, "Set Price");
                search.setItem(0,makeItem(Material.PAPER,"Set Price"));
                player.openInventory(search);
                playerMenuUtility.setAnvilGUI("Set Price");
            }
        }

        if (e.getCurrentItem().getType().equals(Material.OAK_BUTTON)) {
            if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Add Product")) {
                if (inventory.getItem(22) == null || inventory.getItem(22).getItemMeta() == null) return;
                String item = ChatColor.stripColor(inventory.getItem(22).getItemMeta().getDisplayName());
                int amount = inventory.getItem(22).getAmount();
                new MarketData(player.getName(),item,amount,price,false,false);
                new SellMenu(Everhunt.getPlayerMenuUtility(player)).open();
                playerMenuUtility.setItem(null);
                player.getInventory().remove(playerMenuUtility.getItem());
            }
        }
    }

    @Override
    public void setMenuItems() {
        inventory.setItem(32,PRICE_SIGN);
        inventory.setItem(49,CLOSE_BUTTON);
        inventory.setItem(30,ADD_BUTTON);
        setFillerGlass();
        if (playerMenuUtility.getItem() == null) {
            inventory.setItem(22,new ItemStack(Material.AIR));
        } else {
            inventory.setItem(22,playerMenuUtility.getItem());
        }
    }
}
