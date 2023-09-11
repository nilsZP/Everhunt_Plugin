package me.nils.vdvcraftrevamp.items.armor;

import me.nils.vdvcraftrevamp.constants.Ability;
import me.nils.vdvcraftrevamp.constants.Pattern;
import me.nils.vdvcraftrevamp.constants.Tier;
import me.nils.vdvcraftrevamp.constants.Trim;
import me.nils.vdvcraftrevamp.managers.ArmorManager;
import org.bukkit.Material;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;

public class SpringerBoots extends ArmorManager {
    public SpringerBoots() {
        super(Material.CHAINMAIL_BOOTS, Trim.NONE, Pattern.NONE,"Springer Boots", Ability.SPRING, Tier.Natural,2,2,0,0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET);
    }
}
