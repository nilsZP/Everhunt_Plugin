package me.nils.vdvcraftrevamp.items.weapons;

import me.nils.vdvcraftrevamp.constants.Ability;
import me.nils.vdvcraftrevamp.constants.Flow;
import me.nils.vdvcraftrevamp.constants.Tier;
import me.nils.vdvcraftrevamp.managers.WeaponManager;
import org.bukkit.Material;

public class AzureWrath extends WeaponManager {
    public AzureWrath() {
        super(Material.TRIDENT,"AzureWrath", Ability.THUNDER_WARP, Tier.A,9, Flow.SURGING);
    }
}
