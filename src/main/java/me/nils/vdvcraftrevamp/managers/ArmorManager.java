package me.nils.vdvcraftrevamp.managers;

import me.nils.vdvcraftrevamp.VDVCraftRevamp;
import me.nils.vdvcraftrevamp.constants.Ability;
import me.nils.vdvcraftrevamp.constants.Rarity;
import me.nils.vdvcraftrevamp.utils.Chat;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.io.File;
import java.util.*;

public class ArmorManager {
    public static final HashMap<String, ArmorManager> items = new HashMap<>();

    public Material getMaterial() {
        return material;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public Ability getAbility() {
        return ability;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    private final Material material;
    private final String displayName;
    private final Rarity rarity;
    private final Ability ability;
    private final double armor;
    private final double toughness;
    private final double health;
    private final double damage;
    private final EquipmentSlot slot;
    private final AttributeModifier.Operation operation;


    private final ItemStack itemStack;
    private final YamlConfiguration configuration;

    public ArmorManager(Material material, String displayName, Ability ability, Rarity rarity, double health, double armor, double toughness, double damage, AttributeModifier.Operation operation, EquipmentSlot slot) {
        this.ability = ability;
        this.displayName = displayName;
        this.material = material;
        this.rarity = rarity;
        this.armor = armor;
        this.toughness = toughness;
        this.health = health;
        this.slot = slot;
        this.damage = damage;
        this.operation = operation;

        itemStack = new ItemStack(material);
        ItemMeta meta = itemStack.getItemMeta();
        meta.displayName(Component.text(rarity.getColor() + displayName));
        meta.getPersistentDataContainer().set(VDVCraftRevamp.getKey(), PersistentDataType.STRING, displayName);

        AttributeModifier modifier;
        if (health != 0) {
            modifier = new AttributeModifier(UUID.randomUUID(),"health",health, AttributeModifier.Operation.ADD_NUMBER, slot);
            meta.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH,modifier);
        }
        if (armor != 0) {
            modifier = new AttributeModifier(UUID.randomUUID(),"armor",armor, AttributeModifier.Operation.ADD_NUMBER, slot);
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR,modifier);
        }
        if (toughness != 0) {
            modifier = new AttributeModifier(UUID.randomUUID(),"toughness",toughness, AttributeModifier.Operation.ADD_NUMBER, slot);
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS,modifier);
        }
        if (damage != 0) {
            modifier = new AttributeModifier(UUID.randomUUID(),"damage",damage, operation, slot);
            meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE,modifier);
        }
        meta.setUnbreakable(true);

        List<String> lore = new ArrayList<>();
        // lore.add(Chat.color("&7Flow: ") + flow.getColor() + String.valueOf(flow));
        if (!(ability == Ability.NONE)) {
            lore.add(Chat.color("&6Ability: " + ability.getName() + " &e&lSNEAK"));
            lore.add(Chat.color("&8Cooldown: &3" + ability.getCooldown()));
        }
        lore.add(Chat.color("&r"));
        lore.add(rarity.getColor() + String.valueOf(rarity) + " ARMOR");

        meta.setLore(lore);
        itemStack.setItemMeta(meta);

        FileManager fileManager = new FileManager("armor", displayName);
        configuration = fileManager.getFile();
        configuration.set("material", material.toString());
        configuration.set("displayName", displayName);
        configuration.set("rarity", rarity.toString());
        configuration.set("ability", ability.toString());
        configuration.set("health", health);
        configuration.set("armor", armor);
        configuration.set("toughness", toughness);
        configuration.set("damage", damage);
        configuration.set("operation", operation.toString());
        configuration.set("slot",slot.toString());

        fileManager.save();
        items.put(displayName, this);
    }

    public static void registerItems() {
        List<File> files = FileManager.getFiles("armor");
        if (files == null) {
            return;
        }
        for (File file : files) {
            YamlConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);

            Material material = Material.getMaterial(Objects.requireNonNull(fileConfiguration.getString("material")));
            String displayName = fileConfiguration.getString("displayName");
            Rarity rarity = Rarity.valueOf(fileConfiguration.getString("rarity"));
            Ability ability = Ability.valueOf(fileConfiguration.getString("ability"));
            double health = fileConfiguration.getDouble("health");
            double armor = fileConfiguration.getDouble("armor");
            double toughness = fileConfiguration.getDouble("toughness");
            double damage = fileConfiguration.getDouble("damage");
            AttributeModifier.Operation operation = AttributeModifier.Operation.valueOf(fileConfiguration.getString("operation"));
            EquipmentSlot slot = EquipmentSlot.valueOf(fileConfiguration.getString("slot"));

            new ArmorManager(material, displayName, ability, rarity, health, armor, toughness, damage, operation, slot);
        }
    }

    public double getHealth() {
        return health;
    }

    public double getarmor() {
        return armor;
    }

    public EquipmentSlot getSlot() {
        return slot;
    }

    public double getToughness() {
        return toughness;
    }

    public double getDamage() {
        return damage;
    }

    public AttributeModifier.Operation getOperation() {
        return operation;
    }
}

