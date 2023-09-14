package me.nils.vdvcraftrevamp.items.armor;

import me.nils.vdvcraftrevamp.constants.Ability;
import me.nils.vdvcraftrevamp.constants.Tier;
import me.nils.vdvcraftrevamp.managers.LArmorManager;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;

public class Boots extends LArmorManager {
    public Boots() {
        super(Material.LEATHER_BOOTS, Color.BLACK,"Boots", Ability.NONE, Tier.BASIC,0,1,0,0, EquipmentSlot.FEET);
    }
}
