package me.nils.everhunt.items.items;

import me.nils.everhunt.constants.Tier;
import me.nils.everhunt.managers.ItemManager;
import org.bukkit.Material;

public class KingsBone extends ItemManager {
    public KingsBone() {
        super(Material.BONE,"Kings Bone", Tier.NATURAL);
    }
}
