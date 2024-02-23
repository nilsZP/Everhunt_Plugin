package me.nils.everhunt.entities.npc;

import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.constants.MobType;
import me.nils.everhunt.constants.Tier;
import me.nils.everhunt.data.EntityData;
import org.bukkit.Location;
import org.bukkit.entity.Cat;
import org.bukkit.entity.EntityType;

public class Mi extends EntityData {
    public Mi(Location loc) {
        super("Mi",0, Tier.BASIC, Ability.NONE, MobType.NPC);

        Cat mi = (Cat) loc.getWorld().spawnEntity(loc, EntityType.CAT);

        mi.setAI(false);
        mi.setInvulnerable(true);
        mi.setTamed(true);
        mi.setRemoveWhenFarAway(false);

        mi.setCustomName("Mi");
        mi.setCustomNameVisible(true);

        mi.setCatType(Cat.Type.BRITISH_SHORTHAIR);
    }
}
