package me.nils.vdvcraftrevamp.commands;

import me.nils.vdvcraftrevamp.entities.SkeletonKnight;
import me.nils.vdvcraftrevamp.entities.bosses.ThunderBones;
import me.nils.vdvcraftrevamp.utils.Chat;
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
            case "skeletonknight" -> {
                new SkeletonKnight(player.getLocation());
                return true;
            }
            case "thunderbones" -> {
                new ThunderBones(player.getLocation());
                return true;
            }
            default -> {
                player.sendMessage(Component.text(Chat.color("&cUse: /spawn <name>")));
                return true;
            }
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            return new ArrayList<>();
        }

        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            completions.add("SkeletonKnight");
            completions.add("ThunderBones");
        }

        return completions;
    }
}
