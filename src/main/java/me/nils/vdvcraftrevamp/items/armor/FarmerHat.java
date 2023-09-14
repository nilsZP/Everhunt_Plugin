package me.nils.vdvcraftrevamp.items.armor;

import me.nils.vdvcraftrevamp.constants.Ability;
import me.nils.vdvcraftrevamp.constants.Tier;
import me.nils.vdvcraftrevamp.managers.ArmorManager;
import me.nils.vdvcraftrevamp.managers.LArmorManager;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Wither;
import org.bukkit.inventory.EquipmentSlot;

public class FarmerHat extends LArmorManager {
    public FarmerHat() {
       super(Material.LEATHER_HELMET, Color.YELLOW,"Farmer Hat", Ability.NONE, Tier.NATURAL,1,1,0,0, EquipmentSlot.HEAD);
    }
}
