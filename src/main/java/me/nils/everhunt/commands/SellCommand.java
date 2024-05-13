package me.nils.everhunt.commands;

import me.nils.everhunt.constants.ItemType;
import me.nils.everhunt.data.PlayerData;
import me.nils.everhunt.managers.ItemManager;
import me.nils.everhunt.utils.Condition;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class SellCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            return true;
        }

        String param = (args.length > 0) ? args[0].toLowerCase() : "";

        if (!param.isEmpty() && !param.isBlank()) {
            if (param.equals("all")) {
                if (player.getInventory().getItemInMainHand().getItemMeta() != null) {
                    if (Condition.isCustom(ItemType.ITEM, ChatColor.stripColor(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName()))) {
                        Inventory inventory = player.getInventory();
                        int count = 0;

                        for (ItemStack itemStack : inventory) {
                            if (itemStack != null) {
                                if (itemStack.getItemMeta() != null) {
                                    String itemName = ChatColor.stripColor(itemStack.getItemMeta().getDisplayName());
                                    if (Condition.isCustom(ItemType.ITEM, itemName)) {
                                        if (itemName.equals(ChatColor.stripColor(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName()))) {
                                            if (itemStack.getAmount() == 64) {
                                                count += itemStack.getAmount();
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        int value = ItemManager.items.get(ChatColor.stripColor(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName())).getValue() * count;
                        if (value > 0) {
                            player.getInventory().remove(player.getInventory().getItemInMainHand());
                            PlayerData.data.get(player.getUniqueId().toString()).addCoins(value);
                        }
                    }
                }
            }
        }

        if (player.getInventory().getItemInMainHand().getItemMeta() != null) {
            if (Condition.isCustom(ItemType.ITEM, ChatColor.stripColor(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName()))) {
                int value = ItemManager.items.get(ChatColor.stripColor(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName())).getValue() * player.getInventory().getItemInMainHand().getAmount();
                if (value > 0) {
                    player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                    PlayerData.data.get(player.getUniqueId().toString()).addCoins(value);
                }
            }
        }

        return true;
    }
}
