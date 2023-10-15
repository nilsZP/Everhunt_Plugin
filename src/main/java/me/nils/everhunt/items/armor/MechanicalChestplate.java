package me.nils.everhunt.items.armor;

import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.constants.Pattern;
import me.nils.everhunt.constants.Tier;
import me.nils.everhunt.constants.Trim;
import me.nils.everhunt.managers.ArmorManager;
import org.bukkit.Material;
import org.bukkit.inventory.EquipmentSlot;

public class MechanicalChestplate extends ArmorManager {
    public MechanicalChestplate() {
        super(Material.CHAINMAIL_CHESTPLATE, Trim.COPPER, Pattern.SENTRY,"Mechanical Chestplate", Ability.MECHANICAL_SHOT, Tier.MECHANICAL,4,4,2,1, EquipmentSlot.CHEST);
    }
}
