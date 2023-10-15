package me.nils.everhunt.items.weapons;

import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.constants.Tier;
import me.nils.everhunt.managers.WeaponManager;
import org.bukkit.Material;

public class AzureWrath extends WeaponManager {
    public AzureWrath() {
        super(Material.TRIDENT,"AzureWrath", Ability.THUNDER_WARP, Tier.A,9);
    }
}
