package me.nils.everhunt.items.armor;

import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.constants.Pattern;
import me.nils.everhunt.constants.Tier;
import me.nils.everhunt.constants.Trim;
import me.nils.everhunt.managers.ArmorManager;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.EquipmentSlot;

public class UnitedHelmet extends ArmorManager {
    public UnitedHelmet() {
        super(Material.DIAMOND_HELMET, Color.BLACK, Trim.EMERALD, Pattern.TIDE,"United Helmet", Ability.NONE, Tier.SOUL, 10,4, 2, 1, EquipmentSlot.HEAD,false);
    }
}