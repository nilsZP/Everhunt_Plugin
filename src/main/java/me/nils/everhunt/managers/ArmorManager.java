package me.nils.everhunt.managers;

import me.nils.everhunt.Everhunt;
import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.constants.Pattern;
import me.nils.everhunt.constants.Tier;
import me.nils.everhunt.constants.Trim;
import me.nils.everhunt.data.PlayerData;
import me.nils.everhunt.utils.Chat;
import net.kyori.adventure.text.Component;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.persistence.PersistentDataType;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    private final EquipmentSlot slot;
    private final boolean leather;


    private final ItemStack itemStack;

    public ArmorManager(Material material, Color color, Trim trim, Pattern pattern, String displayName, Ability ability, Tier tier, double health, double armor, double toughness, double damage, EquipmentSlot slot, boolean leather) {
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
        this.leather = leather;

        itemStack = new ItemStack(material);
        if (leather) {
            LeatherArmorMeta meta = (LeatherArmorMeta) itemStack.getItemMeta();
            meta.displayName(Component.text(tier.getColor() + displayName));
            meta.getPersistentDataContainer().set(Everhunt.getKey(), PersistentDataType.STRING, displayName);

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
                modifier = new AttributeModifier(UUID.randomUUID(),"damage",damage, AttributeModifier.Operation.ADD_NUMBER, slot);
                meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE,modifier);
            }
            meta.setUnbreakable(true);
            meta.setColor(color);

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
        } else {
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
            meta.setUnbreakable(true);
            if (trim != Trim.NONE && pattern != Pattern.NONE) {
                meta.setTrim(new ArmorTrim(trim.getMaterial(), pattern.getPattern()));
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
        }

        try {
            Everhunt.getDatabase().run("INSERT INTO tblarmor (material,color,trim,pattern,displayname,ability,tier,health,armor,toughness,damage,slot,leather) VALUES ('" + material + "','" + color.asRGB() + "','" + trim + "','" +
                    pattern + "','" + displayName + "','" + ability + "','" + tier + "','" + health + "','" + armor + "','" + toughness + "','" + damage + "','" + slot + "','" + leather + "')").executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        items.put(displayName,this);
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
                EquipmentSlot slot = EquipmentSlot.valueOf(resultSet.getString("slot"));
                boolean leather = resultSet.getBoolean("leather");

                ResultSet check = Everhunt.getDatabase().run("SELECT count(*) FROM tblarmor WHERE displayname = '" + displayName + "'").executeQuery();
                check.next();
                if (check.getInt(1) < 1) {
                    new ArmorManager(material,color,trim,pattern,displayName,ability,tier,health,armor,toughness,damage,slot,leather);
                }
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

