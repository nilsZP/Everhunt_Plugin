package me.nils.vdvcraftrevamp.listeners;

import me.nils.vdvcraftrevamp.VDVCraftRevamp;
import me.nils.vdvcraftrevamp.constants.Ability;
import me.nils.vdvcraftrevamp.entities.Meteor;
import me.nils.vdvcraftrevamp.entities.ThunderBolt;
import me.nils.vdvcraftrevamp.utils.Chat;
import me.nils.vdvcraftrevamp.utils.Cooldown;
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
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class AbilityListener implements Listener {

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        NamespacedKey key = VDVCraftRevamp.getKey();

        ItemStack item = player.getInventory().getItemInMainHand();
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        if (pdc.has(key)) {
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                String ability = pdc.get(key, PersistentDataType.STRING);
                if (ability.equals(Ability.METEOR_BLAST.getName())) {
                    if (!(Cooldown.hasCooldown(item))) {
                        Cooldown.setCooldown(item, 1);
                        player.swingMainHand();
                        Location loc = player.getEyeLocation().toVector().add(player.getLocation().getDirection().multiply(2)).
                                toLocation(player.getWorld(), player.getLocation().getYaw(), player.getLocation().getPitch());
                        new Meteor(loc);
                    }
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
            String ability = pdc.get(key, PersistentDataType.STRING);
            if (ability.equals(Ability.THUNDER_WARP.getName())) {
                if (!(Cooldown.hasCooldown(item))) {
                    Cooldown.setCooldown(item, 2);
                    event.getEntity().getPersistentDataContainer().set(key,PersistentDataType.STRING,ability);
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
                        new ThunderBolt(loc);
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
    }
}
