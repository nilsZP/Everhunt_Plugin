package me.nils.everhunt.listeners;

import me.nils.everhunt.Everhunt;
import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.entities.abilities.*;
import me.nils.everhunt.managers.ArmorManager;
import me.nils.everhunt.managers.WeaponManager;
import me.nils.everhunt.utils.Cooldown;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.entity.*;
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

        NamespacedKey key = Everhunt.getKey();

        ItemStack item = player.getInventory().getItemInMainHand();
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return;
        }
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        if (pdc.has(key)) {
            WeaponManager weapon = WeaponManager.items.get(ChatColor.stripColor(meta.getDisplayName()));
            Ability ability = weapon.getAbility();
            int cooldown = ability.getCooldown();
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (ability.equals(Ability.METEOR_BLAST)) {
                    if (!(Cooldown.hasCooldown(item))) {
                        Cooldown.setCooldown(item, cooldown);
                        player.swingMainHand();
                        Location loc = player.getEyeLocation().toVector().add(player.getLocation().getDirection().multiply(2)).
                                toLocation(player.getWorld(), player.getLocation().getYaw(), player.getLocation().getPitch());
                        double damage = player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue() * ability.getDamageMultiplier();
                        new Meteor(loc, damage);
                    }
                }
                if (ability.equals(Ability.EVOCATION)) {
                    if (!(Cooldown.hasCooldown(item))) {
                        Cooldown.setCooldown(item, cooldown);
                        player.swingMainHand();
                        Location loc;
                        double damage = player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue() * ability.getDamageMultiplier();
                        for (int i = 1; i <= 4; i++) {
                            loc = player.getEyeLocation().toVector().add(player.getLocation().getDirection().multiply(i)).
                                    toLocation(player.getWorld(), player.getLocation().getYaw(), player.getLocation().getPitch());
                            loc.add(0,-1,0);
                            new EvocationFang(loc, damage);
                        }
                        player.damage(player.getHealth()/4);
                    }
                }
                if (ability.equals(Ability.SNOWBALL)) {
                    if (!(Cooldown.hasCooldown(item))) {
                        Cooldown.setCooldown(item,cooldown);
                        player.swingMainHand();
                        Location loc = player.getEyeLocation().toVector().add(player.getLocation().getDirection().multiply(2)).
                                toLocation(player.getWorld(), player.getLocation().getYaw(), player.getLocation().getPitch());
                        double damage = player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue() * ability.getDamageMultiplier();
                        new SnowBall(loc, damage, player);
                    }
                }
                if (ability.equals(Ability.DIMENSION_SHATTER) || ability.equals(Ability.DIMENSION_UNISON)) {
                    Entity entity = player.getTargetEntity(3);
                    if (entity instanceof LivingEntity livingEntity) {
                        if (!(Cooldown.hasCooldown(item))) {
                            Cooldown.setCooldown(item,cooldown);
                            player.swingMainHand();
                            livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,100,254,false,true));
                            if (ability.equals(Ability.DIMENSION_SHATTER)) {
                                player.addPotionEffect(new PotionEffect(PotionEffectType.POISON,100,0,false,true));
                            }
                        }
                    }
                }
                if (ability.equals(Ability.LETHAL_ABSORPTION)) {
                    if (!(Cooldown.hasCooldown(item))) {
                        Cooldown.setCooldown(item,cooldown);
                        player.swingMainHand();
                        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE,200,2,false,true));
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
        NamespacedKey key = Everhunt.getKey();

        if (helmet != null) {
            meta = helmet.getItemMeta();
            if (meta.getPersistentDataContainer().has(key)) {
                armor = ArmorManager.items.get(ChatColor.stripColor(meta.getDisplayName()));
                ability = armor.getAbility();
                if (ability.equals(Ability.UNITE)) { // TODO same fix as dimension unison
                    if (!(Cooldown.hasCooldown(helmet))) {
                        Cooldown.setCooldown(helmet, ability.getCooldown());
                        List<Entity> entityList = player.getNearbyEntities(5,5,5);
                    }
                }
            }
        }
        if (chestplate != null) {
            meta = chestplate.getItemMeta();
            if (meta.getPersistentDataContainer().has(key)) {
                armor = ArmorManager.items.get(ChatColor.stripColor(meta.getDisplayName()));
                ability = armor.getAbility();
                if (ability.equals(Ability.MECHANICAL_SHOT)) {
                    if (!(Cooldown.hasCooldown(chestplate))) {
                        Cooldown.setCooldown(chestplate,ability.getCooldown());
                        Location loc = player.getEyeLocation().toVector().add(player.getLocation().getDirection().multiply(1)).
                                toLocation(player.getWorld(), player.getLocation().getYaw(), player.getLocation().getPitch());
                        loc = loc.add(0,-0.5,0);
                        double damage = player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue();
                        damage = damage * ability.getDamageMultiplier();
                        new MechanicalBullet(loc,damage,player);
                    }
                }
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
                    if (!(Cooldown.hasCooldown(boots))) {
                        Cooldown.setCooldown(boots, ability.getCooldown());
                        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100, 2, false, true));
                    }
                }
            }
        }
    }

    @EventHandler
    public void onExplosion(EntityExplodeEvent event) {
        if (event.getEntity().getPersistentDataContainer().has(Everhunt.getKey())) {
            event.blockList().clear();
        }
    }

    @EventHandler
    public void onStrike(BlockIgniteEvent event) {
        if (event.getCause() == BlockIgniteEvent.IgniteCause.LIGHTNING) {
            event.setCancelled(true);
        }
        if (event.getCause() == BlockIgniteEvent.IgniteCause.FIREBALL) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onTridentThrow(ProjectileLaunchEvent event) {
        if (event.getEntity().getType() == EntityType.TRIDENT) {
            Player player = (Player) event.getEntity().getShooter();
            NamespacedKey key = Everhunt.getKey();

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
                    double damage = player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue() * ability.getDamageMultiplier();
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
            NamespacedKey key = Everhunt.getKey();
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
                if (pdc.has(Everhunt.getKey())) {
                    if (entity instanceof LivingEntity livingEntity) {
                        event.setCancelled(true);
                        double damage = pdc.get(Everhunt.getKey(),PersistentDataType.DOUBLE);
                        livingEntity.damage(damage);
                    }
                }
            }
        }
        if (damager instanceof Player player) {
            NamespacedKey key = Everhunt.getKey();

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
                if (ability.equals(Ability.DIMENSION_UNISON)) { // TODO fix ability so it works with players
                    if (entity instanceof LivingEntity livingEntity) {
                        if (livingEntity.hasPotionEffect(PotionEffectType.SLOW)) {
                            List<Entity> entityList = livingEntity.getNearbyEntities(5,5,5);
                            livingEntity.damage(entityList.size()*5);
                        }
                    }
                }
                if (ability.equals(Ability.THUNDER_CLAP) || ability.equals(Ability.THUNDER_FLASH)) {
                    int cooldown = ability.getCooldown();
                    if (entity instanceof LivingEntity) {
                        if (!(Cooldown.hasCooldown(item))) {
                            Cooldown.setCooldown(item,cooldown);
                            Location loc = entity.getLocation();
                            double damage = player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue() * ability.getDamageMultiplier();
                            new ThunderBolt(loc, damage);
                            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,30,254,false,true));
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
            }
        }
    }
}
