package me.nils.everhunt.commands;

import me.nils.everhunt.Everhunt;
import me.nils.everhunt.constants.ItemType;
import me.nils.everhunt.data.PlayerData;
import me.nils.everhunt.managers.ItemManager;
import me.nils.everhunt.menu.standard.MarketMenu;
import me.nils.everhunt.utils.Condition;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SellCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            return true;
        }

        if (player.getInventory().getItemInMainHand().getItemMeta() != null) {
            if (Condition.isCustom(ItemType.ITEM, ChatColor.stripColor(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName()))) {
                int value = ItemManager.items.get(ChatColor.stripColor(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName())).getValue();
                if (value > 0) {
                    player.getInventory().remove(player.getInventory().getItemInMainHand());
                    PlayerData.data.get(player.getUniqueId().toString()).pay(value);
                }
            }
        }

        return true;
    }
}
