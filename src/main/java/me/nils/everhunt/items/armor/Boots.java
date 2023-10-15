package me.nils.everhunt.items.armor;

import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.constants.Pattern;
import me.nils.everhunt.constants.Tier;
import me.nils.everhunt.constants.Trim;
import me.nils.everhunt.managers.ArmorManager;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.EquipmentSlot;

public class Boots extends ArmorManager {
    public Boots() {
        super(Material.LEATHER_BOOTS, Color.BLACK, Trim.NONE, Pattern.NONE ,"Boots", Ability.NONE, Tier.BASIC,0,1,0,0, EquipmentSlot.FEET,true);
    }
}
