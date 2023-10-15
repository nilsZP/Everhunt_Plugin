package me.nils.everhunt.entities;

import me.nils.everhunt.Everhunt;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.persistence.PersistentDataType;

public class MechanicalBullet {
    public MechanicalBullet(Location loc, double damage, Entity entity) {
        SmallFireball bullet = (SmallFireball) loc.getWorld().spawnEntity(loc, EntityType.SMALL_FIREBALL);

        bullet.setCustomName("Mechanical Bullet");
        bullet.setFireTicks(40);

        bullet.getPersistentDataContainer().set(Everhunt.getKey(), PersistentDataType.DOUBLE, damage);
    }
}
