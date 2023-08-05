package me.nils.vdvcraftrevamp.entities.npc;

import me.nils.vdvcraftrevamp.constants.Ability;
import me.nils.vdvcraftrevamp.constants.Flow;
import me.nils.vdvcraftrevamp.constants.MobType;
import me.nils.vdvcraftrevamp.data.EntityData;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;

public class Marcus extends EntityData {
    public Marcus(Location loc) {
        super("Marcus", Ability.NONE, MobType.NPC, Flow.FREEZING);

        Villager villager = (Villager) loc.getWorld().spawnEntity(loc, EntityType.VILLAGER);

        villager.setAI(false);
        villager.setInvulnerable(true);

        villager.setCustomName("Marcus");
        villager.setCustomNameVisible(true);

        villager.setProfession(Villager.Profession.WEAPONSMITH);
        villager.setVillagerExperience(1);
    }
}
