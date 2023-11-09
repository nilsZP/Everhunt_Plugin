package me.nils.everhunt.items.weapons;

import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.constants.Tier;
import me.nils.everhunt.managers.WeaponManager;
import org.bukkit.Material;

public class LuciaIV extends WeaponManager {
    public LuciaIV() {
        super(Material.IRON_SWORD,"Lucia IV", Ability.THUNDER_CLAP, Tier.MAGICAL,6);
    }
}
