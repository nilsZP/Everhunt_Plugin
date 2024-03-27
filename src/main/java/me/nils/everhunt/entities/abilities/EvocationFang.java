package me.nils.everhunt.entities.abilities;

import me.nils.everhunt.Everhunt;
import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.constants.MobType;
import me.nils.everhunt.constants.Tier;
import me.nils.everhunt.data.EntityData;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.EvokerFangs;
import org.bukkit.persistence.PersistentDataType;

public class EvocationFang extends EntityData {
    public EvocationFang(Location loc, double damage) {
        super("EvocationFang",Tier.BASIC, MobType.ABILITY);
        EvokerFangs fangs = (EvokerFangs) loc.getWorld().spawnEntity(loc, EntityType.EVOKER_FANGS);

        fangs.setCustomName("EvocationFang");

        fangs.getPersistentDataContainer().set(Everhunt.getKey(), PersistentDataType.DOUBLE, damage);
    }
}
