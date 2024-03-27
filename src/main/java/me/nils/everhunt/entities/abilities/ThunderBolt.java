package me.nils.everhunt.entities.abilities;

import me.nils.everhunt.Everhunt;
import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.constants.MobType;
import me.nils.everhunt.constants.Tier;
import me.nils.everhunt.data.EntityData;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LightningStrike;
import org.bukkit.persistence.PersistentDataType;

public class ThunderBolt extends EntityData {
    public ThunderBolt(Location loc, double damage) {
        super("Thunder",Tier.BASIC, MobType.ABILITY);
        LightningStrike thunder = (LightningStrike) loc.getWorld().spawnEntity(loc, EntityType.LIGHTNING);

        thunder.setCustomName("Thunder");

        thunder.getPersistentDataContainer().set(Everhunt.getKey(), PersistentDataType.DOUBLE, damage);
    }
}
