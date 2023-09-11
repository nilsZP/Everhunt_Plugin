package me.nils.vdvcraftrevamp.entities.npc;

import me.nils.vdvcraftrevamp.constants.Ability;
import me.nils.vdvcraftrevamp.constants.MobType;
import me.nils.vdvcraftrevamp.data.EntityData;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class OldManDave extends EntityData {
    public OldManDave(Location loc) {
        super("Old Man Dave", Ability.NONE, MobType.NPC, Flow.FREEZING);

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
