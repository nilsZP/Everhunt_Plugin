package me.nils.vdvcraftrevamp.items.weapons;

import me.nils.vdvcraftrevamp.constants.Ability;
import me.nils.vdvcraftrevamp.constants.Flow;
import me.nils.vdvcraftrevamp.constants.Rarity;
import me.nils.vdvcraftrevamp.managers.WeaponManager;
import org.bukkit.Material;

public class Evokus extends WeaponManager {
    public Evokus() {
        super(Material.NETHERITE_SHOVEL,"Evokus", Ability.EVOCATION, Rarity.CURSED,9, Flow.QUAKING);
    }
}
