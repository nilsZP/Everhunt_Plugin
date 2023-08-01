package me.nils.vdvcraftrevamp.items.weapons;

import me.nils.vdvcraftrevamp.constants.Ability;
import me.nils.vdvcraftrevamp.constants.Flow;
import me.nils.vdvcraftrevamp.constants.Rarity;
import me.nils.vdvcraftrevamp.managers.WeaponManager;
import org.bukkit.Material;

public class LuciaIV extends WeaponManager {
    public LuciaIV() {
        super(Material.IRON_SWORD,"Lucia IV", Ability.THUNDER_CLAP, Rarity.EXCELLENT,6, Flow.SURGING);
    }
}
