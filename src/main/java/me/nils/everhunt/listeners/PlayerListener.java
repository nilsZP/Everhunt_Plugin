package me.nils.everhunt.listeners;

import com.destroystokyo.paper.event.player.PlayerPickupExperienceEvent;
import me.nils.everhunt.Everhunt;
import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.constants.ItemType;
import me.nils.everhunt.constants.Job;
import me.nils.everhunt.data.JobData;
import me.nils.everhunt.data.PlayerData;
import me.nils.everhunt.entities.UndeadScarecrow;
import me.nils.everhunt.managers.ItemManager;
import me.nils.everhunt.managers.ToolManager;
import me.nils.everhunt.constants.MobType;
import me.nils.everhunt.data.EntityData;
import me.nils.everhunt.utils.Condition;
import me.nils.everhunt.utils.Drop;
import me.nils.everhunt.utils.Stats;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.joinMessage(Component.text(ChatColor.translateAlternateColorCodes('&', "&a" + player.getName() + " &fhas joined the server!")));
        new PlayerData(player.getUniqueId().toString(), player.getName(), 0, 0);
        int level = PlayerData.data.get(player.getUniqueId().toString()).getXp() / 100;
        player.setPlayerListName(String.format("[%d] %s", level, player.getName()));

        Stats.giveStats(player);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        event.quitMessage(Component.text(ChatColor.translateAlternateColorCodes('&', "&c" + player.getName() + " &fhas left the server!")));
    }

    @EventHandler
    public void onHunger(FoodLevelChangeEvent event) {
        if (event.getEntity() instanceof Player player) {
            event.setCancelled(true);
            player.setFoodLevel(20);
        }
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player player) {
            if (event.getEntity() instanceof LivingEntity livingEntity) {
                EntityData data = EntityData.data.get(livingEntity.getName());

                if (data != null) {
                    if (data.getType() != MobType.NPC) {
                        double damage = event.getDamage();
                        int luck = (int) player.getAttribute(Attribute.GENERIC_MAX_ABSORPTION).getValue();
                        Random random = new Random();
                        int randInt = random.nextInt(0, 101);
                        if (randInt <= luck) {
                            event.setDamage(damage * 1.5);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        String name = ChatColor.stripColor(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName());
        if (player.getGameMode().equals(GameMode.CREATIVE)) {
            return;
        }
        if (!Condition.isHolding(player, name, ItemType.TOOL)) {
            player.sendMessage(Component.text("Not a tool"));
            event.setCancelled(true);
            return;
        }

        if (!Condition.itemNameContains(player,"Hoe")) {
            player.sendMessage(Component.text("Wrong tool"));
            event.setCancelled(true);
            return;
        }

        ToolManager tool = ToolManager.items.get(name);
        Ability ability = tool.getAbility();

        if (block.getBlockData() instanceof Ageable ageable) {
            if (ageable.getAge() == ageable.getMaximumAge()) {
                String drop = switch (ageable.getMaterial()) {
                    case WHEAT -> "Wheat";
                    case POTATOES -> "Potato";
                    case BEETROOT, BEETROOTS -> "Beetroot";
                    case CARROTS -> "Carrot";
                    default -> "null";
                };

                Drop.getCropDrops(drop, ability, player, block, ageable);
                block.getDrops().clear();
            }
        }
        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockDamage(BlockDamageEvent event) {
        Player player = event.getPlayer();
        String uuid = player.getUniqueId().toString();
        String name = ChatColor.stripColor(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName());
        Block block = event.getBlock();
        Material type = block.getType();

        if (!Condition.isHolding(player, name, ItemType.TOOL)) {
            player.sendMessage(Component.text("Not a tool"));
            event.setCancelled(true);
            return;
        }

        if (!Condition.itemNameContains(player,"Drill")) {
            player.sendMessage(Component.text("Wrong tool"));
            event.setCancelled(true);
            return;
        }

        if (block.getType() == Material.STONE || block.getType() == Material.COAL_ORE || block.getType() == Material.IRON_ORE) {
            double customBreakingSpeed = (JobData.hasJob(uuid, Job.MINER)) ? JobData.getLevel(uuid, Job.MINER) * 0.10 + 1 : 1;

            event.setCancelled(true);
            Ability ability = ToolManager.items.get(ChatColor.stripColor(player.getInventory().getItemInMainHand().displayName().toString())).getAbility();

            double hardness = getBlockHardness(block.getType());
            int originalTime = (int) (hardness * 1.5 * 20);
            int modifiedTime = (int) (originalTime / customBreakingSpeed);

            new BukkitRunnable() {
                int progress = 0;

                @Override
                public void run() {
                    if (progress >= 10) {
                        cancel();
                        Drop.getBlockDrops(ability,player,block);
                        player.sendBlockDamage(block.getLocation(),0);
                        block.getDrops().clear();
                        block.setType(Material.BEDROCK);
                        player.sendBlockChange(block.getLocation(), Material.BEDROCK.createBlockData());
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                block.setType(type);
                                player.sendBlockChange(block.getLocation(), block.getBlockData());
                            }
                        }.runTaskLater(Everhunt.getInstance(), 100);
                    }

                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,modifiedTime,254,false,false));
                    player.playEffect(block.getLocation(), Effect.STEP_SOUND, block.getType());
                    player.sendBlockDamage(block.getLocation(), (float) progress / 10);

                    progress++;
                }
            }.runTaskTimer(Everhunt.getInstance(), 0, modifiedTime / 10);
        }
        event.setCancelled(true);
    }

    private double getBlockHardness(Material material) {
        double hardness = switch (material) {
            case STONE -> 1.5;
            case COAL_ORE -> 2;
            case IRON_ORE -> 3;
            default -> 1.0;
        };

        return hardness;
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode().equals(GameMode.CREATIVE)) {
            return;
        }
        event.setCancelled(true);
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onXpGain(PlayerPickupExperienceEvent event) {
        event.getExperienceOrb().remove();
        event.setCancelled(true);
    }

    public static void checkMonsterSpawn() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (!player.getWorld().isDayTime()) {
                        List<Block> blocks = getNearbyBlocks(player.getLocation(), 8);
                        for (Block block : blocks) {
                            Location loc = block.getLocation();
                            Block block1 = loc.getWorld().getBlockAt(loc.getBlockX(), loc.getBlockY() + 1, loc.getBlockZ());
                            Block block2 = loc.getWorld().getBlockAt(loc.getBlockX(), loc.getBlockY() + 2, loc.getBlockZ());

                            if (block1.isEmpty() && block2.isEmpty()) {
                                Random random = new Random();
                                int randInt = random.nextInt(0, 11);

                                if (randInt == 1) {
                                    if (block1.getLightLevel() <= 6) {
                                        if (block.getType() == Material.WHEAT) {
                                            new UndeadScarecrow(loc);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }.runTaskTimer(Everhunt.getInstance(), 300L, 300L);
    }

    public static List<Block> getNearbyBlocks(Location location, int radius) {
        List<Block> blocks = new ArrayList<Block>();
        for (int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) {
            for (int y = location.getBlockY() - radius; y <= location.getBlockY() + radius; y++) {
                for (int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
                    blocks.add(location.getWorld().getBlockAt(x, y, z));
                }
            }
        }
        return blocks;
    }
}
