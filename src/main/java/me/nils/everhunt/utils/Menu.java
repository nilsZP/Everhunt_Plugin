package me.nils.everhunt.utils;

import me.nils.everhunt.data.BackpackData;
import me.nils.everhunt.data.TeleportData;
import me.nils.everhunt.managers.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class Menu {
    public static Inventory createTeleportMenu(Player player) {
        String uuid = player.getUniqueId().toString();
        ArrayList<String> teleportList = TeleportData.getLocations(uuid);
        int size = teleportList.size();
        while (size%9 != 0) {
            size++;
        }

        ArrayList<ItemStack> menuItems = new ArrayList<>();

        for (String loc : teleportList) {
            menuItems.add(ItemManager.items.get(loc).getItemStack());
        }

        ItemStack[] contents = new ItemStack[menuItems.size()];
        contents = menuItems.toArray(contents);

        Inventory menu = Bukkit.createInventory(player, size, "Teleport Menu");
        menu.setContents(contents);

        return menu;
    }

    public static Inventory createBackpackMenu(Player player) {
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
