package me.nils.vdvcraftrevamp.managers;

import me.nils.vdvcraftrevamp.VDVCraftRevamp;
import me.nils.vdvcraftrevamp.constants.Ability;
import me.nils.vdvcraftrevamp.constants.Pattern;
import me.nils.vdvcraftrevamp.constants.Tier;
import me.nils.vdvcraftrevamp.constants.Trim;
import me.nils.vdvcraftrevamp.utils.Chat;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;
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

    public Tier getTier() {
        return tier;
    }

    public Ability getAbility() {
        return ability;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    private final Material material;
    private final Trim trim;
    private final Pattern pattern;
    private final String displayName;
    private final Tier tier;
    private final Ability ability;
    private final double armor;
    private final double toughness;
    private final double health;
    private final double damage;
    private final EquipmentSlot slot;
    private final AttributeModifier.Operation operation;


    private final ItemStack itemStack;
    private final YamlConfiguration configuration;

    public ArmorManager(Material material, Trim trim, Pattern pattern, String displayName, Ability ability, Tier tier, double health, double armor, double toughness, double damage, AttributeModifier.Operation operation, EquipmentSlot slot) {
        this.ability = ability;
        this.displayName = displayName;
        this.material = material;
        this.trim = trim;
        this.pattern = pattern;
        this.tier = tier;
        this.armor = armor;
        this.toughness = toughness;
        this.health = health;
        this.slot = slot;
        this.damage = damage;
        this.operation = operation;

        itemStack = new ItemStack(material);
        ArmorMeta meta = (ArmorMeta) itemStack.getItemMeta();
        meta.displayName(Component.text(tier.getColor() + displayName));
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
        if (trim != Trim.NONE && pattern != Pattern.NONE) {
            meta.setTrim(new ArmorTrim(trim.getMaterial(),pattern.getPattern()));
        }

        List<String> lore = new ArrayList<>();
        if (!(ability == Ability.NONE)) {
            String action = ability.getActivation().getAction();
            lore.add(Chat.color("&6Ability: " + ability.getName() + " &e&l" + action));
            lore.add(Chat.color("&8Cooldown: &3" + ability.getCooldown()));
        }
        lore.add(Chat.color("&r"));
        lore.add(tier.getColor() + String.valueOf(tier) + " ARMOR");

        meta.setLore(lore);
        itemStack.setItemMeta(meta);

        FileManager fileManager = new FileManager("armor", displayName);
        configuration = fileManager.getFile();
        configuration.set("material", material.toString());
        configuration.set("trim", trim.toString());
        configuration.set("pattern", pattern.toString());
        configuration.set("displayName", displayName);
        configuration.set("tier", tier.toString());
        configuration.set("ability", ability.toString());
        configuration.set("health",String.valueOf(health));
        configuration.set("armor", String.valueOf(armor));
        configuration.set("toughness", String.valueOf(toughness));
        configuration.set("damage", String.valueOf(damage));
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
            Trim trim = Trim.valueOf(fileConfiguration.getString("trim"));
            Pattern pattern = Pattern.valueOf(fileConfiguration.getString("pattern"));
            String displayName = fileConfiguration.getString("displayName");
            Tier tier = Tier.valueOf(fileConfiguration.getString("tier"));
            Ability ability = Ability.valueOf(fileConfiguration.getString("ability"));
            double health = (fileConfiguration.getDouble("health"));
            double armor = (fileConfiguration.getDouble("armor"));
            double toughness = (fileConfiguration.getDouble("toughness"));
            double damage = (fileConfiguration.getDouble("damage"));
            AttributeModifier.Operation operation = AttributeModifier.Operation.valueOf(fileConfiguration.getString("operation"));
            EquipmentSlot slot = EquipmentSlot.valueOf(fileConfiguration.getString("slot"));

            new ArmorManager(material, trim, pattern, displayName, ability, tier, health, armor, toughness, damage, operation, slot);
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

    public Trim getTrim() {
        return trim;
    }

    public Pattern getPattern() {
        return pattern;
    }
}

