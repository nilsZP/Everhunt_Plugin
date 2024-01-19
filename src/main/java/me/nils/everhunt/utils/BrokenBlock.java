package me.nils.everhunt.utils;

import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.managers.ToolManager;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Date;

public class BrokenBlock {

    private int time;
    private int oldAnimation;
    private double damage = -1;
    private Block block;
    private Date lastDamage;

    public BrokenBlock(Block block, int time){
        this.block = block;
        this.time = time;
        lastDamage = new Date();
    }

    public void incrementDamage(Player from, double multiplier){
        if(isBroken()) return;

        damage += multiplier;
        int animation = getAnimation();

        if(animation != oldAnimation){
            if(animation < 10){
                sendBreakPacket(from,animation);
                lastDamage = new Date();
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
        Ability ability = ToolManager.items.get(ChatColor.stripColor(breaker.getInventory().getItemInMainHand().displayName().toString())).getAbility();
        breaker.playEffect(block.getLocation(), Effect.STEP_SOUND, block.getType());
        if(breaker == null) return;
        breaker.breakBlock(block);
        Drop.getBlockDrops(ability,breaker,block);
    }

    public void destroyBlockObject(Player player){
        sendBreakPacket(player,-1);
    }

    public int getAnimation(){
        return (int) (damage / time*11) - 1;
    }

    public void sendBreakPacket(Player player, int animation){
        player.sendBlockDamage(block.getLocation(),animation);
    }
}