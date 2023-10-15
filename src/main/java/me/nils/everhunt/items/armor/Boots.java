package me.nils.everhunt.items.armor;

import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.constants.Tier;
import me.nils.everhunt.managers.LArmorManager;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.EquipmentSlot;

public class Boots extends LArmorManager {
    public Boots() {
        super(Material.LEATHER_BOOTS, Color.BLACK,"Boots", Ability.NONE, Tier.BASIC,0,1,0,0, EquipmentSlot.FEET);
    }
}
