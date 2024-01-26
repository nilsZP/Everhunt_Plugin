package me.nils.everhunt.listeners;

import com.destroystokyo.paper.event.player.PlayerPickupExperienceEvent;
import me.nils.everhunt.Everhunt;
import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.constants.ItemType;
import me.nils.everhunt.constants.Job;
import me.nils.everhunt.data.JobData;
import me.nils.everhunt.data.PlayerData;
import me.nils.everhunt.entities.UndeadScarecrow;
import me.nils.everhunt.entities.loottables.Loot;
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
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.EquipmentSlot;
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
        if (player.getGameMode().equals(GameMode.CREATIVE)) {
            return;
        }

        event.setCancelled(true);

        if (player.getInventory().getItemInMainHand().getItemMeta() == null) return;
        String name = ChatColor.stripColor(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName());

        if (!Condition.isHolding(player, name, ItemType.TOOL)) {
            player.sendMessage(Component.text("Not a tool"));
            event.setCancelled(true);
            return;
        }

        if (Condition.itemNameContains(player,"Drill")) return;

        if (!Condition.itemNameContains(player,"Hoe")) {
            player.sendMessage(Component.text("Wrong tool"));
            event.setCancelled(true);
            return;
        }

        ToolManager tool = ToolManager.items.get(name);
        Ability ability = tool.getAbility();

        if (block.getBlockData() instanceof Ageable ageable && Condition.isFarmeable(block)) {
            if (ageable.getAge() == ageable.getMaximumAge()) {
                String drop = switch (ageable.getMaterial()) {
                    case WHEAT -> "Wheat";
                    case POTATOES -> "Potato";
                    case BEETROOT, BEETROOTS -> "Beetroot";
                    case CARROTS -> "Carrot";
                    default -> "null";
                };

                block.getLocation().getWorld().dropItemNaturally(block.getLocation(),Loot.getlootTable(block,ability).getRandom());
                block.getDrops().clear();
                ageable.setAge(0);
                block.setBlockData(ageable);
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (e.getHand() != EquipmentSlot.HAND) return;
        if (e.getClickedBlock() == null) return;
        if (e.getClickedBlock().getType() != Material.SUSPICIOUS_GRAVEL &&
                e.getClickedBlock().getType() != Material.SUSPICIOUS_SAND) return;
        if (e.getPlayer().getInventory().getItemInMainHand().getType() != Material.BRUSH) return;

        // archeology system
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
