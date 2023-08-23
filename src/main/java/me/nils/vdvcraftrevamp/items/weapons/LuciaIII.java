package me.nils.vdvcraftrevamp.items.weapons;

import me.nils.vdvcraftrevamp.constants.Ability;
import me.nils.vdvcraftrevamp.constants.Flow;
import me.nils.vdvcraftrevamp.constants.Rarity;
import me.nils.vdvcraftrevamp.managers.WeaponManager;
import org.bukkit.Material;

public class LuciaIII extends WeaponManager {
    public LuciaIII() {
        super(Material.STONE_SWORD,"Lucia III", Ability.THUNDER_CLAP, Rarity.EXCELLENT,5, Flow.SURGING);
    }
}
