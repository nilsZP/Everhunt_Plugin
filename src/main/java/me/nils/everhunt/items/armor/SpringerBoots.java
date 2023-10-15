package me.nils.everhunt.items.armor;

import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.constants.Pattern;
import me.nils.everhunt.constants.Tier;
import me.nils.everhunt.constants.Trim;
import me.nils.everhunt.managers.ArmorManager;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.EquipmentSlot;

public class SpringerBoots extends ArmorManager {
    public SpringerBoots() {
        super(Material.CHAINMAIL_BOOTS, Color.BLACK, Trim.NONE, Pattern.NONE,"Springer Boots", Ability.SPRING, Tier.NATURAL,2,2,0,0, EquipmentSlot.FEET,false);
    }
}
