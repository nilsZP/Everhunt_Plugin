package me.nils.everhunt.items.weapons;

import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.constants.Tier;
import me.nils.everhunt.managers.WeaponManager;
import org.bukkit.Material;

public class WoodenBat extends WeaponManager {
    public WoodenBat() {
        super(Material.STICK,"Wooden Bat", Ability.NONE, Tier.BASIC, 1);
    }
}
