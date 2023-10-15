package me.nils.everhunt.items.armor;

import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.constants.Pattern;
import me.nils.everhunt.constants.Tier;
import me.nils.everhunt.constants.Trim;
import me.nils.everhunt.managers.ArmorManager;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.EquipmentSlot;

public class Pants extends ArmorManager {
    public Pants() {
        super(Material.LEATHER_LEGGINGS, Color.BLUE, Trim.NONE, Pattern.NONE ,"Pants", Ability.NONE, Tier.BASIC,0,1,0,0, EquipmentSlot.LEGS,true);
    }
}
