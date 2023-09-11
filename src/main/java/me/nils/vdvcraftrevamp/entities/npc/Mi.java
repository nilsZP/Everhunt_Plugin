package me.nils.vdvcraftrevamp.entities.npc;

import me.nils.vdvcraftrevamp.constants.Ability;
import me.nils.vdvcraftrevamp.constants.MobType;
import me.nils.vdvcraftrevamp.data.EntityData;
import org.bukkit.Location;
import org.bukkit.entity.Cat;
import org.bukkit.entity.EntityType;

public class Mi extends EntityData {
    public Mi(Location loc) {
        super("Mi", Ability.NONE, MobType.NPC, Flow.BURNING);

        Cat mi = (Cat) loc.getWorld().spawnEntity(loc, EntityType.CAT);

        mi.setAI(false);
        mi.setInvulnerable(true);

        mi.setCustomName("Mi");
        mi.setCustomNameVisible(true);

        mi.setCatType(Cat.Type.BRITISH_SHORTHAIR);
    }
}
