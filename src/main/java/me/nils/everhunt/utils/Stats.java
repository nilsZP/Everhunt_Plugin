package me.nils.everhunt.utils;

import me.nils.everhunt.Everhunt;
import me.nils.everhunt.data.PlayerData;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Stats {
    public static void giveStats(Player player) {
        player.getInventory().setItem(8, createStats(player));
    }

    private static ItemStack createStats(Player player) {
        PlayerData pData = PlayerData.data.get(player.getUniqueId().toString());

        int level = pData.getXp() / 100;

        double toughness, damage, health,luck,flow;
        double base = level;

        flow = (level + 1) * 5;

        toughness = base / 10;
        damage = base / 5;

        while (base %5 != 0) {
            base--;
        }

        if (base != 0) {
            health = 20 + (base / 5);
        } else {
            health = 20;
        }

        base = level;

        while (base %2 != 0) {
            base--;
        }

        if (base != 0) {
            luck = base / 2;
        } else {
            luck = 0;
        }

        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health);
        player.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).setBaseValue(toughness);
        player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(damage);
        player.getAttribute(Attribute.GENERIC_LUCK).setBaseValue(luck);
        player.getAttribute(Attribute.GENERIC_MAX_ABSORPTION).setBaseValue(flow);

        ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) itemStack.getItemMeta();
        meta.setPlayerProfile(player.getPlayerProfile());
        meta.displayName(Component.text(player.getName()));
        meta.getPersistentDataContainer().set(Everhunt.getKey(), PersistentDataType.STRING, player.getName());
        meta.setUnbreakable(true);

        List<String> lore = new ArrayList<>();

        lore.add(Chat.color("&7Health: &a" + health));

        lore.add(Chat.color("&7Toughness: &8" + toughness));

        lore.add(Chat.color("&7Damage: &4" + damage));

        lore.add(Chat.color("&7Luck: &2" + luck));

        lore.add(Chat.color("&7Flow: &3" + flow));

        meta.setLore(lore);

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);

        itemStack.setItemMeta(meta);

        return itemStack;
    }

    public static int getBreakTime(Block block, String item) {
        int breakTime = 0;
        breakTime += switch (block.getType()) {
            case STONE -> 30;
            default -> 20;
        };

        return breakTime;
    }

    public static void addSlowDig(Player player, int duration){
        if(player.hasPotionEffect(PotionEffectType.SLOW_DIGGING)) removeSlowDig(player);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, duration, -1, false, false), true);
    }

    public static void removeSlowDig(Player player){
        player.removePotionEffect(PotionEffectType.SLOW_DIGGING);
    }
}
