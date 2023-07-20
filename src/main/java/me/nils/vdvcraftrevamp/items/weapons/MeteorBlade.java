package me.nils.vdvcraftrevamp.items.weapons;

import me.nils.vdvcraftrevamp.constants.Ability;
import me.nils.vdvcraftrevamp.constants.Flow;
import me.nils.vdvcraftrevamp.constants.Rarity;
import me.nils.vdvcraftrevamp.managers.WeaponManager;
import org.bukkit.Material;

public class MeteorBlade extends WeaponManager {

    public MeteorBlade() {
        super(Material.GOLDEN_SWORD,"Meteor Blade",Ability.METEOR_BLAST,Rarity.GRAND,7, Flow.BURNING);
    }
}
