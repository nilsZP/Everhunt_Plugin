package me.nils.vdvcraftrevamp.items.weapons;

import me.nils.vdvcraftrevamp.constants.Ability;
import me.nils.vdvcraftrevamp.constants.Flow;
import me.nils.vdvcraftrevamp.constants.Rarity;
import me.nils.vdvcraftrevamp.managers.WeaponManager;
import org.bukkit.Material;

public class SnowShovel extends WeaponManager {
    public SnowShovel() {
        super(Material.IRON_SHOVEL,"Snow Shovel", Ability.SNOWBALL, Rarity.UNIQUE, 3, Flow.FREEZING);
    }
}
