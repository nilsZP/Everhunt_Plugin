package me.nils.everhunt.utils;

import me.nils.everhunt.Everhunt;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;



import java.util.Objects;


public class Flow {
    public static boolean useFlow(int amount, Player player) {
        if (amount > getFlowAmount(player)) {
            return false;
        }
        if (player.getLevel() - amount < 0) {
            return false;
        }
        player.setLevel(player.getLevel() - amount);
        player.setExp(0f);
        return true;
    }

    public static void regenFlow() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    int flowAmount = getFlowAmount(player);
                    if (player.getExp() < 1f) {
                        float manaRegen = player.getExp() + 0.1f + (((flowAmount / 5f) / 100f));
                        if (manaRegen > 1f) {
                            manaRegen = 1f;
                        }
                        player.setExp(manaRegen);
                    }
                    if (player.getExp() >= 1f) {
                        if (player.getLevel()+1 <= flowAmount) {
                            player.setExp(0f);
                            player.giveExpLevels(1);
                        }
                    }
                }
            }
        }.runTaskTimer(Everhunt.getInstance(),20L,20L);
    }

    public static int getFlowAmount(Player player) {
        return (int) player.getAttribute(Attribute.GENERIC_MAX_ABSORPTION).getValue(); // TODO fix not counting modifiers
    }
}
