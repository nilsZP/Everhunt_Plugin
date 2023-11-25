package me.nils.everhunt.listeners;

import me.nils.everhunt.constants.MobType;
import me.nils.everhunt.data.EntityData;
import me.nils.everhunt.entities.loottables.Monster_Loot;
import me.nils.everhunt.entities.loottables.WolfKing_Loot;
import me.nils.everhunt.utils.Chat;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTransformEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.LootContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class EntityListener implements Listener {
    // TODO make npc's invincible
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

            int looting_mod;
            double luck_mod;

            Player player = entity.getKiller();

            if (entity.getKiller() == null) {
                looting_mod = 0;
                luck_mod = 0;
            } else {
                looting_mod = player.getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS);
                luck_mod = player.getAttribute(Attribute.GENERIC_LUCK).getValue();
            }

            Location location = entity.getLocation();

            LootContext.Builder builder = new LootContext.Builder(location);
            builder.lootedEntity(event.getEntity());
            builder.lootingModifier(looting_mod);
            builder.luck((float) luck_mod);
            builder.killer(player);
            LootContext lootContext = builder.build();

            if (data.getDisplayName().equals("Wolf King")) {
                WolfKing_Loot wolfKing_loot = new WolfKing_Loot();
                Collection<ItemStack> drops = wolfKing_loot.populateLoot(new Random(), lootContext);
                ArrayList<ItemStack> items = (ArrayList<ItemStack>) drops;

                for (ItemStack item : items) {
                    if (item.getAmount() > 0) {
                        location.getWorld().dropItemNaturally(location, item);
                    }
                }
            }

            if (data.getType().equals(MobType.ENEMY) || data.getType().equals(MobType.BOSS)) {
                Monster_Loot monster_loot = new Monster_Loot();
                Collection<ItemStack> drops = monster_loot.populateLoot(new Random(), lootContext);
                ArrayList<ItemStack> items = (ArrayList<ItemStack>) drops;

                for (ItemStack item : items) {
                    if (item.getAmount() > 0) {
                        location.getWorld().dropItemNaturally(location, item);
                    }
                }
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
            List<Entity> entityList = entity.getPassengers();
            EntityData data = EntityData.data.get(entity.getName());
            int maxHealth = data.getMaxHealth();
            for (Entity passenger : entityList) {
                if (passenger instanceof ArmorStand) {
                    passenger.setCustomName(Chat.color(String.format("%s &c%d&f/&c%d%s", entity.getName(), Math.round(entity.getHealth() - event.getFinalDamage()), maxHealth,"♥")));
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
}
