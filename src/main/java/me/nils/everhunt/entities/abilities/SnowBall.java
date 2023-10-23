package me.nils.everhunt.entities.abilities;

import me.nils.everhunt.Everhunt;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Snowball;
import org.bukkit.persistence.PersistentDataType;

public class SnowBall {
    public SnowBall(Location loc, double damage, Entity entity) {
        Snowball snowball = (Snowball) loc.getWorld().spawnEntity(loc, EntityType.SNOWBALL);

        snowball.setCustomName("SnowBall");
        snowball.setVelocity(entity.getLocation().getDirection().multiply(1.5));

        snowball.getPersistentDataContainer().set(Everhunt.getKey(), PersistentDataType.DOUBLE, damage);
    }
}