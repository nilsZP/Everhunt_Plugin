package me.nils.vdvcraftrevamp.items.armor;

import me.nils.vdvcraftrevamp.constants.Ability;
import me.nils.vdvcraftrevamp.constants.Pattern;
import me.nils.vdvcraftrevamp.constants.Rarity;
import me.nils.vdvcraftrevamp.constants.Trim;
import me.nils.vdvcraftrevamp.managers.ArmorManager;
import org.bukkit.Material;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;

public class MechanicalChestplate extends ArmorManager {
    public MechanicalChestplate() {
        super(Material.CHAINMAIL_CHESTPLATE, Trim.COPPER, Pattern.SENTRY,"Mechanical Chestplate", Ability.MECHANICAL_SHOT, Rarity.GRAND,4,4,2,1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST);
    }
}
