package me.nils.everhunt.managers;

import me.nils.everhunt.Everhunt;
import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.constants.Pattern;
import me.nils.everhunt.constants.Tier;
import me.nils.everhunt.constants.Trim;
import me.nils.everhunt.utils.Chat;
import net.kyori.adventure.text.Component;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.persistence.PersistentDataType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

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
    private final Color color;
    private final Trim trim;
    private final Pattern pattern;
    private final String displayName;
    private final Tier tier;
    private final Ability ability;
    private final double armor;
    private final double toughness;
    private final double health;
    private final double damage;
    private final double flow;
    private final EquipmentSlot slot;
    private final boolean leather;


    private final ItemStack itemStack;

    public ArmorManager(Material material, Color color, Trim trim, Pattern pattern, String displayName, Ability ability, Tier tier, double health, double armor, double toughness, double damage, double flow, EquipmentSlot slot, boolean leather) {
        this.ability = ability;
        this.displayName = displayName;
        this.material = material;
        this.trim = trim;
        this.pattern = pattern;
        this.color = color;
        this.tier = tier;
        this.armor = armor;
        this.toughness = toughness;
        this.health = health;
        this.slot = slot;
        this.damage = damage;
        this.flow = flow;
        this.leather = leather;

        String type = switch (slot) {
            case HAND -> " HAND";
            case OFF_HAND -> " OFF HAND";
            case FEET -> " BOOTS";
            case LEGS -> " LEGGINGS";
            case CHEST -> " CHESTPLATE";
            case HEAD -> " HELMET";
        };

        itemStack = new ItemStack(material);
        if (leather) {
            LeatherArmorMeta lMeta = (LeatherArmorMeta) itemStack.getItemMeta();
            lMeta.setColor(color);
            itemStack.setItemMeta(lMeta);
        }
        ArmorMeta meta = (ArmorMeta) itemStack.getItemMeta();
        meta.displayName(Component.text(tier.getColor() + displayName));
        meta.getPersistentDataContainer().set(Everhunt.getKey(), PersistentDataType.STRING, displayName);

        AttributeModifier modifier;
        if (health != 0) {
            modifier = new AttributeModifier(UUID.randomUUID(), "health", health, AttributeModifier.Operation.ADD_NUMBER, slot);
            meta.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH, modifier);
        }
        if (armor != 0) {
            modifier = new AttributeModifier(UUID.randomUUID(), "armor", armor, AttributeModifier.Operation.ADD_NUMBER, slot);
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR, modifier);
        }
        if (toughness != 0) {
            modifier = new AttributeModifier(UUID.randomUUID(), "toughness", toughness, AttributeModifier.Operation.ADD_NUMBER, slot);
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, modifier);
        }
        if (damage != 0) {
            modifier = new AttributeModifier(UUID.randomUUID(), "damage", damage, AttributeModifier.Operation.ADD_NUMBER, slot);
            meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);
        }
        if (flow != 0) {
            modifier = new AttributeModifier(UUID.randomUUID(), "flow", flow, AttributeModifier.Operation.ADD_NUMBER, slot);
            meta.addAttributeModifier(Attribute.GENERIC_MAX_ABSORPTION, modifier);
        }
        meta.setUnbreakable(true);
        if (trim != Trim.NONE && pattern != Pattern.NONE) {
            meta.setTrim(new ArmorTrim(trim.getMaterial(), pattern.getPattern()));
        }

        List<String> lore = new ArrayList<>();

        if (health != 0) {
            lore.add(Chat.color("&7Health: &a+" + health));
        }
        if (armor != 0) {
            lore.add(Chat.color("&7Armor: &2+" + armor));
        }
        if (toughness != 0) {
            lore.add(Chat.color("&7Toughness: &8+" + toughness));
        }
        if (damage != 0) {
            lore.add(Chat.color("&7Damage: &4+" + damage));
        }
        if (flow != 0) {
            lore.add(Chat.color("&7Flow: &3+" + flow));
        }

        ability.addAbilityLore(lore);

        lore.add(Chat.color("&r"));
        lore.add(tier.getColor() + String.valueOf(tier) + type);

        meta.setLore(lore);

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);
        meta.addItemFlags(ItemFlag.HIDE_ARMOR_TRIM);
        meta.addItemFlags(ItemFlag.HIDE_DYE);

        itemStack.setItemMeta(meta);

        items.put(displayName, this);

        Everhunt.items.add(this);

        try {
            ResultSet check = Everhunt.getDatabase().run("SELECT count(*) FROM tblarmor WHERE displayname = '" + displayName + "'").executeQuery();
            check.next();
            if (check.getInt(1) < 1) {
                int leatherInt;
                if (leather) {
                    leatherInt = 1;
                } else {
                    leatherInt = 0;
                }
                Everhunt.getDatabase().run("INSERT INTO tblarmor (material,color,trim,pattern,displayname,ability,tier,health,armor,toughness,damage,flow,slot,leather) VALUES ('" + material + "','" + color.asRGB() + "','" + trim + "','" +
                        pattern + "','" + displayName + "','" + ability + "','" + tier + "','" + health + "','" + armor + "','" + toughness + "','" + damage + "','" + flow + "','" + slot + "','" + leatherInt + "')").executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void registerItems() {
        try {
            ResultSet resultSet = Everhunt.getDatabase().run("SELECT * FROM tblarmor").executeQuery();

            while (resultSet.next()) {
                Material material = Material.valueOf(resultSet.getString("material"));
                Color color = Color.fromRGB(resultSet.getInt("color"));
                Trim trim = Trim.valueOf(resultSet.getString("trim"));
                Pattern pattern = Pattern.valueOf(resultSet.getString("pattern"));
                String displayName = resultSet.getString("displayname");
                Tier tier = Tier.valueOf(resultSet.getString("tier"));
                Ability ability = Ability.valueOf(resultSet.getString("ability"));
                double health = resultSet.getDouble("health");
                double armor = resultSet.getDouble("armor");
                double toughness = resultSet.getDouble("toughness");
                double damage = resultSet.getDouble("damage");
                double flow = resultSet.getDouble("flow");
                EquipmentSlot slot = EquipmentSlot.valueOf(resultSet.getString("slot"));
                boolean leather = resultSet.getBoolean("leather");

                new ArmorManager(material, color, trim, pattern, displayName, ability, tier, health, armor, toughness, damage,flow, slot, leather);
            }
        } catch (SQLException e) {
            e.printStackTrace();
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

    public Trim getTrim() {
        return trim;
    }

    public Pattern getPattern() {
        return pattern;
    }
}

