package me.nils.vdvcraftrevamp.items.weapons;

import me.nils.vdvcraftrevamp.constants.Ability;
import me.nils.vdvcraftrevamp.constants.Flow;
import me.nils.vdvcraftrevamp.constants.Rarity;
import me.nils.vdvcraftrevamp.managers.WeaponManager;
import org.bukkit.Material;

public class LuciaVI extends WeaponManager {
    public LuciaVI() {
        super(Material.GOLDEN_SWORD,"Lucia VI", Ability.THUNDER_FLASH, Rarity.GRAND,7, Flow.SURGING);
    }
}
