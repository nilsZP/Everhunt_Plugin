package me.nils.everhunt.listeners;

import me.nils.everhunt.Everhunt;
import me.nils.everhunt.constants.MobType;
import me.nils.everhunt.data.EntityData;
import me.nils.everhunt.entities.UndeadScarecrow;
import me.nils.everhunt.entities.loottables.Loot;
import me.nils.everhunt.utils.Chat;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Biome;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTransformEvent;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EntityListener implements Listener {
    @EventHandler
    public void onDeath(EntityDeathEvent event) {
        if (isNPC(event.getEntity())) {
            event.setCancelled(true);
            return;
        }
        event.getDrops().clear();
        if (!(event.getEntity() instanceof Player)) {
            LivingEntity entity = event.getEntity();
            if (entity.getPersistentDataContainer().has(Everhunt.getKey())) {
                String name = entity.getPersistentDataContainer().get(Everhunt.getKey(),PersistentDataType.STRING);
                Location location = entity.getLocation();

                location.getWorld().dropItemNaturally(location, Loot.getLootTable(name).getRandom());
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (isNPC(event.getEntity())) {
            event.setCancelled(true);
            return;
        }
        if (event.getEntity() instanceof Player) {
            return;
        }
        if (event.getEntity() instanceof LivingEntity entity) {
            if (entity.getPersistentDataContainer().has(Everhunt.getKey())) {
                int maxHealth = (int) entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() - 1;
                int health = (int) Math.round(entity.getHealth() - event.getFinalDamage());
                if (health < 0) health = 0;
                String name = entity.getPersistentDataContainer().get(Everhunt.getKey(), PersistentDataType.STRING);

                entity.setCustomName(Chat.color(String.format("%s &c%d&f/&c%d%s", name, health, maxHealth, "♥")));
            }
        }
    }

    @EventHandler
    public void onTransform(EntityTransformEvent event) {
        if (isNPC(event.getEntity())) {
            event.setCancelled(true);
        }
    }

    public boolean isNPC(Entity entity) {
        EntityData data = EntityData.data.get(entity.getName());
        if (data != null) {
            if (data.getType() == MobType.NPC) {
                return true;
            }
        }

        return false;
    }

    @EventHandler
    public void onSpawn(CreatureSpawnEvent e) {
        if (e.getEntity() instanceof Player) return;
        if (e.getEntity() instanceof Projectile) return;
        if (e.getSpawnReason() != CreatureSpawnEvent.SpawnReason.CUSTOM) {
            e.setCancelled(true);
            List<Entity> list = e.getEntity().getNearbyEntities(16,5,16);
            list.removeIf(entity -> entity instanceof ArmorStand || isNPC(entity) || entity instanceof Player);
            if (list.size() >= 5) {
                return;
            }
            if (new Random().nextInt(0,5) == 0) {
                if (e.getLocation().getWorld().getBiome(e.getLocation()) == Biome.PLAINS) {
                    if (e.getEntity() instanceof Zombie) {
                        new UndeadScarecrow(e.getLocation());
                    }
                }
            }
        }
    }

    /*@EventHandler
    public void onDespawn(EntitiesUnloadEvent e) {
        for (Entity entity : e.getEntities()) {
            if (!isNPC(entity) && !(entity instanceof Player)) {
                if (!entity.getPassengers().isEmpty()) {
                    entity.getPassengers().clear();
                }
                entity.remove();
            }
        }
    }*/
}
