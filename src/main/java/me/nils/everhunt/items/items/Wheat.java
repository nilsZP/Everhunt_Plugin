package me.nils.everhunt.items.items;

import me.nils.everhunt.constants.Tier;
import me.nils.everhunt.items.tools.WheatHoe;
import me.nils.everhunt.managers.ItemManager;
import org.bukkit.Material;

public class Wheat extends ItemManager {
    public Wheat() {
        super(Material.WHEAT,"Wheat", Tier.NATURAL,1);
    }
}
