package me.nils.everhunt.items.armor;

import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.constants.Tier;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.EquipmentSlot;

public class FarmerHat extends LArmorManager {
    public FarmerHat() {
       super(Material.LEATHER_HELMET, Color.YELLOW,"Farmer Hat", Ability.NONE, Tier.NATURAL,1,1,0,0, EquipmentSlot.HEAD);
    }
}
