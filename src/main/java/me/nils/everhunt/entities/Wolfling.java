package me.nils.everhunt.entities;

import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.constants.MobType;
import me.nils.everhunt.data.EntityData;
import me.nils.everhunt.data.LootData;
import me.nils.everhunt.managers.ItemManager;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;

import java.util.List;

public class Wolfling {
    public Wolfling(Location loc) {
        EntityData data = new EntityData("Wolfling", 1, 8, Ability.NONE, MobType.ENEMY);
        Wolf wolf = (Wolf) loc.getWorld().spawnEntity(loc, EntityType.WOLF);

        wolf.setCustomName("Wolfling");
        wolf.setCustomNameVisible(false);

        wolf.setBaby();

        int dropID = ItemManager.getItemID("Wolfling Hide");

        new LootData(data.getEntityID(), dropID,1,3,20);

        List<Entity> entityList = wolf.getNearbyEntities(5,5,5);
        for (Entity entity : entityList) {
            if (entity instanceof Player player) {
                wolf.setTarget(player);
            }
        }

        wolf.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(8);
        wolf.setHealth(wolf.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
        wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(2);
        wolf.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(5);

        Hologram.addHologram(wolf);
    }
}
