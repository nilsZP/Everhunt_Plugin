package me.nils.vdvcraftrevamp.items.armor;

import me.nils.vdvcraftrevamp.constants.Ability;
import me.nils.vdvcraftrevamp.constants.Pattern;
import me.nils.vdvcraftrevamp.constants.Tier;
import me.nils.vdvcraftrevamp.constants.Trim;
import me.nils.vdvcraftrevamp.managers.ArmorManager;
import org.bukkit.Material;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;

public class UnitedHelmet extends ArmorManager {
    public UnitedHelmet() {
        super(Material.DIAMOND_HELMET, Trim.EMERALD, Pattern.TIDE,"United Helmet", Ability.NONE, Tier.A, 10,4, 2, 1, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.HEAD);
    }
}
