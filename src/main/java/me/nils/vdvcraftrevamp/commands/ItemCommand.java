package me.nils.vdvcraftrevamp.commands;

import me.nils.vdvcraftrevamp.items.armor.UnitedHelmet;
import me.nils.vdvcraftrevamp.items.weapons.*;
import me.nils.vdvcraftrevamp.managers.ArmorManager;
import me.nils.vdvcraftrevamp.managers.WeaponManager;
import me.nils.vdvcraftrevamp.utils.Chat;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ItemCommand implements CommandExecutor, TabCompleter {

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
            if (args.length == 1) {
                player.sendMessage(Component.text(Chat.color("&cUse: /item get <name>")));
                return true;
            }

            String name = args[1].toLowerCase();
            ItemStack item;
            switch (name) {
                case "meteorblade" -> {
                    item = new MeteorBlade().getItemStack();
                }
                case "azurewrath" -> {
                    item = new AzureWrath().getItemStack();
                }
                case "unitedhelmet" -> {
                    item = new UnitedHelmet().getItemStack();
                }
                case "daggerofshattereddimensions" -> {
                    item = new DaggerOfShatteredDimensions().getItemStack();
                }
                case "daggerofuniteddimensions" -> {
                    item = new DaggerOfUnitedDimensions().getItemStack();
                }
                case "woodenbat" -> {
                    item = new WoodenBat().getItemStack();
                }
                case "snowshovel" -> {
                    item = new SnowShovel().getItemStack();
                }
                case "nixeus" -> {
                    item = new Nixeus().getItemStack();
                }
                default -> {
                    player.sendMessage(Component.text(Chat.color("&cUse: /item get <name>")));
                    return true;
                }
            }
            getItem(item, player);
        }
        return true;
    }

    public void getItem(ItemStack item, Player player) {
        player.getInventory().addItem(item);
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            return new ArrayList<>();
        }

        List<String> completions = new ArrayList<>();

        switch (args.length) {
            case 1 -> {
                completions.add("get");
            }
            case 2 -> {
                if ("get".equalsIgnoreCase(args[0])) {
                    completions.add("MeteorBlade");
                    completions.add("AzureWrath");
                    completions.add("UnitedHelmet");
                    completions.add("DaggerOfShatteredDimensions");
                    completions.add("DaggerOfUnitedDimensions");
                    completions.add("WoodenBat");
                    completions.add("SnowShovel");
                    completions.add("Nixeus");
                }
            }
        }

        return completions;
    }
}
