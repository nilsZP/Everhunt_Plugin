package me.nils.everhunt.listeners;

import me.nils.everhunt.constants.MobType;
import me.nils.everhunt.data.EntityData;
import me.nils.everhunt.entities.UndeadScarecrow;
import me.nils.everhunt.entities.loottables.Loot;
import me.nils.everhunt.utils.Chat;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Biome;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.LootContext;

import java.util.ArrayList;
import java.util.Collection;
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
        int count = 0;
        for (Entity entity : e.getLocation().getChunk().getEntities()) {
            if (!(entity instanceof ArmorStand) && !(isNPC(entity)) && !(entity instanceof Player)) {
                count++;
            }
        }
        if (count >= 5) {
            if (!e.getEntity().getPassengers().isEmpty()) {
                for (Entity entity : e.getEntity().getPassengers()) {
                    e.getEntity().removePassenger(entity);
                }
            }
            e.setCancelled(true);
            return;
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
}
