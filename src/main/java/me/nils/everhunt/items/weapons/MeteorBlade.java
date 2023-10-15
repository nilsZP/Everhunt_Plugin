package me.nils.everhunt.items.weapons;

import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.constants.Tier;
import me.nils.everhunt.managers.WeaponManager;
import org.bukkit.Material;

public class MeteorBlade extends WeaponManager {

    public MeteorBlade() {
        super(Material.GOLDEN_SWORD,"Meteor Blade",Ability.METEOR_BLAST, Tier.MAGICAL,7);
    }
}
