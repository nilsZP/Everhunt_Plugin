package me.nils.everhunt.commands;

import me.nils.everhunt.data.GuildData;
import me.nils.everhunt.utils.Chat;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class GuildCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            return true;
        }

        String param = args[0].toLowerCase();
        String message = args[1];

        switch (param) {
            case "chat" -> {
                if (!message.isBlank() || !message.isEmpty()) {
                    for (Player receiver : GuildData.getReceivers(player)) {
                        Chat.guild(receiver, player.getName(), message);
                    }
                }
            }
            case "invite" -> {
                if (!message.isBlank() || !message.isEmpty()) {
                    if (Bukkit.getPlayer(message) != null && Bukkit.getPlayer(message).isOnline()) {
                        Player invited = Bukkit.getPlayer(message);

                        if (GuildData.getGuild(invited) != null && GuildData.getGuild(player).equals(GuildData.getGuild(invited))) {
                            // TODO open guild invite menu
                        }
                    }
                }
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
            completions.add("chat");
            completions.add("invite");
        }

        return completions;
    }
}
