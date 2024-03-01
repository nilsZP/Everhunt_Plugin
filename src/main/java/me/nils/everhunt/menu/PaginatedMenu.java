package me.nils.everhunt.menu;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public abstract class PaginatedMenu extends Menu {
    protected int page = 0;
    protected int maxItemsPerPage = 28;
    protected int index = 0;

    public PaginatedMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    public void addMenuBorder(){
        inventory.setItem(48, makeItem(Material.SPECTRAL_ARROW, ChatColor.YELLOW + "Left"));

        inventory.setItem(49, CLOSE_BUTTON);

        inventory.setItem(50, makeItem(Material.SPECTRAL_ARROW, ChatColor.YELLOW + "Right"));

        for (int i = 0; i < 10; i++) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, super.FILLER_GLASS);
            }
        }

        inventory.setItem(17, super.FILLER_GLASS);
        inventory.setItem(18, super.FILLER_GLASS);
        inventory.setItem(26, super.FILLER_GLASS);
        inventory.setItem(27, super.FILLER_GLASS);
        inventory.setItem(35, super.FILLER_GLASS);
        inventory.setItem(36, super.FILLER_GLASS);

        for (int i = 44; i < 54; i++) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, super.FILLER_GLASS);
            }
        }
    }

    public int getMaxItemsPerPage() {
        return maxItemsPerPage;
    }

    public void insertContents(ArrayList<ItemStack> list) {
        if(list != null && !list.isEmpty()) {
            for(int i = 0; i < getMaxItemsPerPage(); i++) {
                index = getMaxItemsPerPage() * page + i;
                if(index >= list.size()) break;
                if (list.get(index) != null){
                    inventory.addItem(list.get(index));
                }
            }
        }
    }

    public void handlePages(Player player, ArrayList<ItemStack> list, InventoryClickEvent e) {
        if (e.getCurrentItem().getType().equals(Material.SPECTRAL_ARROW)) {
            if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Left")) {
                if (page == 0) {
                    player.sendMessage(ChatColor.GRAY + "You are already on the first page.");
                } else {
                    page = page - 1;
                    super.open();
                }
            } else if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Right")) {
                if (!((index + 1) >= list.size())) {
                    page = page + 1;
                    super.open();
                } else {
                    player.sendMessage(ChatColor.GRAY + "You are on the last page.");
                }
            }
        } else if (e.getCurrentItem().getType().equals(Material.BARRIER)) {
            if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Close")) player.closeInventory();
        }
    }
}
