package me.nils.everhunt.items.tools;

import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.constants.Tier;
import me.nils.everhunt.managers.ToolManager;
import org.bukkit.Material;

public class WheatHoe extends ToolManager {
    public WheatHoe() {
        super(Material.GOLDEN_HOE,"Wheat Hoe", Ability.BREAD_MAKER, Tier.NATURAL,1);
    }
}
