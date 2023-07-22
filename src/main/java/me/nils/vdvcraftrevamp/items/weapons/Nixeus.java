package me.nils.vdvcraftrevamp.items.weapons;

import me.nils.vdvcraftrevamp.constants.Ability;
import me.nils.vdvcraftrevamp.constants.Flow;
import me.nils.vdvcraftrevamp.constants.Rarity;
import me.nils.vdvcraftrevamp.managers.WeaponManager;
import org.bukkit.Material;

public class Nixeus extends WeaponManager {
    public Nixeus() {
        super(Material.WOODEN_HOE,"Nixeus", Ability.LETHAL_ABSORPTION, Rarity.CURSED, 2, Flow.WAVING);
    }
}
