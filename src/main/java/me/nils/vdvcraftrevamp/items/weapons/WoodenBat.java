package me.nils.vdvcraftrevamp.items.weapons;

import me.nils.vdvcraftrevamp.constants.Ability;
import me.nils.vdvcraftrevamp.constants.Flow;
import me.nils.vdvcraftrevamp.constants.Tier;
import me.nils.vdvcraftrevamp.managers.WeaponManager;
import org.bukkit.Material;

public class WoodenBat extends WeaponManager {
    public WoodenBat() {
        super(Material.STICK,"Wooden Bat", Ability.NONE, Tier.F, 1, Flow.BLOWING);
    }
}
