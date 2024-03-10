package me.nils.everhunt.listeners;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import me.nils.everhunt.Everhunt;
import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.constants.ItemType;
import me.nils.everhunt.entities.UndeadScarecrow;
import me.nils.everhunt.entities.abilities.EvocationFang;
import me.nils.everhunt.entities.abilities.Meteor;
import me.nils.everhunt.entities.abilities.SnowBall;
import me.nils.everhunt.entities.abilities.ThunderBolt;
import me.nils.everhunt.managers.ArmorManager;
import me.nils.everhunt.managers.HelmetManager;
import me.nils.everhunt.managers.SoulManager;
import me.nils.everhunt.managers.WeaponManager;
import me.nils.everhunt.utils.Condition;
import me.nils.everhunt.utils.Cooldown;
import me.nils.everhunt.utils.Flow;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.List;

public class AbilityListener implements Listener {
    private LivingEntity target;

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        ItemStack item = player.getInventory().getItemInMainHand();
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return;
        }
        WeaponManager weapon = WeaponManager.items.get(ChatColor.stripColor(meta.getDisplayName()));
        if (weapon == null) {
            return;
        }
        Ability ability = weapon.getAbility();
        int cooldown = ability.getCooldown();
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (ability.needsTarget()) {
                Entity entity = player.getTargetEntity(ability.getRange());
                if (entity instanceof LivingEntity) {
                    this.target = (LivingEntity) entity;
                } else {
                    return;
                }
            }
            if (!(Cooldown.hasCooldown(item))) {
                if (Flow.useFlow(ability.getFlowCost(),player)) {
                    Cooldown.setCooldown(item, cooldown);
                    switch (ability) {
                        case METEOR_BLAST -> {
                            player.swingMainHand();
                            Location loc = player.getEyeLocation().toVector().add(player.getLocation().getDirection().multiply(2)).
                                    toLocation(player.getWorld(), player.getLocation().getYaw(), player.getLocation().getPitch());
                            double damage = player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue() * ability.getDamageMultiplier();
                            new Meteor(loc, damage);
                        }
                        case EVOCATION -> {
                            player.swingMainHand();
                            Location loc;
                            double damage = player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue() * ability.getDamageMultiplier();
                            for (int i = 1; i <= 4; i++) {
                                loc = player.getEyeLocation().toVector().add(player.getLocation().getDirection().multiply(i)).
                                        toLocation(player.getWorld(), player.getLocation().getYaw(), player.getLocation().getPitch());
                                loc.add(0, -1, 0);
                                new EvocationFang(loc, damage);
                            }
                            player.damage(player.getHealth() / 4);
                        }
                        case SNOWBALL -> {
                            player.swingMainHand();
                            Location loc = player.getEyeLocation().toVector().add(player.getLocation().getDirection().multiply(2)).
                                    toLocation(player.getWorld(), player.getLocation().getYaw(), player.getLocation().getPitch());
                            double damage = player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue() * ability.getDamageMultiplier();
                            new SnowBall(loc, damage, player);
                        }
                        case DIMENSION_SHATTER,DIMENSION_UNISON -> {
                            player.swingMainHand();
                            this.target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 254, false, true));
                            if (ability.equals(Ability.DIMENSION_SHATTER)) {
                                player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 0, false, true));
                            }
                        }
                        case LETHAL_ABSORPTION -> {
                            player.swingMainHand();
                            player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 200, 2, false, true));
                            player.damage(player.getHealth() / 2);
                        }
                        case DIMENSIONAL_FREEZE -> {
                            player.swingMainHand();
                            player.playSound(player,Sound.BLOCK_GLASS_BREAK,1,1);
                            Location loc = target.getLocation();
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    if (!Cooldown.hasCooldown(item)) {
                                        cancel();
                                    }
                                    target.teleport(loc);
                                }
                            }.runTaskTimer(Everhunt.getInstance(), 20L,20L);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        e.setCancelled(true);

        Player player  = e.getPlayer();
        ItemStack itemStack = player.getInventory().getItemInOffHand();
        ItemMeta meta = itemStack.getItemMeta();

        if (meta == null) return;

        String item = ChatColor.stripColor(meta.getDisplayName());

        if (Condition.isCustom(ItemType.SOUL,item)) {
            SoulManager soul = SoulManager.souls.get(item);

            Ability ability = soul.getAbility();
            int cooldown = ability.getCooldown();

            if (ability.needsTarget()) {
                Entity entity = player.getTargetEntity(ability.getRange());
                if (entity instanceof LivingEntity livingEntity) {
                    this.target = livingEntity;
                } else {
                    return;
                }
            }
            if (!(Cooldown.hasCooldown(itemStack))) {
                if (Flow.useFlow(ability.getFlowCost(), player)) {
                    Cooldown.setCooldown(itemStack, cooldown);
                    switch (ability) {
                        case SUMMON -> {
                            switch (item) {
                                case "Undead Scarecrow" -> new UndeadScarecrow(player.getLocation(),target); // TODO fix this attacking me
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onJump(PlayerJumpEvent event) {
        Player player = event.getPlayer();
        ItemStack boots = player.getInventory().getBoots();
        if (boots == null) {
            return;
        }
        ArmorManager armor = ArmorManager.items.get(ChatColor.stripColor(boots.getItemMeta().getDisplayName()));
        if (armor == null) {
            return;
        }
        Ability ability = armor.getAbility();
        if (ability == Ability.SPRING) {
            if (!(Cooldown.hasCooldown(boots))) {
                Cooldown.setCooldown(boots, ability.getCooldown());
                Flow.useFlow(ability.getFlowCost(),player);
                player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 60, 3, false, false, false));
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
            if (weapon == null) {
                return;
            }
            Ability ability = weapon.getAbility();
            if (ability.equals(Ability.THUNDER_WARP)) {
                if (!(Cooldown.hasCooldown(item))) {
                    if (Flow.useFlow(ability.getFlowCost(),player)) {
                        Cooldown.setCooldown(item, 2);
                        double damage = player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue() * ability.getDamageMultiplier();
                        event.getEntity().getPersistentDataContainer().set(key, PersistentDataType.DOUBLE, damage);
                    } else {
                        event.setCancelled(true);
                    }
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
                List<Entity> entityList = entity.getNearbyEntities(4, 4, 4);
                Location loc;
                loc = entity.getLocation().toVector().add(player.getLocation().getDirection()).
                        toLocation(player.getWorld(), player.getLocation().getYaw(), player.getLocation().getPitch());
                loc = loc.add(0, 2, 0);
                player.teleport(loc);
                for (int i = 0; i < entityList.size(); i++) {
                    if (!(entityList.get(i) instanceof Player) && !(entityList.get(i) instanceof Item)) {
                        loc = entityList.get(i).getLocation();
                        double damage = entity.getPersistentDataContainer().get(key, PersistentDataType.DOUBLE);
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
        if (entity instanceof Player player) {
            if (Condition.getFullSet(Ability.FOOL, player)) {
                Ability ability = Ability.FOOL;
                double cooldown = ability.getCooldown();
                ItemStack item = player.getInventory().getHelmet();

                if (!(Cooldown.hasCooldown(item))) {
                    Cooldown.setCooldown(item, cooldown);
                    player.getWorld().playSound(player, Sound.ENTITY_WITCH_CELEBRATE, 2F, 1F);
                    event.setDamage(event.getDamage() * ability.getDamageMultiplier());
                    if (damager instanceof LivingEntity living) {
                        living.damage(player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue() / 4);
                    }
                }
            }
        }
        if (!(damager instanceof Player)) {
            if (!(entity instanceof Item) && !(entity instanceof Player)) {
                PersistentDataContainer pdc = damager.getPersistentDataContainer();
                if (pdc.has(Everhunt.getKey())) {
                    if (entity instanceof LivingEntity livingEntity) {
                        event.setCancelled(true);
                        double damage = pdc.get(Everhunt.getKey(), PersistentDataType.DOUBLE);
                        livingEntity.damage(damage);
                    }
                }
            }
        }
        if (damager instanceof Player player) {
            NamespacedKey key = Everhunt.getKey();

            ItemStack item = player.getInventory().getItemInMainHand();
            ItemMeta meta = item.getItemMeta();
            if (meta == null) {
                return;
            }
            PersistentDataContainer pdc = meta.getPersistentDataContainer();
            if (pdc.has(key)) {
                WeaponManager weapon = WeaponManager.items.get(ChatColor.stripColor(meta.getDisplayName()));
                if (weapon == null) {
                    return;
                }
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
                            List<Entity> entityList = livingEntity.getNearbyEntities(5, 5, 5);
                            livingEntity.damage(entityList.size() * 5);
                        }
                    }
                }
                if (ability.equals(Ability.THUNDER_CLAP) || ability.equals(Ability.THUNDER_FLASH) || ability.equals(Ability.CLAP)) {
                    int cooldown = ability.getCooldown();
                    if (entity instanceof LivingEntity) {
                        if (!(Cooldown.hasCooldown(item))) {
                            Cooldown.setCooldown(item, cooldown);
                            Flow.useFlow(ability.getFlowCost(),player);
                            Location loc = entity.getLocation();
                            double damage = player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue() * ability.getDamageMultiplier();
                            if (ability.equals(Ability.CLAP)) {
                                event.setDamage(event.getDamage()+damage);
                                player.spawnParticle(Particle.CRIT_MAGIC,entity.getLocation(),16);
                            }
                            if (ability.equals(Ability.THUNDER_CLAP) || ability.equals(Ability.THUNDER_FLASH)) {
                                new ThunderBolt(loc, damage);
                                player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 30, 254, false, true));
                            }
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

            ItemStack helmet = player.getInventory().getHelmet();
            if (helmet == null) {
                return;
            }
            HelmetManager helm = HelmetManager.items.get(ChatColor.stripColor(helmet.getItemMeta().getDisplayName()));
            if (helm == null) {
                return;
            }
            Ability ability = helm.getAbility();
            int cooldown = ability.getCooldown();

            if (ability == Ability.POWER_OF_THE_SUN) {
                if (!(Cooldown.hasCooldown(helmet))) {
                    Cooldown.setCooldown(item, cooldown);
                    double damage = player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue() * ability.getDamageMultiplier();
                    if (entity instanceof LivingEntity livingEntity) {
                        livingEntity.damage(damage);
                        livingEntity.getWorld().spawnParticle(Particle.FLAME, livingEntity.getLocation(), 5);
                        livingEntity.getWorld().playSound(livingEntity.getLocation(), Sound.ITEM_FIRECHARGE_USE, 2F, 1F);
                    }
                }
            }
            if (ability == Ability.INFECTATION) {
                if (!(Cooldown.hasCooldown(helmet))) {
                    Cooldown.setCooldown(item, cooldown);
                    if (entity instanceof LivingEntity livingEntity) {
                        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 40, 0, false, true, false));
                        livingEntity.getWorld().spawnParticle(Particle.FALLING_SPORE_BLOSSOM, livingEntity.getLocation(), 2);
                        livingEntity.getWorld().playSound(livingEntity.getLocation(), Sound.ENTITY_ZOMBIE_VILLAGER_CURE, 2F, 1F);
                    }
                }
            }
        }
    }
}
