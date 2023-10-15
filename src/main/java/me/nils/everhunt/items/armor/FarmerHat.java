package me.nils.everhunt.items.armor;

import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.constants.Pattern;
import me.nils.everhunt.constants.Tier;
import me.nils.everhunt.constants.Trim;
import me.nils.everhunt.managers.ArmorManager;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.EquipmentSlot;

public class FarmerHat extends ArmorManager {
    public FarmerHat() {
       super(Material.LEATHER_HELMET, Color.YELLOW, Trim.NONE, Pattern.NONE,"Farmer Hat", Ability.NONE, Tier.NATURAL,1,1,0,0, EquipmentSlot.HEAD,true);
    }
}
