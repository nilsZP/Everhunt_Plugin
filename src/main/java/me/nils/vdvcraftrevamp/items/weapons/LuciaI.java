package me.nils.vdvcraftrevamp.items.weapons;

import me.nils.vdvcraftrevamp.constants.Ability;
import me.nils.vdvcraftrevamp.constants.Flow;
import me.nils.vdvcraftrevamp.constants.Tier;
import me.nils.vdvcraftrevamp.managers.WeaponManager;
import org.bukkit.Material;

public class LuciaI extends WeaponManager {
    public LuciaI() {
        super(Material.WOODEN_SWORD,"Lucia I", Ability.NONE, Tier.F,4, Flow.SURGING);
    }
}
