package me.nils.everhunt.items.items;

import me.nils.everhunt.constants.Tier;
import me.nils.everhunt.managers.ItemManager;
import org.bukkit.Material;

public class WolflingHide extends ItemManager {
    public WolflingHide() {
        super(Material.RABBIT_HIDE,"Wolfling Hide", Tier.NATURAL);
    }
}
