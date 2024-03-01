package me.nils.everhunt.utils;

import me.nils.everhunt.data.BackpackData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class Backpack {

    public static Inventory Menu(Player player) {
        String uuid = player.getUniqueId().toString();
        int size = 54;
        Inventory menu = Bukkit.createInventory(player, size, "Backpack");
        if (BackpackData.getContents(uuid) != null) {
            ArrayList<ItemStack> itemList = BackpackData.getContents(uuid);


            ItemStack[] contents = new ItemStack[itemList.size()];
            contents = itemList.toArray(contents);


            menu.setContents(contents);
        }

        return menu;
    }
}
