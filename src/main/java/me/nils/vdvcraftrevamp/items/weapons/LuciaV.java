package me.nils.vdvcraftrevamp.items.weapons;

import me.nils.vdvcraftrevamp.constants.Ability;
import me.nils.vdvcraftrevamp.constants.Flow;
import me.nils.vdvcraftrevamp.constants.Rarity;
import me.nils.vdvcraftrevamp.managers.WeaponManager;
import org.bukkit.Material;

public class LuciaV extends WeaponManager {
    public LuciaV() {
        super(Material.IRON_SWORD,"Lucia V", Ability.THUNDER_CLAP, Rarity.EXCELLENT,7, Flow.SURGING);
    }
}
