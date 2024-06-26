package me.nils.everhunt.entities.npc;

import me.nils.everhunt.constants.MobType;
import me.nils.everhunt.constants.Tier;
import me.nils.everhunt.data.EntityData;
import org.bukkit.Location;
import org.bukkit.entity.Cat;
import org.bukkit.entity.EntityType;

public class Mikull extends EntityData {
    public Mikull(Location loc) {
        super("Mikull",Tier.BASIC, MobType.NPC);

        Cat mi = (Cat) loc.getWorld().spawnEntity(loc, EntityType.CAT);

        mi.setAI(false);
        mi.setInvulnerable(true);
        mi.setTamed(true);
        mi.setRemoveWhenFarAway(false);

        mi.setCustomName("Mikull");
        mi.setCustomNameVisible(true);

        mi.setCatType(Cat.Type.BRITISH_SHORTHAIR);
    }
}
