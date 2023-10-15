package me.nils.everhunt.items.weapons;

import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.constants.Tier;
import me.nils.everhunt.managers.WeaponManager;
import org.bukkit.Material;

public class LuciaVI extends WeaponManager {
    public LuciaVI() {
        super(Material.GOLDEN_SWORD,"Lucia VI", Ability.THUNDER_FLASH, Tier.BASIC,7);
    }
}
