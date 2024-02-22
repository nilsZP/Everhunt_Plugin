package me.nils.everhunt.menu.standard;

import me.nils.everhunt.constants.ItemType;
import me.nils.everhunt.managers.DishManager;
import me.nils.everhunt.managers.ItemManager;
import me.nils.everhunt.menu.Menu;
import me.nils.everhunt.menu.PlayerMenuUtility;
import me.nils.everhunt.utils.Condition;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CookMenu extends Menu {
    public CookMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "Cook Menu";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        if (e.getCurrentItem().getType().equals(Material.RED_STAINED_GLASS_PANE)) {
            int totalNutrition = 0;

            ItemStack[] ingredients = {inventory.getItem(13),inventory.getItem(21),inventory.getItem(32)};
            for (ItemStack ingredient : ingredients) {
                String name = ChatColor.stripColor(ingredient.displayName().toString());
                if (Condition.isCustom(ItemType.ITEM,name)) {
                    ItemManager item = ItemManager.items.get(name);

                    totalNutrition += item.getNutrition();
                }
            }

            totalNutrition /= 10;

            inventory.setItem(13,new ItemStack(Material.AIR));
            inventory.setItem(21,new ItemStack(Material.AIR));
            inventory.setItem(23,new ItemStack(Material.AIR));
            inventory.setItem(32,DishManager.getViaNutrition(totalNutrition));
        } else if (!inventory.contains(e.getCurrentItem())) {
            if (inventory.getItem(13).isEmpty()) {
                inventory.setItem(13,e.getCurrentItem());
            } else if (inventory.getItem(21).isEmpty()) {
                inventory.setItem(21,e.getCurrentItem());
            } else if (inventory.getItem(32).isEmpty()) {
                inventory.setItem(32,e.getCurrentItem());
            }
        }

        if (Condition.isCustom(ChatColor.stripColor(e.getCurrentItem().displayName().toString())) && inventory.contains(e.getCurrentItem())) {
            playerMenuUtility.getOwner().getInventory().addItem(e.getCurrentItem());
            inventory.remove(e.getCurrentItem());
        }
    }

    @Override
    public void setMenuItems() {
        inventory.setItem(13,new ItemStack(Material.AIR));
        inventory.setItem(21,new ItemStack(Material.AIR));
        inventory.setItem(23,new ItemStack(Material.AIR));
        inventory.setItem(50,COOK_BUTTON);
        inventory.setItem(32,RESULT_GLASS);
        setFillerGlass();
    }
}
