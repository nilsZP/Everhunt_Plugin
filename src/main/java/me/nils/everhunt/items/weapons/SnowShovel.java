package me.nils.everhunt.items.weapons;

import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.constants.Tier;
import me.nils.everhunt.managers.WeaponManager;
import org.bukkit.Material;

public class SnowShovel extends WeaponManager {
    public SnowShovel() {
        super(Material.IRON_SHOVEL,"Snow Shovel", Ability.SNOWBALL, Tier.BASIC, 3);
    }
}
