package me.nils.everhunt.items.weapons;

import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.constants.Tier;
import me.nils.everhunt.managers.WeaponManager;
import org.bukkit.Material;

public class LuciaV extends WeaponManager {
    public LuciaV() {
        super(Material.IRON_SWORD,"Lucia V", Ability.THUNDER_CLAP, Tier.MAGICAL,7);
    }
}
