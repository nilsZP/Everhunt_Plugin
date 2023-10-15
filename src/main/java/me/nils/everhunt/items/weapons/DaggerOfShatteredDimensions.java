package me.nils.everhunt.items.weapons;

import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.constants.Tier;
import me.nils.everhunt.managers.WeaponManager;
import org.bukkit.Material;

public class DaggerOfShatteredDimensions extends WeaponManager {
    public DaggerOfShatteredDimensions() {
        super(Material.NETHERITE_SWORD,"Dagger Of Shattered Dimensions", Ability.DIMENSION_SHATTER, Tier.CURSED, 10);
    }
}
