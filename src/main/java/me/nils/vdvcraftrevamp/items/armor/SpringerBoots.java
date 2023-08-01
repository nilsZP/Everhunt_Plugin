package me.nils.vdvcraftrevamp.items.armor;

import me.nils.vdvcraftrevamp.constants.Ability;
import me.nils.vdvcraftrevamp.constants.Rarity;
import me.nils.vdvcraftrevamp.managers.ArmorManager;
import org.bukkit.Material;
import org.bukkit.inventory.EquipmentSlot;

public class SpringerBoots extends ArmorManager {
    public SpringerBoots() {
        super(Material.CHAINMAIL_BOOTS,"Springer Boots", Ability.NONE, Rarity.UNIQUE,2,2,0,0,null, EquipmentSlot.FEET);
    }
}
