package me.nils.vdvcraftrevamp.commands;

import me.nils.vdvcraftrevamp.items.materials.Materials;
import me.nils.vdvcraftrevamp.items.weapons.Weapons;
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

            switch (name) {
                case "meteorblade" -> {
                    ItemStack meteorBlade = new Weapons().MeteorBlade();
                    getItem(meteorBlade, player);
                    return true;
                }
                case "azurewrath" -> {
                    ItemStack azureWrath = new Weapons().AzureWrath();
                    getItem(azureWrath,player);
                    return true;
                }
                case "shatteredshard" -> {
                    ItemStack shard = new Materials().ShatteredShard();
                    getItem(shard,player);
                    return true;
                }
                case "unisonshard" -> {
                    ItemStack shard = new Materials().UnisonShard();
                    getItem(shard,player);
                    return true;
                }
                case "rippeddimension" -> {
                    ItemStack rippedDimension = new Materials().RippedDimension();
                    getItem(rippedDimension,player);
                    return true;
                }
                case "dagger" -> {
                    ItemStack dagger = new Weapons().Dagger();
                    getItem(dagger,player);
                    return true;
                }
                default -> {
                    player.sendMessage(Component.text(Chat.color("&cUse: /item get <name>")));
                    return true;
                }
            }
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
                    completions.add("ShatteredShard");
                    completions.add("UnisonShard");
                    completions.add("RippedDimension");
                    completions.add("Dagger");
                }
            }
        }

        return completions;
    }
}
