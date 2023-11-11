package me.nils.everhunt.commands;

import me.nils.everhunt.data.PlayerData;
import me.nils.everhunt.data.TeleportData;
import me.nils.everhunt.managers.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class WarpCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            return true;
        }

        ArrayList<String> teleportList = TeleportData.getLocations(PlayerData.getPlayerID(player));

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

        player.openInventory(menu);

        return true;
    }
}

