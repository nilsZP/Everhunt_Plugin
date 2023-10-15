package me.nils.everhunt.items.weapons;

import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.constants.Tier;
import me.nils.everhunt.managers.WeaponManager;
import org.bukkit.Material;

public class DaggerOfUnitedDimensions extends WeaponManager {
    public DaggerOfUnitedDimensions() {
        super(Material.DIAMOND_SWORD,"Dagger Of United Dimensions", Ability.DIMENSION_UNISON, Tier.SOUL, 10);
    }
}
