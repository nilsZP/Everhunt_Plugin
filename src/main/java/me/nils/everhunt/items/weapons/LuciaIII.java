package me.nils.everhunt.items.weapons;

import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.constants.Tier;
import me.nils.everhunt.managers.WeaponManager;
import org.bukkit.Material;

public class LuciaIII extends WeaponManager {
    public LuciaIII() {
        super(Material.STONE_SWORD,"Lucia III", Ability.THUNDER_CLAP, Tier.MAGICAL,5);
    }
}
