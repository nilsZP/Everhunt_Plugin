package me.nils.everhunt.entities;

import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.constants.MobType;
import me.nils.everhunt.data.EntityData;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Wolf;

public class Wolfling {
    public Wolfling(Location loc) {
        new EntityData("Wolfling", 1, 10, Ability.NONE, MobType.ENEMY);
        Wolf wolf = (Wolf) loc.getWorld().spawnEntity(loc, EntityType.WOLF);

        wolf.setCustomName("Wolfling");
        wolf.setCustomNameVisible(false);

        wolf.setBaby();
        wolf.setAge(0);

        wolf.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(10);
        wolf.setHealth(wolf.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
        wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(2);
        wolf.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(5);

        Hologram.addHologram(wolf);
    }
}
