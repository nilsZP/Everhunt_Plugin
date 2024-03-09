package me.nils.everhunt.commands;

import me.nils.everhunt.Everhunt;
import me.nils.everhunt.data.GuildData;
import me.nils.everhunt.data.ReportData;
import me.nils.everhunt.menu.standard.InviteMenu;
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

public class ReportCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            return true;
        }

        String user = args[0];
        String report = args[1];

        new ReportData(player,user,report);

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            return new ArrayList<>();
        }

        List<String> completions = new ArrayList<>();

        if (args.length == 2) {
            completions.add("Hacking");
            completions.add("Scamming");
            completions.add("Abuse");
            completions.add("IRLtrading");
            completions.add("Bullying");
        }

        return completions;
    }
}
