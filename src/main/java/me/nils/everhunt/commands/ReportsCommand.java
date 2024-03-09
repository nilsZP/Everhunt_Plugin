package me.nils.everhunt.commands;

import me.nils.everhunt.Everhunt;
import me.nils.everhunt.menu.admin.ReportMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ReportsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            return true;
        }

        String user = args[0].toLowerCase();

        if (!user.isEmpty() && !user.isBlank()) {
            new ReportMenu(Everhunt.getPlayerMenuUtility(player),user).open();
        } else {
            new ReportMenu(Everhunt.getPlayerMenuUtility(player),null);
        }

        return true;
    }
}
