package me.nils.everhunt.utils;

import me.nils.everhunt.data.CostNPCData;
import me.nils.everhunt.data.TeleportData;
import me.nils.everhunt.managers.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Menu {
    private final ItemStack sell = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
    private final ItemMeta sellMeta = sell.getItemMeta();
    private final ItemStack air = new ItemStack(Material.AIR);

    private final ItemStack pane = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
    private final ItemMeta paneMeta = pane.getItemMeta();

    private final ItemStack cook = new ItemStack(Material.RED_STAINED_GLASS_PANE);
    private final ItemMeta cookMeta = cook.getItemMeta();
    private final ItemStack result = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
    private final ItemMeta resultMeta = result.getItemMeta();

    public Menu() {
        sellMeta.setDisplayName("Sell");
        sellMeta.setLocalizedName("sell");
        sell.setItemMeta(sellMeta);
        paneMeta.setDisplayName("");
        paneMeta.setLocalizedName("pane");
        pane.setItemMeta(paneMeta);
        cookMeta.setDisplayName("Cook");
        cookMeta.setLocalizedName("cook");
        cook.setItemMeta(cookMeta);
        resultMeta.setDisplayName("Result");
        resultMeta.setLocalizedName("result");
        result.setItemMeta(resultMeta);
    }
    public Inventory teleport(Player player) {
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

    public Inventory cook(Player player) {
        ItemStack[][] items = {
                {pane,pane,pane,pane,pane,pane,pane,pane,pane},
                {pane,pane,pane,pane,air,pane,pane,pane,pane},
                {pane,pane,pane,air,result,air,pane,pane,pane},
                {pane,pane,pane,pane,pane,pane,pane,pane,pane},
                {pane,pane,pane,pane,cook,pane,pane,pane,pane}
        };

        ArrayList<ItemStack> menuItems = new ArrayList<>();
        for (ItemStack[] itemStacks : items) {
            menuItems.addAll(Arrays.asList(itemStacks));
        }

        ItemStack[] contents = new ItemStack[menuItems.size()];
        contents = menuItems.toArray(contents);

        Inventory menu = Bukkit.createInventory(player, 45, "Cook Menu");
        menu.setContents(contents);

        return menu;
    }

    public Inventory sell(Player player, ItemStack... selling) {
        String uuid = player.getUniqueId().toString();
        ArrayList<ItemStack> menuItems = new ArrayList<>();
        List<ItemStack> sellingList = Arrays.stream(selling).toList();

        ItemStack[][] items = {
                {pane,pane,pane,pane,pane,pane,pane,pane,pane},
                {pane,sellingList.get(0),sellingList.get(1),sellingList.get(2),sellingList.get(3),sellingList.get(4),sellingList.get(5),sellingList.get(6),pane},
                {pane,sellingList.get(7),sellingList.get(8),sellingList.get(9),sellingList.get(10),sellingList.get(11),sellingList.get(12),sellingList.get(13),pane},
                {pane,sellingList.get(14),sellingList.get(15),sellingList.get(16),sellingList.get(17),sellingList.get(18),sellingList.get(19),sellingList.get(20),pane},
                {pane,pane,pane,pane,sell,pane,pane,pane,pane}
        };

        for (int i = 0; i <= items.length; i++) {
            for (int i2 = 0; i2 <= items[i].length; i2++) {
                ItemStack itemStack = items[i][i2];
                if (itemStack == null) {
                    itemStack = air;
                }

                ItemMeta meta = itemStack.getItemMeta();
                if (meta != null) {
                    if (CostNPCData.data.get(ChatColor.stripColor(meta.getDisplayName())) != null) {
                        int cost = CostNPCData.data.get(ChatColor.stripColor(meta.getDisplayName())).getCost();

                        List<String> lore = meta.getLore();
                        lore.add(Chat.color("&7Cost: &e" + cost));
                    }
                }
            }
        }

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
