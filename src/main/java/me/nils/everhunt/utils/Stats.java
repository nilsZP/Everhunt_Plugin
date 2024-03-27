package me.nils.everhunt.utils;

import io.papermc.paper.scoreboard.numbers.NumberFormat;
import me.nils.everhunt.Everhunt;
import me.nils.everhunt.constants.Job;
import me.nils.everhunt.data.GuildData;
import me.nils.everhunt.data.JobData;
import me.nils.everhunt.data.PlayerData;
import me.nils.everhunt.data.QuestData;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;
import java.util.List;

public class Stats {
    public static void giveStats(Player player) {
        player.getInventory().setItem(8, createStats(player));
    }

    private static ItemStack createStats(Player player) {
        String uuid = player.getUniqueId().toString();
        PlayerData pData = PlayerData.data.get(uuid);

        int level = pData.getXp() / 100;

        double toughness, damage, health,crit,flow;
        double base = level;

        flow = (level + 1) * 5;

        toughness = (JobData.hasJob(uuid, Job.MINER)) ? base / 10 + JobData.getLevel(uuid,Job.MINER) : base / 10;

        damage = (JobData.hasJob(uuid, Job.HUNTER)) ? base / 5 + JobData.getLevel(uuid,Job.HUNTER) : base / 5;

        health = (JobData.hasJob(uuid, Job.FARMER)) ? 20 + Math.round(base / 5) + JobData.getLevel(uuid,Job.FARMER) : 20 + Math.round(base / 5);

        crit = Math.round(base / 2);

        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health);
        player.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).setBaseValue(toughness);
        player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(damage);
        player.getAttribute(Attribute.GENERIC_LUCK).setBaseValue(crit);
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

        lore.add(Chat.color("&7Crit Chance: &2" + crit));

        lore.add(Chat.color("&7Flow: &3" + flow));

        meta.setLore(lore);

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);

        itemStack.setItemMeta(meta);

        return itemStack;
    }

    public static void setScoreBoard(Player player) {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = manager.getNewScoreboard();

        Objective objective = scoreboard.registerNewObjective("Title", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(ChatColor.BLUE + "Everhunt");

        if (GuildData.getGuild(player) != null) {
            Score guild = objective.getScore(ChatColor.DARK_GREEN + "Guild: " + ChatColor.BOLD + GuildData.getGuild(player));
            Score rank = objective.getScore(ChatColor.DARK_GREEN + "Rank: " + ChatColor.BOLD + GuildData.getRank(player));
            guild.setScore(5);
            guild.numberFormat(NumberFormat.blank());
            rank.setScore(4);
            rank.numberFormat(NumberFormat.blank());
        }

        Score score = objective.getScore(ChatColor.GOLD + "Coins: $" + ChatColor.GREEN + PlayerData.data.get(player.getUniqueId().toString()).getCoins());
        Score score1 = objective.getScore(ChatColor.AQUA + "Current Objective: ");
        Score score2 = objective.getScore(ChatColor.WHITE + QuestData.getCurrentTask(player.getUniqueId().toString()));

        score.setScore(3);
        score.numberFormat(NumberFormat.blank());
        score1.setScore(2);
        score1.numberFormat(NumberFormat.blank());
        score2.setScore(1);
        score2.numberFormat(NumberFormat.blank());

        player.setScoreboard(scoreboard);
    }

    public static int getBreakTime(Block block) {
        int breakTime = 0;
        breakTime += switch (block.getType()) {
            case STONE -> 30;
            case COAL_ORE -> 35;
            case IRON_ORE -> 40;
            case GOLD_ORE,NETHER_GOLD_ORE -> 50;
            case DEEPSLATE_GOLD_ORE -> 60;
            case GOLD_BLOCK -> 80;
            default -> 20;
        };

        return breakTime;
    }

    public static int getBreakModifier(String item) {
        int modifier = 0;
        modifier += switch (item) {
            case "Drill" -> 3;
            case "Drill G16" -> 6;
            case "Drill G29" -> 15;
            case "Drill G42" -> 45;
            default -> 1;
        };

        return modifier;
    }

    public static void addSlowDig(Player player, int duration){
        if(player.hasPotionEffect(PotionEffectType.SLOW_DIGGING)) removeSlowDig(player);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, duration, -1, false, false), true);
    }

    public static void removeSlowDig(Player player){
        player.removePotionEffect(PotionEffectType.SLOW_DIGGING);
    }
}
