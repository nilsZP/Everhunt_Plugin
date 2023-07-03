package me.nils.vdvcraftrevamp.entities;

import me.nils.vdvcraftrevamp.VDVCraftRevamp;
import me.nils.vdvcraftrevamp.constants.Ability;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LightningStrike;
import org.bukkit.persistence.PersistentDataType;

public class ThunderBolt {
    public ThunderBolt(Location loc) {
        LightningStrike thunder = (LightningStrike) loc.getWorld().spawnEntity(loc, EntityType.LIGHTNING);

        Ability ability = Ability.THUNDER_WARP;

        double damage = ability.getAbilityDamage();

        thunder.getPersistentDataContainer().set(VDVCraftRevamp.getKey(), PersistentDataType.DOUBLE, damage);
    }
}
