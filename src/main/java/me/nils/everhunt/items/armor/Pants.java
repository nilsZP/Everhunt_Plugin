package me.nils.everhunt.items.armor;

import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.constants.Tier;
import me.nils.everhunt.managers.LArmorManager;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.EquipmentSlot;

public class Pants extends LArmorManager {
    public Pants() {
        super(Material.LEATHER_LEGGINGS, Color.BLUE,"Pants", Ability.NONE, Tier.BASIC,0,1,0,0, EquipmentSlot.LEGS);
    }
}
