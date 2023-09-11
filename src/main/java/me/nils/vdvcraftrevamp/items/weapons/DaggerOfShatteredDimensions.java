package me.nils.vdvcraftrevamp.items.weapons;

import me.nils.vdvcraftrevamp.constants.Ability;
import me.nils.vdvcraftrevamp.constants.Tier;
import me.nils.vdvcraftrevamp.managers.WeaponManager;
import org.bukkit.Material;

public class DaggerOfShatteredDimensions extends WeaponManager {
    public DaggerOfShatteredDimensions() {
        super(Material.NETHERITE_SWORD,"Dagger Of Shattered Dimensions", Ability.DIMENSION_SHATTER, Tier.CURSED, 10);
    }
}
