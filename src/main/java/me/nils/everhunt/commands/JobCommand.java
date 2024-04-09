package me.nils.everhunt.commands;

import me.nils.everhunt.Everhunt;
import me.nils.everhunt.menu.standard.AchievementMenu;
import me.nils.everhunt.menu.standard.JobMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class JobCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            return true;
        }

        new JobMenu(Everhunt.getPlayerMenuUtility(player)).open();

        return true;
    }
}
