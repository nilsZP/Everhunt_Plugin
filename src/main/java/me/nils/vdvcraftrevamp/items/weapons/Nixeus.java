package me.nils.vdvcraftrevamp.items.weapons;

import me.nils.vdvcraftrevamp.constants.Ability;
import me.nils.vdvcraftrevamp.constants.Tier;
import me.nils.vdvcraftrevamp.managers.WeaponManager;
import org.bukkit.Material;

public class Nixeus extends WeaponManager {
    public Nixeus() {
        super(Material.WOODEN_HOE,"Nixeus", Ability.LETHAL_ABSORPTION, Tier.CURSED, 2);
    }
}
