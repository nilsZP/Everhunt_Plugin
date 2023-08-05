package me.nils.vdvcraftrevamp.items.armor;

import me.nils.vdvcraftrevamp.constants.Ability;
import me.nils.vdvcraftrevamp.constants.Pattern;
import me.nils.vdvcraftrevamp.constants.Rarity;
import me.nils.vdvcraftrevamp.constants.Trim;
import me.nils.vdvcraftrevamp.managers.ArmorManager;
import org.bukkit.Material;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;

public class UnitedHelmet extends ArmorManager {
    public UnitedHelmet() {
        super(Material.DIAMOND_HELMET, Trim.NONE, Pattern.NONE,"United Helmet", Ability.NONE, Rarity.GIFTED, 10,4, 2, 1, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.HEAD);
    }
}
