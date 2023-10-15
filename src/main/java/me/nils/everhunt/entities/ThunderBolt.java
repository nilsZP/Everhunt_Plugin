package me.nils.everhunt.entities;

import me.nils.everhunt.Everhunt;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LightningStrike;
import org.bukkit.persistence.PersistentDataType;

public class ThunderBolt {
    public ThunderBolt(Location loc, double damage) {
        LightningStrike thunder = (LightningStrike) loc.getWorld().spawnEntity(loc, EntityType.LIGHTNING);

        thunder.setCustomName("thunder");

        thunder.getPersistentDataContainer().set(Everhunt.getKey(), PersistentDataType.DOUBLE, damage);
    }
}
