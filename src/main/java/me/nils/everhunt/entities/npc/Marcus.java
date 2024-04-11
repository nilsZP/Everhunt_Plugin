package me.nils.everhunt.entities.npc;

import me.nils.everhunt.constants.MobType;
import me.nils.everhunt.constants.Tier;
import me.nils.everhunt.data.EntityData;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Marcus extends EntityData {
    public Marcus(Location loc) {
        super("Marcus",Tier.BASIC, MobType.NPC);

        Villager villager = (Villager) loc.getWorld().spawnEntity(loc, EntityType.VILLAGER);

        villager.setAI(false);
        villager.setInvulnerable(true);
        villager.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,999999999,254,false,false));

        villager.setCustomName("Marcus");
        villager.setCustomNameVisible(true);

        villager.setProfession(Villager.Profession.WEAPONSMITH);
        villager.setVillagerExperience(1);
    }
}
