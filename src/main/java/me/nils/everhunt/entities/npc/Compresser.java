package me.nils.everhunt.entities.npc;

import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.constants.MobType;
import me.nils.everhunt.constants.Tier;
import me.nils.everhunt.data.EntityData;
import me.nils.everhunt.data.QuestData;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;

public class Compresser extends EntityData {
    public Compresser(Location loc) {
        super("Tim",0, Tier.BASIC, Ability.NONE, MobType.NPC);

        Villager villager = (Villager) loc.getWorld().spawnEntity(loc, EntityType.VILLAGER);

        villager.setAI(false);
        villager.setInvulnerable(true);

        villager.setCustomName("Compresser");
        villager.setCustomNameVisible(true);

        villager.setProfession(Villager.Profession.TOOLSMITH);
        villager.setVillagerExperience(1);
    }
}
