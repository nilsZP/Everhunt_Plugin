package me.nils.everhunt.commands;

import me.nils.everhunt.entities.npc.*;
import me.nils.everhunt.utils.Chat;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SpawnCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            return true;
        }

        String param = args[0].toLowerCase();

        switch (param) {
            case "oldmandave" -> {
                new OldManDave(player.getLocation());
            }
            case "marcus" -> {
                new Marcus(player.getLocation());
            }
            case "mi" -> {
                new Mi(player.getLocation());
            }
            case "mikull" -> {
                new Mikull(player.getLocation());
            }
            case "hunter" -> {
                new Hunter(player.getLocation());
            }
            case "guildmaster" -> {
                new GuildMaster(player.getLocation());
            }
            case "farmer" -> {
                new Farmer(player.getLocation());
            }
            case "tim" -> {
                new Tim(player.getLocation());
            }
            case "compresser" -> {
                new Compresser(player.getLocation());
            }
            case "crateshop" -> {
                new CrateShop(player.getLocation());
            }
            default -> {
                player.sendMessage(Component.text(Chat.color("&cUse: /spawn <name>")));
            }
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            return new ArrayList<>();
        }

        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            completions.add("OldManDave");
            completions.add("Mi");
            completions.add("Mikull");
            completions.add("Marcus");
            completions.add("Hunter");
            completions.add("GuildMaster");
            completions.add("Farmer");
            completions.add("Tim");
            completions.add("CrateShop");
        }

        return completions;
    }
}
