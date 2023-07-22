package me.nils.vdvcraftrevamp.items.weapons;

import me.nils.vdvcraftrevamp.constants.Ability;
import me.nils.vdvcraftrevamp.constants.Flow;
import me.nils.vdvcraftrevamp.constants.Rarity;
import me.nils.vdvcraftrevamp.managers.WeaponManager;
import org.bukkit.Material;

public class LuciaI extends WeaponManager {
    public LuciaI() {
        super(Material.WOODEN_SWORD,"Lucia I", Ability.NONE, Rarity.UNIQUE,4, Flow.SURGING);
    }
}
