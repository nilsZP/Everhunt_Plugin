package me.nils.vdvcraftrevamp.items.weapons;

import me.nils.vdvcraftrevamp.constants.Ability;
import me.nils.vdvcraftrevamp.constants.Flow;
import me.nils.vdvcraftrevamp.constants.Rarity;
import me.nils.vdvcraftrevamp.managers.WeaponManager;
import org.bukkit.Material;

public class LuciaII extends WeaponManager {
    public LuciaII() {
        super(Material.STONE_SWORD,"Lucia II", Ability.NONE, Rarity.UNIQUE,5, Flow.SURGING);
    }
}
