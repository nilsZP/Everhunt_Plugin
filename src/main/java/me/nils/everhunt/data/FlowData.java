package me.nils.everhunt.data;

import me.nils.everhunt.managers.ToolManager;
import org.bukkit.entity.Player;

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
}
