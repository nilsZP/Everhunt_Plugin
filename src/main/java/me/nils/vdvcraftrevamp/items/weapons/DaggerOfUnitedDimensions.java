package me.nils.vdvcraftrevamp.items.weapons;

import me.nils.vdvcraftrevamp.constants.Ability;
import me.nils.vdvcraftrevamp.constants.Flow;
import me.nils.vdvcraftrevamp.constants.Rarity;
import me.nils.vdvcraftrevamp.managers.WeaponManager;
import org.bukkit.Material;

public class DaggerOfUnitedDimensions extends WeaponManager {
    public DaggerOfUnitedDimensions() {
        super(Material.DIAMOND_SWORD,"Dagger Of United Dimensions", Ability.DIMENSION_UNISON, Rarity.GIFTED, 10, Flow.WAVING);
    }
}
