package me.nils.everhunt.entities.npc;

import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.constants.MobType;
import me.nils.everhunt.constants.Tier;
import me.nils.everhunt.data.EntityData;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;

public class Farmer extends EntityData {
    public Farmer(Location loc) {
        super("Farmer",Tier.BASIC, MobType.NPC);

        Villager villager = (Villager) loc.getWorld().spawnEntity(loc, EntityType.VILLAGER);

        villager.setAI(false);
        villager.setInvulnerable(true);

        villager.setCustomName("Farmer");
        villager.setCustomNameVisible(true);

        villager.setProfession(Villager.Profession.FARMER);
        villager.setVillagerExperience(1);
    }
}
