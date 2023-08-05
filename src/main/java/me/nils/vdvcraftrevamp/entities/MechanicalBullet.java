package me.nils.vdvcraftrevamp.entities;

import me.nils.vdvcraftrevamp.VDVCraftRevamp;
import me.nils.vdvcraftrevamp.constants.Ability;
import me.nils.vdvcraftrevamp.data.EntityData;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.persistence.PersistentDataType;

public class MechanicalBullet {
    public MechanicalBullet(Location loc, double damage, Entity entity) {
        SmallFireball bullet = (SmallFireball) loc.getWorld().spawnEntity(loc, EntityType.SMALL_FIREBALL);

        bullet.setCustomName("Mechanical Bullet");
        bullet.setFireTicks(40);

        bullet.getPersistentDataContainer().set(VDVCraftRevamp.getKey(), PersistentDataType.DOUBLE, damage);
    }
}
