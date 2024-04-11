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

            ItemStack[] ingredients = {inventory.getItem(13),inventory.getItem(21),inventory.getItem(23)};
            for (ItemStack ingredient : ingredients) {
                if (ingredient != null) {
                    if (ingredient.getItemMeta() != null) {
                        String name = ChatColor.stripColor(ingredient.getItemMeta().getDisplayName());
                        if (Condition.isCustom(ItemType.ITEM, name)) {
                            ItemManager item = ItemManager.items.get(name);

                            totalNutrition += item.getNutrition();
                        }
                    }
                }
            }

            totalNutrition /= 10;

            if (inventory.getItem(13) != null) inventory.getItem(13).subtract();
            if (inventory.getItem(21) != null) inventory.getItem(21).subtract();
            if (inventory.getItem(23) != null) inventory.getItem(23).subtract();
            inventory.setItem(31,DishManager.getViaNutrition(totalNutrition));

            playerMenuUtility.getOwner().getInventory().addItem(DishManager.getViaNutrition(totalNutrition));
        }

        if (e.getSlot() == 13 || e.getSlot() == 21 || e.getSlot() == 23) {
            if (e.getCurrentItem() != null) {
                playerMenuUtility.getOwner().getInventory().addItem(e.getCurrentItem());
                inventory.setItem(e.getSlot(),new ItemStack(Material.AIR));
            }
        }
    }

    @Override
    public void setMenuItems() {
        inventory.setItem(49,COOK_BUTTON);
        inventory.setItem(31,RESULT_GLASS);
        setFillerGlass();
        inventory.setItem(13,new ItemStack(Material.AIR));
        inventory.setItem(21,new ItemStack(Material.AIR));
        inventory.setItem(23,new ItemStack(Material.AIR));
    }
}
