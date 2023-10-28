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

        String uuid = player.getUniqueId().toString();

        int xp = PlayerData.data.get(uuid).getXp();
        int level = xp / 100;
        this.flowAmount = (level + 1) * 5;

        data.put(player,this);
    }

    public void useFlow(int amount) {
        if (amount > flowAmount) {
            return;
        }
        player.setLevel(flowAmount - amount);
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
                        player.setExp(manaRegen);
                    }
                    if (player.getExp() >= 1f) {
                        player.setExp(0f);
                        if (player.getLevel()+1 <= flowAmount) {
                            player.giveExpLevels(1);
                        }
                    }
                }
            }
        }.runTaskTimer(Everhunt.getInstance(),20L,20L);
    }

    public int getFlowAmount() {
        return flowAmount;
    }
}
