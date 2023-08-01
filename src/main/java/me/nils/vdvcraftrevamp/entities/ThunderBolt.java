package me.nils.vdvcraftrevamp.entities;

import me.nils.vdvcraftrevamp.VDVCraftRevamp;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LightningStrike;
import org.bukkit.persistence.PersistentDataType;

public class ThunderBolt {
    public ThunderBolt(Location loc, double damage) {
        LightningStrike thunder = (LightningStrike) loc.getWorld().spawnEntity(loc, EntityType.LIGHTNING);

        thunder.setCustomName("thunder");

        thunder.getPersistentDataContainer().set(VDVCraftRevamp.getKey(), PersistentDataType.DOUBLE, damage);
    }
}
