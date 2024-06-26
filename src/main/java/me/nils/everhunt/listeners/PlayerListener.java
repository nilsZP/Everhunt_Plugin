package me.nils.everhunt.listeners;

import com.destroystokyo.paper.event.player.PlayerPickupExperienceEvent;
import me.nils.everhunt.Everhunt;
import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.constants.ItemType;
import me.nils.everhunt.constants.MobType;
import me.nils.everhunt.data.EntityData;
import me.nils.everhunt.data.PlayerData;
import me.nils.everhunt.entities.loottables.Loot;
import me.nils.everhunt.managers.ArmorManager;
import me.nils.everhunt.managers.HelmetManager;
import me.nils.everhunt.managers.ToolManager;
import me.nils.everhunt.menu.standard.CookMenu;
import me.nils.everhunt.menu.standard.WeaponCreationMenu;
import me.nils.everhunt.utils.Condition;
import me.nils.everhunt.utils.Stats;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.Brushable;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.*;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

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
        Stats.setScoreBoard(player);
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
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            event.setCancelled(true);
            return;
        }

        if (event.getDamager() instanceof Player player) {
            if (event.getEntity() instanceof LivingEntity livingEntity) {
                if (livingEntity.getPersistentDataContainer().has(Everhunt.getKey())) {
                    String name = livingEntity.getPersistentDataContainer().get(Everhunt.getKey(), PersistentDataType.STRING);
                    EntityData data = EntityData.data.get(name);

                    if (data != null) {
                        if (data.getType() != MobType.NPC) {
                            double damage = event.getDamage();
                            int luck = (int) player.getAttribute(Attribute.GENERIC_LUCK).getValue();
                            Random random = new Random();
                            int randInt = random.nextInt(0, 101);
                            if (randInt <= luck) {
                                event.setDamage(damage * 1.5);
                                player.getWorld().playEffect(livingEntity.getLocation(), Effect.ELECTRIC_SPARK, 2);
                            }
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
            event.setCancelled(true);
            return;
        }

        if (Condition.itemNameContains(player, "Drill")) return;

        if (!Condition.itemNameContains(player, "Hoe")) {
            event.setCancelled(true);
            return;
        }

        ToolManager tool = ToolManager.items.get(name);
        Ability ability = tool.getAbility();
        Ability ability2 = null;

        if (player.getInventory().getHelmet() != null) {
            if (Condition.isCustom(ChatColor.stripColor(player.getInventory().getHelmet().getItemMeta().getDisplayName()))) {
                String item = ChatColor.stripColor(player.getInventory().getHelmet().getItemMeta().getDisplayName());
                if (Condition.isCustom(ItemType.HELMET, item)) {
                    ability2 = HelmetManager.items.get(item).getAbility();
                }
                if (Condition.isCustom(ItemType.ARMOR, item)) {
                    ability2 = ArmorManager.items.get(item).getAbility();
                }
            }
        }

        if (block.getBlockData() instanceof Ageable ageable && Condition.isFarmeable(block)) {
            if (ageable.getAge() == ageable.getMaximumAge()) {
                if (ability2 != null)
                    block.getLocation().getWorld().dropItemNaturally(block.getLocation(), Loot.getlootTable(block, ability, ability2).getRandom());
                else
                    block.getLocation().getWorld().dropItemNaturally(block.getLocation(), Loot.getlootTable(block, ability).getRandom());
                block.getDrops().clear();
                ageable.setAge(0);
                block.setBlockData(ageable);
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Block block = e.getClickedBlock();

        if(e.getAction() == Action.PHYSICAL) {

            if(block == null) return;

            if(block.getType() == Material.FARMLAND) {
                e.setCancelled(true);
            }
        }

        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (e.getClickedBlock() == null) return;

        if (e.getClickedBlock().getType() == Material.SOUL_CAMPFIRE) {
            new CookMenu(Everhunt.getPlayerMenuUtility(e.getPlayer())).open();
        } else if (e.getClickedBlock().getType() == Material.LAVA_CAULDRON) {
            new WeaponCreationMenu(Everhunt.getPlayerMenuUtility(e.getPlayer()),"").open();
        }

        if (block.getType() != Material.SUSPICIOUS_GRAVEL && block.getType() != Material.SUSPICIOUS_SAND) return;
        if (e.getPlayer().getInventory().getItemInMainHand().getType() != Material.BRUSH) return;

        // archeology system
        if (block.getBlockData() instanceof Brushable) {
            block.getLocation().getWorld().dropItemNaturally(block.getLocation(), Loot.getLootTable().getRandom());
            if (block.getType() == Material.SUSPICIOUS_GRAVEL) {
                block.setType(Material.GRAVEL);
            } else {
                block.setType(Material.SAND);
            }
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (block.getType().equals(Material.GRAVEL)) {
                        block.setType(Material.SUSPICIOUS_GRAVEL);
                    } else {
                        block.setType(Material.SUSPICIOUS_SAND);
                    }
                }
            }.runTaskLater(Everhunt.getInstance(), 200);
        }
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
    public void onXpGain(PlayerPickupExperienceEvent event) {
        event.getExperienceOrb().remove();
        event.setCancelled(true);
    }

    @EventHandler
    public void onPortalEnter(PlayerPortalEvent e) {
        if (e.getCause() == PlayerTeleportEvent.TeleportCause.END_PORTAL) {
            e.setTo(new Location(Bukkit.getWorld("world"),-280,2,91));
        }
    }

    @EventHandler
    public void onBiomesChange(PlayerTeleportEvent e) {
        Player player = e.getPlayer();

        if (e.getTo().getWorld().getBiome(e.getTo()) == Biome.SOUL_SAND_VALLEY) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING,999999999,1,true,false,false));
            player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP,999999999,1,true,false,false));
        } else {
            if (player.hasPotionEffect(PotionEffectType.SLOW_FALLING)) player.removePotionEffect(PotionEffectType.SLOW_FALLING);
            if (player.hasPotionEffect(PotionEffectType.JUMP)) player.removePotionEffect(PotionEffectType.JUMP);
        }
    }
}
