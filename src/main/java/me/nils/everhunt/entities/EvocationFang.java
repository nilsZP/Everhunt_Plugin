package me.nils.everhunt.entities;

import me.nils.everhunt.Everhunt;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.EvokerFangs;
import org.bukkit.persistence.PersistentDataType;

public class EvocationFang {
    public EvocationFang(Location loc, double damage) {
        EvokerFangs fangs = (EvokerFangs) loc.getWorld().spawnEntity(loc, EntityType.EVOKER_FANGS);

        fangs.setCustomName("EvocationFang");

        fangs.getPersistentDataContainer().set(Everhunt.getKey(), PersistentDataType.DOUBLE, damage);
    }
}
