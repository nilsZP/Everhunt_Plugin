package me.nils.vdvcraftrevamp.items.armor;

import me.nils.vdvcraftrevamp.constants.Ability;
import me.nils.vdvcraftrevamp.constants.Pattern;
import me.nils.vdvcraftrevamp.constants.Tier;
import me.nils.vdvcraftrevamp.constants.Trim;
import me.nils.vdvcraftrevamp.managers.ArmorManager;
import org.bukkit.Material;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;

public class MechanicalChestplate extends ArmorManager {
    public MechanicalChestplate() {
        super(Material.CHAINMAIL_CHESTPLATE, Trim.COPPER, Pattern.SENTRY,"Mechanical Chestplate", Ability.MECHANICAL_SHOT, Tier.C,4,4,2,1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST);
    }
}
