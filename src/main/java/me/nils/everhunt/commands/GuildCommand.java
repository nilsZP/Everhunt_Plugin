package me.nils.everhunt.commands;

import me.nils.everhunt.Everhunt;
import me.nils.everhunt.data.GuildData;
import me.nils.everhunt.menu.standard.InviteMenu;
import me.nils.everhunt.utils.Chat;
import me.nils.everhunt.utils.Stats;
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
        String param2 = args[1];
        String message = "";

        for (int i = 1; i < args.length; i++) {
            message += args[i] + " ";
        }

        switch (param) {
            case "chat" -> {
                if (!message.isBlank() || !message.isEmpty()) {
                    for (Player receiver : GuildData.getReceivers(player)) {
                        Chat.guild(receiver, player.getName(), message);
                    }
                }
            }
            case "invite" -> {
                if (!param2.isBlank() || !param2.isEmpty()) {
                    if (Bukkit.getPlayer(param2) != null && Bukkit.getPlayer(param2).isOnline()) {
                        Player invited = Bukkit.getPlayer(param2);

                        if (GuildData.getGuild(invited) == null && !GuildData.getGuild(player).equals(GuildData.getGuild(invited))) {
                            new InviteMenu(Everhunt.getPlayerMenuUtility(invited),GuildData.getGuild(player)).open();
                        }
                    }
                }
            }
            case "create" -> {
                if (!param2.isBlank() || !param2.isEmpty()) {
                    if (GuildData.getGuild(player) == null) {
                        new GuildData(player,param2);
                        Stats.setScoreBoard(player);
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
            completions.add("create");
        }

        return completions;
    }
}
