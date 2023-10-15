package me.nils.everhunt.items.weapons;

import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.constants.Tier;
import me.nils.everhunt.managers.WeaponManager;
import org.bukkit.Material;

public class Nixeus extends WeaponManager {
    public Nixeus() {
        super(Material.WOODEN_HOE,"Nixeus", Ability.LETHAL_ABSORPTION, Tier.CURSED, 2);
    }
}
