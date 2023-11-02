package me.nils.everhunt.commands;

import me.nils.everhunt.items.Weapons;
import me.nils.everhunt.items.armor.MechanicalChestplate;
import me.nils.everhunt.items.armor.SpringerBoots;
import me.nils.everhunt.items.armor.UnitedHelmet;
import me.nils.everhunt.items.tools.WheatHoe;
import me.nils.everhunt.items.weapons.*;
import me.nils.everhunt.utils.Chat;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ItemCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            return true;
        }

        if (args.length == 0) {
            return true;
        }

        String param = args[0].toLowerCase();

        if ("get".equals(param)) {

            Inventory menu = Bukkit.createInventory(player, Weapons.getAll().length, "Admin Weapons");
            menu.setContents(Weapons.getAll());

            player.openInventory(menu);
        }
        return true;
    }
}
