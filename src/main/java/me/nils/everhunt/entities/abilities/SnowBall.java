package me.nils.everhunt.entities.abilities;

import me.nils.everhunt.Everhunt;
import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.constants.MobType;
import me.nils.everhunt.constants.Tier;
import me.nils.everhunt.data.EntityData;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Snowball;
import org.bukkit.persistence.PersistentDataType;

public class SnowBall extends EntityData {
    public SnowBall(Location loc, double damage, Entity entity) {
        super("SnowBall",0, Tier.BASIC, Ability.NONE, MobType.ABILITY);
        Snowball snowball = (Snowball) loc.getWorld().spawnEntity(loc, EntityType.SNOWBALL);

        snowball.setCustomName("SnowBall");
        snowball.setVelocity(entity.getLocation().getDirection().multiply(1.5));

        snowball.getPersistentDataContainer().set(Everhunt.getKey(), PersistentDataType.DOUBLE, damage);
    }
}
