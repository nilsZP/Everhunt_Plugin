package me.nils.everhunt.utils;

import me.nils.everhunt.data.PlayerData;
import me.nils.everhunt.data.TeleportData;
import me.nils.everhunt.managers.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;

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

    public static Inventory createSellMenu(Player player) {
        String uuid = player.getUniqueId().toString();
        ArrayList<ItemStack> menuItems = new ArrayList<>();

        ItemStack sell = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
        ItemMeta sellMeta = sell.getItemMeta();
        sellMeta.setDisplayName("Sell");
        sellMeta.setLocalizedName("sell");
        sell.setItemMeta(sellMeta);

        ItemStack air = new ItemStack(Material.AIR);

        ItemStack pane = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta paneMeta = pane.getItemMeta();
        paneMeta.setDisplayName("");
        paneMeta.setLocalizedName("pane");
        pane.setItemMeta(paneMeta);
        
        ItemStack[][] items = {
                {pane,pane,pane,pane,pane,pane,pane,pane,pane},
                {pane,air,air,air,air,air,air,air,pane},
                {pane,air,air,air,air,air,air,air,pane},
                {pane,air,air,air,air,air,air,air,pane},
                {pane,pane,pane,pane,sell,pane,pane,pane,pane}
        };

        for (ItemStack[] itemStacks : items) {
            menuItems.addAll(Arrays.asList(itemStacks));
        }

        ItemStack[] contents = new ItemStack[menuItems.size()];
        contents = menuItems.toArray(contents);

        Inventory menu = Bukkit.createInventory(player, 45, "Sell Menu");
        menu.setContents(contents);

        return menu;
    }
}
