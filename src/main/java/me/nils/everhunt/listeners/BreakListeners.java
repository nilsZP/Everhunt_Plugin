package me.nils.everhunt.listeners;

import me.nils.everhunt.Everhunt;
import me.nils.everhunt.constants.ItemType;
import me.nils.everhunt.utils.Condition;
import me.nils.everhunt.utils.Stats;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.player.PlayerAnimationEvent;

import java.util.HashSet;

public class BreakListeners implements Listener {

    private static HashSet<Material> transparentBlocks = new HashSet<>();

    static {
        transparentBlocks.add(Material.WATER);
        transparentBlocks.add(Material.AIR);
    }


    @EventHandler
    public void onBlockBreak(BlockDamageEvent event){
        Player player = event.getPlayer();
        if (player.getInventory().getItemInMainHand().getItemMeta() == null) return;
        String name = ChatColor.stripColor(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName());
        Block block = event.getBlock();

        if (!Condition.isHolding(player, name, ItemType.TOOL)) {
            event.setCancelled(true);
            return;
        }

        if (Condition.itemNameContains(player,"Hoe")) return;

        if (!Condition.itemNameContains(player,"Drill")) {
            event.setCancelled(true);
            return;
        }

        if (Condition.canMine(name,block)) {
            event.setCancelled(true);
            if (Condition.itemNameContains(player,"G")) {
                if (!Condition.has(player,"Coal",ItemType.ITEM,1)) return;
            }
            Everhunt.brokenBlocksService.createBrokenBlock(event.getBlock(), Stats.getBreakTime(block));
        }
    }

    @EventHandler
    public void onBreakingBlock(PlayerAnimationEvent e){
        Player player = e.getPlayer();
        if (player.getInventory().getItemInMainHand().getItemMeta() == null) return;
        String name = ChatColor.stripColor(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName());

        Block block = player.getTargetBlock(transparentBlocks, 5);
        Location blockPosition = block.getLocation();

        if(!Everhunt.brokenBlocksService.isBrokenBlock(blockPosition)) return;

        double distanceX = blockPosition.getX() - player.getLocation().getX();
        double distanceY = blockPosition.getY() - player.getLocation().getY();
        double distanceZ = blockPosition.getZ() - player.getLocation().getZ();

        if(distanceX * distanceX + distanceY * distanceY + distanceZ * distanceZ >= 1024.0D) return;
        Stats.addSlowDig(e.getPlayer(), 200);
        Everhunt.brokenBlocksService.getBrokenBlock(blockPosition).incrementDamage(player, Stats.getBreakModifier(name));
    }
}
