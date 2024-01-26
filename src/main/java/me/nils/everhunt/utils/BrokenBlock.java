package me.nils.everhunt.utils;

import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.entities.loottables.Loot;
import me.nils.everhunt.managers.ToolManager;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Date;

public class BrokenBlock {

    private int time;
    private int oldAnimation;
    private double damage = 0;
    private Block block;

    public BrokenBlock(Block block, int time){
        this.block = block;
        this.time = time;
    }

    public void incrementDamage(Player from, double multiplier){
        if(isBroken()) return;

        damage += multiplier;
        int animation = getAnimation();

        if(animation != oldAnimation){
            if(animation < 10){
                sendBreakPacket(from,animation);
            } else {
                breakBlock(from);
                return;
            }
        }

        oldAnimation = animation;
    }

    public boolean isBroken(){
        return getAnimation() >= 10;
    }

    public void breakBlock(Player breaker){
        destroyBlockObject(breaker);
        Ability ability;
        if (ToolManager.items.get(ChatColor.stripColor(breaker.getInventory().getItemInMainHand().getItemMeta().displayName().toString())) != null) {
            ability = ToolManager.items.get(ChatColor.stripColor(breaker.getInventory().getItemInMainHand().getItemMeta().displayName().toString())).getAbility();
        } else {
            ability = Ability.NONE;
        }
        breaker.playEffect(block.getLocation(), Effect.STEP_SOUND, block.getType());
        if(breaker == null) return;
        breaker.breakBlock(block);
        BrokenBlocksService.removeBrokenBLock(block.getLocation());
        block.getLocation().getWorld().dropItemNaturally(block.getLocation(), Loot.getlootTable(block,ability).getRandom());
    }

    public void destroyBlockObject(Player player){
        sendBreakPacket(player,0);
    }

    public int getAnimation(){
        return (int) (damage / time);
    }

    public void sendBreakPacket(Player player, int animation){
        player.sendBlockDamage(block.getLocation(), (float) animation/10);
    }
}
