package me.nils.vdvcraftrevamp.items.armor;

import me.nils.vdvcraftrevamp.constants.Ability;
import me.nils.vdvcraftrevamp.constants.Pattern;
import me.nils.vdvcraftrevamp.constants.Tier;
import me.nils.vdvcraftrevamp.constants.Trim;
import me.nils.vdvcraftrevamp.managers.ArmorManager;
import org.bukkit.Material;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;

public class Shirt extends ArmorManager {
    public Shirt() {
        super(Material.LEATHER_CHESTPLATE, Trim.QUARTZ, Pattern.RAISER, "Shirt", Ability.NONE, Tier.BASIC, 0, 1, 0, 0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST);
    }
}
