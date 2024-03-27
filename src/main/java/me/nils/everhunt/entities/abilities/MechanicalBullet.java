package me.nils.everhunt.entities.abilities;

import me.nils.everhunt.Everhunt;
import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.constants.MobType;
import me.nils.everhunt.constants.Tier;
import me.nils.everhunt.data.EntityData;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.SmallFireball;
import org.bukkit.persistence.PersistentDataType;

public class MechanicalBullet extends EntityData {
    public MechanicalBullet(Location loc, double damage) {
        super("Mechanical Bullet",Tier.BASIC, MobType.ABILITY);
        SmallFireball bullet = (SmallFireball) loc.getWorld().spawnEntity(loc, EntityType.SMALL_FIREBALL);

        bullet.setCustomName("Mechanical Bullet");
        bullet.setFireTicks(40);

        bullet.getPersistentDataContainer().set(Everhunt.getKey(), PersistentDataType.DOUBLE, damage);
    }
}
