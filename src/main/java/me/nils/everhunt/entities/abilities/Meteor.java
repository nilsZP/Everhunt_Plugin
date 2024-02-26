package me.nils.everhunt.entities.abilities;

import me.nils.everhunt.Everhunt;
import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.constants.MobType;
import me.nils.everhunt.constants.Tier;
import me.nils.everhunt.data.EntityData;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.persistence.PersistentDataType;

public class Meteor extends EntityData {
    public Meteor(Location loc, double damage) {
        super("Meteor", 0, Tier.BASIC, Ability.NONE, MobType.ABILITY);
        Fireball meteor = (Fireball) loc.getWorld().spawnEntity(loc, EntityType.FIREBALL);

        meteor.setCustomName("Meteor");
        meteor.setYield(3.0F);

        meteor.getPersistentDataContainer().set(Everhunt.getKey(), PersistentDataType.DOUBLE, damage);
    }
}
