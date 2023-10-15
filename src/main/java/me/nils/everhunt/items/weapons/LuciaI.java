package me.nils.everhunt.items.weapons;

import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.constants.Tier;
import me.nils.everhunt.managers.WeaponManager;
import org.bukkit.Material;

public class LuciaI extends WeaponManager {
    public LuciaI() {
        super(Material.WOODEN_SWORD,"Lucia I", Ability.NONE, Tier.BASIC,4);
    }
}
