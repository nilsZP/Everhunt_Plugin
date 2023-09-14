package me.nils.vdvcraftrevamp.items.armor;

import me.nils.vdvcraftrevamp.constants.Ability;
import me.nils.vdvcraftrevamp.constants.Tier;
import me.nils.vdvcraftrevamp.managers.LArmorManager;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;

public class Pants extends LArmorManager {
    public Pants() {
        super(Material.LEATHER_LEGGINGS, Color.BLUE,"Pants", Ability.NONE, Tier.BASIC,0,1,0,0, EquipmentSlot.LEGS);
    }
}
