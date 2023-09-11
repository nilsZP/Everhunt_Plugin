package me.nils.vdvcraftrevamp.entities.npc;

import me.nils.vdvcraftrevamp.constants.Ability;
import me.nils.vdvcraftrevamp.constants.MobType;
import me.nils.vdvcraftrevamp.data.EntityData;
import org.bukkit.Location;
import org.bukkit.entity.Cat;
import org.bukkit.entity.EntityType;

public class Mikull extends EntityData {
    public Mikull(Location loc) {
        super("Mikull", Ability.NONE, MobType.NPC);

        Cat mi = (Cat) loc.getWorld().spawnEntity(loc, EntityType.CAT);

        mi.setAI(false);
        mi.setInvulnerable(true);

        mi.setCustomName("Mikull");
        mi.setCustomNameVisible(true);

        mi.setCatType(Cat.Type.BRITISH_SHORTHAIR);
    }
}
