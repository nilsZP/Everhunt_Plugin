package me.nils.everhunt.entities.npc;

import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.constants.MobType;
import me.nils.everhunt.constants.Tier;
import me.nils.everhunt.data.EntityData;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class OldManDave extends EntityData {
    public OldManDave(Location loc) {
        super("Old Man Dave",0, Tier.BASIC, Ability.NONE, MobType.NPC);

        Villager villager = (Villager) loc.getWorld().spawnEntity(loc, EntityType.VILLAGER);

        villager.setAI(false);
        villager.setInvulnerable(true);
        villager.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,999999999,254,false,false));

        villager.setCustomName("Old Man Dave");
        villager.setCustomNameVisible(true);

        villager.setProfession(Villager.Profession.NITWIT);
        villager.setVillagerExperience(1);
    }
}
