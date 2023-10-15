package me.nils.everhunt.items.weapons;

import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.constants.Tier;
import me.nils.everhunt.managers.WeaponManager;
import org.bukkit.Material;

public class LuciaII extends WeaponManager {
    public LuciaII() {
        super(Material.STONE_SWORD,"Lucia II", Ability.NONE, Tier.BASIC,5);
    }
}
