package me.nils.vdvcraftrevamp.items.weapons;

import me.nils.vdvcraftrevamp.constants.Ability;
import me.nils.vdvcraftrevamp.constants.Flow;
import me.nils.vdvcraftrevamp.constants.Tier;
import me.nils.vdvcraftrevamp.managers.WeaponManager;
import org.bukkit.Material;

public class SnowShovel extends WeaponManager {
    public SnowShovel() {
        super(Material.IRON_SHOVEL,"Snow Shovel", Ability.SNOWBALL, Tier.E, 3, Flow.FREEZING);
    }
}
