package me.nils.vdvcraftrevamp.listeners;

import me.nils.vdvcraftrevamp.VDVCraftRevamp;
import me.nils.vdvcraftrevamp.constants.Ability;
import me.nils.vdvcraftrevamp.entities.Meteor;
import me.nils.vdvcraftrevamp.entities.SnowBall;
import me.nils.vdvcraftrevamp.entities.ThunderBolt;
import me.nils.vdvcraftrevamp.managers.ArmorManager;
import me.nils.vdvcraftrevamp.managers.WeaponManager;
import me.nils.vdvcraftrevamp.utils.Cooldown;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.List;

public class AbilityListener implements Listener {

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        NamespacedKey key = VDVCraftRevamp.getKey();

        ItemStack item = player.getInventory().getItemInMainHand();
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return;
        }
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        if (pdc.has(key)) {
            WeaponManager weapon = WeaponManager.items.get(ChatColor.stripColor(meta.getDisplayName()));
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                Ability ability = weapon.getAbility();
                int cooldown = ability.getCooldown();
                if (ability.equals(Ability.METEOR_BLAST)) {
                    if (!(Cooldown.hasCooldown(item))) {
                        Cooldown.setCooldown(item, cooldown);
                        player.swingMainHand();
                        Location loc = player.getEyeLocation().toVector().add(player.getLocation().getDirection().multiply(2)).
                                toLocation(player.getWorld(), player.getLocation().getYaw(), player.getLocation().getPitch());
                        double damage = weapon.getDamage() * ability.getDamageMultiplier();
                        new Meteor(loc, damage);
                    }
                }
                if (ability.equals(Ability.SNOWBALL)) {
                    if (!(Cooldown.hasCooldown(item))) {
                        Cooldown.setCooldown(item,cooldown);
                        player.swingMainHand();
                        Location loc = player.getEyeLocation().toVector().add(player.getLocation().getDirection().multiply(2)).
                                toLocation(player.getWorld(), player.getLocation().getYaw(), player.getLocation().getPitch());
                        double damage = weapon.getDamage() * ability.getDamageMultiplier();
                        new SnowBall(loc, damage, player);
                    }
                }
                if (ability.equals(Ability.THUNDER_CLAP) || ability.equals(Ability.THUNDER_FLASH)) {
                    Entity entity = player.getTargetEntity(3);
                    if (entity instanceof LivingEntity) {
                        if (!(Cooldown.hasCooldown(item))) {
                            Cooldown.setCooldown(item,cooldown);
                            player.swingMainHand();
                            Location loc = entity.getLocation();
                            double damage = weapon.getDamage() * ability.getDamageMultiplier();
                            new ThunderBolt(loc, damage);
                            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,30,255,false,true));
                            if (ability.equals(Ability.THUNDER_FLASH)) {
                                loc = player.getLocation();
                                Vector dir = loc.getDirection();
                                dir.normalize();
                                dir.multiply(5);
                                loc.add(dir);
                                player.teleport(loc);
                            }
                        }
                    }
                }
                if (ability.equals(Ability.DIMENSION_SHATTER) || ability.equals(Ability.DIMENSION_UNISON)) {
                    Entity entity = player.getTargetEntity(3);
                    if (entity instanceof LivingEntity livingEntity) {
                        if (!(Cooldown.hasCooldown(item))) {
                            Cooldown.setCooldown(item,cooldown);
                            player.swingMainHand();
                            livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,100,255,false,true));
                        }
                    }
                }
                if (ability.equals(Ability.LETHAL_ABSORPTION)) {
                    if (!(Cooldown.hasCooldown(item))) {
                        Cooldown.setCooldown(item,cooldown);
                        player.swingMainHand();
                        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE,200,3,false,true));
                        player.damage(player.getHealth()/2);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();

        ItemStack helmet = player.getInventory().getHelmet();
        ItemStack chestplate = player.getInventory().getChestplate();
        ItemStack leggings = player.getInventory().getLeggings();
        ItemStack boots = player.getInventory().getBoots();
        ItemMeta meta;
        ArmorManager armor;
        Ability ability;
        NamespacedKey key = VDVCraftRevamp.getKey();

        if (helmet != null) {
            meta = helmet.getItemMeta();
            if (meta.getPersistentDataContainer().has(key)) {
                armor = ArmorManager.items.get(ChatColor.stripColor(meta.getDisplayName()));
                ability = armor.getAbility();
                if (ability.equals(Ability.UNITE)) {

                }
            }
        }
        if (chestplate != null) {
            meta = chestplate.getItemMeta();
            if (meta.getPersistentDataContainer().has(key)) {
                armor = ArmorManager.items.get(ChatColor.stripColor(meta.getDisplayName()));
                ability = armor.getAbility();
            }
        }
        if (leggings != null) {
            meta = leggings.getItemMeta();
            if (meta.getPersistentDataContainer().has(key)) {
                armor = ArmorManager.items.get(ChatColor.stripColor(meta.getDisplayName()));
                ability = armor.getAbility();
            }
        }
        if (boots != null) {
            meta = boots.getItemMeta();
            if (meta.getPersistentDataContainer().has(key)) {
                armor = ArmorManager.items.get(ChatColor.stripColor(meta.getDisplayName()));
                ability = armor.getAbility();
                if (ability.equals(Ability.SPRING)) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP,100,3,false,true));
                }
            }
        }
    }

    @EventHandler
    public void onExplosion(EntityExplodeEvent event) {
        if (event.getEntity().getPersistentDataContainer().has(VDVCraftRevamp.getKey())) {
            event.blockList().clear();
        }
    }

    @EventHandler
    public void onStrike(BlockIgniteEvent event) {
        if (event.getCause() == BlockIgniteEvent.IgniteCause.LIGHTNING) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onTridentThrow(ProjectileLaunchEvent event) {
        if (event.getEntity().getType() == EntityType.TRIDENT) {
            Player player = (Player) event.getEntity().getShooter();
            NamespacedKey key = VDVCraftRevamp.getKey();

            ItemStack item = player.getInventory().getItemInMainHand();
            ItemMeta meta = item.getItemMeta();
            PersistentDataContainer pdc = meta.getPersistentDataContainer();
            if (!(pdc.getKeys().contains(key))) {
                return;
            }
            WeaponManager weapon = WeaponManager.items.get(ChatColor.stripColor(meta.getDisplayName()));
            Ability ability = weapon.getAbility();
            if (ability.equals(Ability.THUNDER_WARP)) {
                if (!(Cooldown.hasCooldown(item))) {
                    Cooldown.setCooldown(item, 2);
                    double damage = weapon.getDamage() * ability.getDamageMultiplier();
                    event.getEntity().getPersistentDataContainer().set(key,PersistentDataType.DOUBLE,damage);
                } else {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void tridentHit(ProjectileHitEvent event) {
        if (event.getEntity().getType() == EntityType.TRIDENT) {
            NamespacedKey key = VDVCraftRevamp.getKey();
            Entity entity = event.getEntity();
            PersistentDataContainer container = entity.getPersistentDataContainer();
            if (container.has(key)) {
                Player player = (Player) event.getEntity().getShooter();
                List<Entity> entityList = entity.getNearbyEntities(4,4,4);
                Location loc;
                loc = entity.getLocation().toVector().add(player.getLocation().getDirection()).
                        toLocation(player.getWorld(), player.getLocation().getYaw(), player.getLocation().getPitch());
                loc = loc.add(0,2,0);
                player.teleport(loc);
                for (int i = 0; i < entityList.size(); i++) {
                    if (!(entityList.get(i) instanceof Player) && !(entityList.get(i) instanceof Item)) {
                        loc = entityList.get(i).getLocation();
                        double damage = entity.getPersistentDataContainer().get(key,PersistentDataType.DOUBLE);
                        new ThunderBolt(loc, damage);
                    }
                }
            }
        }
    }

    @EventHandler
    public void abilityHit(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();
        Entity entity = event.getEntity();
        if (!(damager instanceof Player)) {
            if (!(entity instanceof Item)) {
                PersistentDataContainer pdc = damager.getPersistentDataContainer();
                if (pdc.has(VDVCraftRevamp.getKey())) {
                    if (entity instanceof LivingEntity livingEntity) {
                        event.setCancelled(true);
                        double damage = pdc.get(VDVCraftRevamp.getKey(),PersistentDataType.DOUBLE);
                        livingEntity.damage(damage);
                    }
                }
            }
        }
        if (damager instanceof Player player) {
            NamespacedKey key = VDVCraftRevamp.getKey();

            ItemStack item = player.getInventory().getItemInMainHand();
            ItemMeta meta = item.getItemMeta();
            PersistentDataContainer pdc = meta.getPersistentDataContainer();
            if (pdc.has(key)) {
                WeaponManager weapon = WeaponManager.items.get(ChatColor.stripColor(meta.getDisplayName()));
                Ability ability = weapon.getAbility();
                if (ability.equals(Ability.DIMENSION_SHATTER)) {
                    if (entity instanceof LivingEntity livingEntity) {
                        if (livingEntity.hasPotionEffect(PotionEffectType.SLOW)) {
                            livingEntity.setMaximumNoDamageTicks(0);
                        } else {
                            livingEntity.setMaximumNoDamageTicks(20);
                        }
                    }
                }
                if (ability.equals(Ability.DIMENSION_UNISON)) {
                    if (entity instanceof LivingEntity livingEntity) {
                        if (livingEntity.hasPotionEffect(PotionEffectType.SLOW)) {
                            List<Entity> entityList = livingEntity.getNearbyEntities(5,5,5);
                            livingEntity.damage(entityList.size()*5);
                        }
                    }
                }
            }
        }
    }
}
