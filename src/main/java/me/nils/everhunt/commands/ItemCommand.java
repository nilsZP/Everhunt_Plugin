package me.nils.everhunt.commands;

import me.nils.everhunt.Everhunt;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public class ItemCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            return true;
        }

        String param = args[0].toLowerCase();

        int size;

        switch (param) {
            case "weapons" -> {
                size = Everhunt.items.getWeapons().length;
                while (size%9 != 0) {
                    size++;
                }

                Inventory menu = Bukkit.createInventory(player, size, "Admin Items");
                menu.setContents(Everhunt.items.getWeapons());

                player.openInventory(menu);
            }
            case "items" -> {
                size = Everhunt.items.getItems().length;
                while (size%9 != 0) {
                    size++;
                }

                Inventory menu = Bukkit.createInventory(player, size, "Admin Items");
                menu.setContents(Everhunt.items.getItems());

                player.openInventory(menu);
            }
            case "tools" -> {
                size = Everhunt.items.getTools().length;
                while (size%9 != 0) {
                    size++;
                }

                Inventory menu = Bukkit.createInventory(player, size, "Admin Items");
                menu.setContents(Everhunt.items.getTools());

                player.openInventory(menu);
            }
            case "armor" -> {
                size = Everhunt.items.getArmor().length;
                while (size%9 != 0) {
                    size++;
                }

                Inventory menu = Bukkit.createInventory(player, size, "Admin Items");
                menu.setContents(Everhunt.items.getArmor());

                player.openInventory(menu);
            }
            case "helmets" -> {
                size = Everhunt.items.getHelmets().length;
                while (size%9 != 0) {
                    size++;
                }

                Inventory menu = Bukkit.createInventory(player, size, "Admin Items");
                menu.setContents(Everhunt.items.getHelmets());

                player.openInventory(menu);
            }
        }

        return true;
    }
}
