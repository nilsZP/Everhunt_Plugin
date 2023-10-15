package me.nils.everhunt.items.weapons;

import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.constants.Tier;
import me.nils.everhunt.managers.WeaponManager;
import org.bukkit.Material;

public class Evokus extends WeaponManager {
    public Evokus() {
        super(Material.NETHERITE_SHOVEL,"Evokus", Ability.EVOCATION, Tier.CURSED,9);
    }
}
