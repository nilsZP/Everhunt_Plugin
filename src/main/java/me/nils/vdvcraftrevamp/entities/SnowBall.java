package me.nils.vdvcraftrevamp.entities;

import me.nils.vdvcraftrevamp.VDVCraftRevamp;
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

        snowball.getPersistentDataContainer().set(VDVCraftRevamp.getKey(), PersistentDataType.DOUBLE, damage);
    }
}
