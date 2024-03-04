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
import org.bukkit.event.world.EntitiesUnloadEvent;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

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
            List<Entity> entityList = entity.getPassengers();
            for (Entity passenger : entityList) {
                if (passenger instanceof ArmorStand hologram) {
                    hologram.remove();
                }
            }
            EntityData data = EntityData.data.get(entity.getName());
            if (data == null) {
                return;
            }

            Location location = entity.getLocation();

            location.getWorld().dropItemNaturally(location, Loot.getLootTable(entity.getName()).getRandom());
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
            List<Entity> entityList = entity.getPassengers();
            int maxHealth = (int) entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
            for (Entity passenger : entityList) {
                if (passenger instanceof ArmorStand) {
                    passenger.setCustomName(Chat.color(String.format("%s &c%d&f/&c%d%s", entity.getName(), Math.round(entity.getHealth() - event.getFinalDamage()), maxHealth, "♥")));
                }
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
        ArrayList<Entity> list = new ArrayList<>(List.of(e.getLocation().getChunk().getEntities()));
        list.removeIf(entity -> entity instanceof ArmorStand && isNPC(entity) && entity instanceof Player);
        if (list.size() >= 3) {
            if (!e.getEntity().getPassengers().isEmpty()) {
                e.getEntity().getPassengers().clear();
            }
            e.setCancelled(true);
            return;
        }
        if (e.getEntity() instanceof ArmorStand armorStand) {
            if (!armorStand.isInsideVehicle()) e.setCancelled(true);
        }
        if (e.getSpawnReason() != CreatureSpawnEvent.SpawnReason.CUSTOM) {
            e.setCancelled(true);
            if (e.getLocation().getWorld().getBiome(e.getLocation()) == Biome.PLAINS) {
                if (e.getEntity() instanceof Zombie) {
                    new UndeadScarecrow(e.getLocation());
                }
            }
        }
    }

    @EventHandler
    public void onDespawn(EntitiesUnloadEvent e) {
        for (Entity entity : e.getEntities()) {
            if (!isNPC(entity)) {
                if (!entity.getPassengers().isEmpty()) {
                    entity.getPassengers().clear();
                }
                entity.remove();
            }
        }
    }
}
