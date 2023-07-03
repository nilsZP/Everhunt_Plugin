package me.nils.vdvcraftrevamp.items.weapons;

import me.nils.vdvcraftrevamp.VDVCraftRevamp;
import me.nils.vdvcraftrevamp.constants.Ability;
import me.nils.vdvcraftrevamp.constants.Rarity;
import me.nils.vdvcraftrevamp.utils.Chat;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Weapons {

    public ItemStack MeteorBlade() {
        ItemStack item = new ItemStack(Material.GOLDEN_SWORD);
        ItemMeta meta = item.getItemMeta();
        Rarity rarity = Rarity.EPIC;

        Ability ability = Ability.METEOR_BLAST;
        meta.getPersistentDataContainer().set(VDVCraftRevamp.getKey(),PersistentDataType.STRING, ability.getName());

        AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(),"damage",7, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE,modifier);
        modifier = new AttributeModifier(UUID.randomUUID(),"speed",-2.4, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED,modifier);

        meta.setDisplayName(rarity.getColor() + "Meteor Blade");

        List<String> lore = getLore(ability,rarity);

        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack AzureWrath() {
        ItemStack item = new ItemStack(Material.TRIDENT);
        ItemMeta meta = item.getItemMeta();
        Rarity rarity = Rarity.LEGENDARY;

        Ability ability = Ability.THUNDER_WARP;
        meta.getPersistentDataContainer().set(VDVCraftRevamp.getKey(),PersistentDataType.STRING, ability.getName());

        AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(),"damage",5, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE,modifier);
        modifier = new AttributeModifier(UUID.randomUUID(),"speed",-2.4, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED,modifier);

        meta.setDisplayName(rarity.getColor() + "AzureWrath");

        List<String> lore = getLore(ability,rarity);

        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public static List<String> getLore(Ability ability, Rarity rarity) {
        List<String> lore = new ArrayList<>();
        if (!(ability == Ability.NONE)) {
            lore.add(Chat.color("&6Ability: " + ability.getName() + " &e&lRIGHT CLICK"));
            lore.add(Chat.color("&8Cooldown: &3" + ability.getCooldown()));
        }
        lore.add(Chat.color("&r"));
        lore.add(rarity.getColor() + String.valueOf(rarity));

        return lore;
    }
}
