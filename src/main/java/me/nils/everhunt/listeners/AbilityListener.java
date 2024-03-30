package me.nils.everhunt.listeners;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import me.nils.everhunt.Everhunt;
import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.constants.ItemType;
import me.nils.everhunt.entities.UndeadScarecrow;
import me.nils.everhunt.entities.abilities.*;
import me.nils.everhunt.managers.*;
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
        double damage = player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue() * ability.getDamageMultiplier();
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
                            new Meteor(loc, damage);
                        }
                        case EVOCATION -> {
                            player.swingMainHand();
                            Location loc = this.target.getLocation();
                            new EvocationFang(loc,damage);
                            new EvocationFang(loc.add(1,0,0),damage);
                            new EvocationFang(loc.add(-1,0,0),damage);
                            new EvocationFang(loc.add(0,0,1),damage);
                            new EvocationFang(loc.add(0,0,-1),damage);
                            player.damage(player.getHealth() / 4);
                        }
                        case SNOWBALL -> {
                            player.swingMainHand();
                            Location loc = player.getEyeLocation().toVector().add(player.getLocation().getDirection().multiply(2)).
                                    toLocation(player.getWorld(), player.getLocation().getYaw(), player.getLocation().getPitch());
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
                        case MECHANICAL_SHOT -> {
                            player.swingMainHand();
                            Location loc = player.getEyeLocation().toVector().add(player.getLocation().getDirection().multiply(2)).
                                    toLocation(player.getWorld(), player.getLocation().getYaw(), player.getLocation().getPitch());
                            new MechanicalBullet(loc,damage);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onConsume(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        ItemStack itemStack = player.getInventory().getItemInMainHand();

        if (!itemStack.hasItemMeta()) return;

        String item = ChatColor.stripColor(itemStack.getItemMeta().getDisplayName());

        if (Condition.isCustom(ItemType.DISH,item)) {
            DishManager dish = DishManager.items.get(item);
            int amplifier = dish.getNutrition();

            if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (player.hasPotionEffect(PotionEffectType.REGENERATION)) {
                    if (player.getPotionEffect(PotionEffectType.REGENERATION).getAmplifier() > amplifier) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.HEAL,40,amplifier-1,false,true,true));
                    }
                } else player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,200,amplifier, false,true,true));

                if (itemStack.getAmount() > 1) {
                    itemStack.subtract();
                } else player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
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
                                case "Undead Scarecrow" -> {
                                    LivingEntity entity = new UndeadScarecrow(player.getLocation()).getEntity();// TODO fix this attacking me
                                    target.attack(entity);
                                    entity.attack(target);
                                }
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
            ItemStack itemStack = player.getInventory().getItemInMainHand();

            if (!itemStack.hasItemMeta()) return;

            String item = ChatColor.stripColor(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName());

            if (Condition.isCustom(ItemType.WEAPON,item)) {
                Ability ability = WeaponManager.items.get(item).getAbility();
                if (ability.equals(Ability.THUNDER_WARP)) {
                    if (!(Cooldown.hasCooldown(itemStack))) {
                        if (Flow.useFlow(ability.getFlowCost(), player)) {
                            Cooldown.setCooldown(itemStack, ability.getCooldown());
                            double damage = player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue() * ability.getDamageMultiplier();
                            event.getEntity().getPersistentDataContainer().set(Everhunt.getKey(), PersistentDataType.DOUBLE, damage);
                            return;
                        }
                    }
                }
            }

            event.setCancelled(true);
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
                        if (pdc.has(Everhunt.getKey(),PersistentDataType.DOUBLE)) {
                            event.setCancelled(true);
                            double damage = pdc.get(Everhunt.getKey(), PersistentDataType.DOUBLE);
                            livingEntity.damage(damage);
                        }
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
