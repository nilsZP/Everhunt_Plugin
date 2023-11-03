package me.nils.everhunt.data;

import me.nils.everhunt.Everhunt;
import me.nils.everhunt.managers.ToolManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public class FlowData {
    public static final HashMap<Player, FlowData> data = new HashMap<>();
    private final Player player;
    private final int flowAmount;

    public FlowData(Player player) {
        this.player = player;
        this.flowAmount = getFlowAmount();

        data.put(player,this);
    }

    public void useFlow(int amount) {
        if (amount > flowAmount) {
            return;
        }
        player.setLevel(player.getLevel() - amount);
        player.setExp(0f);
    }

    public static void regenFlow() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    int flowAmount = FlowData.data.get(player).getFlowAmount();
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

    public int getFlowAmount() {
        int xp = PlayerData.data.get(player.getUniqueId().toString()).getXp();
        int level = xp / 100;
        return (level + 1) * 5;
    }
}
