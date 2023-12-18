package me.nils.everhunt.entities.npc;

import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.constants.MobType;
import me.nils.everhunt.data.EntityData;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;

public class Hunter extends EntityData {
    public Hunter(Location loc) {
        super("Hunter",0,1, Ability.NONE, MobType.NPC);

        Villager villager = (Villager) loc.getWorld().spawnEntity(loc, EntityType.VILLAGER);

        villager.setAI(false);
        villager.setInvulnerable(true);

        villager.setCustomName("Hunter");
        villager.setCustomNameVisible(true);

        villager.setProfession(Villager.Profession.ARMORER);
        villager.setVillagerExperience(1);
    }
}
