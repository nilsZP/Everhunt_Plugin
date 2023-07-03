package me.nils.vdvcraftrevamp.items.materials;

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

public class Materials {
    public ItemStack RippedDimension() {
        ItemStack item = new ItemStack(Material.ECHO_SHARD);
        ItemMeta meta = item.getItemMeta();
        Rarity rarity = Rarity.LEGENDARY;

        Ability ability = Ability.NONE;
        meta.getPersistentDataContainer().set(VDVCraftRevamp.getKey(), PersistentDataType.STRING, ability.getName());

        meta.setDisplayName(rarity.getColor() + "Ripped Dimension");

        List<String> lore = getLore(ability,rarity);

        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack UnisonShard() {
        ItemStack item = new ItemStack(Material.PRISMARINE_SHARD);
        ItemMeta meta = item.getItemMeta();
        Rarity rarity = Rarity.EPIC;

        Ability ability = Ability.NONE;
        meta.getPersistentDataContainer().set(VDVCraftRevamp.getKey(), PersistentDataType.STRING, ability.getName());

        meta.setDisplayName(rarity.getColor() + "Unison Shard");

        List<String> lore = getLore(ability,rarity);

        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack ShatteredShard() {
        ItemStack item = new ItemStack(Material.PRISMARINE_CRYSTALS);
        ItemMeta meta = item.getItemMeta();
        Rarity rarity = Rarity.EPIC;

        Ability ability = Ability.NONE;
        meta.getPersistentDataContainer().set(VDVCraftRevamp.getKey(), PersistentDataType.STRING, ability.getName());

        meta.setDisplayName(rarity.getColor() + "Shattered Shard");

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
