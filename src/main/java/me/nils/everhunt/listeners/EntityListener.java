package me.nils.everhunt.listeners;

import me.nils.everhunt.data.EntityData;
import me.nils.everhunt.entities.Hologram;
import me.nils.everhunt.items.armor.MechanicalChestplate;
import me.nils.everhunt.items.armor.SpringerBoots;
import me.nils.everhunt.items.armor.UnitedHelmet;
import me.nils.everhunt.items.weapons.AzureWrath;
import me.nils.everhunt.utils.Chat;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Random;

public class EntityListener implements Listener {
    @EventHandler
    public void onDeath(EntityDeathEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            Entity entity = event.getEntity();
            List<Entity> entityList = entity.getPassengers();
            for (Entity passenger : entityList) {
                if (passenger instanceof ArmorStand hologram) {
                    hologram.remove();
                }
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof LivingEntity entity) {
            List<Entity> entityList = entity.getPassengers();
            EntityData data = EntityData.data.get(entity.getName());
            int maxHealth = data.getMaxHealth();
            for (Entity passenger : entityList) {
                if (passenger instanceof ArmorStand) {
                    passenger.setCustomName(Chat.color(String.format("%s &c%d&f/&c%d%s", entity.getName(), maxHealth-Math.round(entity.getLastDamage()), maxHealth,"♥")));
                }
            }
        }
    }
}
