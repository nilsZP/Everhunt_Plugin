package me.nils.everhunt.items.armor;

import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.constants.Tier;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.EquipmentSlot;

public class Shirt extends LArmorManager {
    public Shirt() {
        super(Material.LEATHER_CHESTPLATE, Color.RED, "Shirt", Ability.NONE, Tier.BASIC, 0, 1, 0, 0, EquipmentSlot.CHEST);
    }
}
