package me.nils.everhunt.managers;

import me.nils.everhunt.Everhunt;
import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.constants.Pattern;
import me.nils.everhunt.constants.Tier;
import me.nils.everhunt.constants.Trim;
import me.nils.everhunt.data.PlayerData;
import me.nils.everhunt.utils.Chat;
import me.nils.everhunt.utils.Head;
import net.kyori.adventure.text.Component;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.persistence.PersistentDataType;

import java.io.File;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class HelmetManager {
    public static final HashMap<String, HelmetManager> items = new HashMap<>();

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

    private final String displayName;
    private final Tier tier;
    private final Ability ability;
    private final double armor;
    private final double toughness;
    private final double health;
    private final double damage;
    private final URL url;


    private final ItemStack itemStack;

    public HelmetManager(String displayName, Ability ability, Tier tier, double health, double armor, double toughness, double damage, URL url) {
        this.ability = ability;
        this.displayName = displayName;
        this.tier = tier;
        this.armor = armor;
        this.toughness = toughness;
        this.health = health;
        this.damage = damage;
        this.url = url;

        itemStack = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) itemStack.getItemMeta();
        meta.setPlayerProfile(Head.createTexture(url));
        meta.displayName(Component.text(tier.getColor() + displayName));
        meta.getPersistentDataContainer().set(Everhunt.getKey(), PersistentDataType.STRING, displayName);

        AttributeModifier modifier;
        if (health != 0) {
            modifier = new AttributeModifier(UUID.randomUUID(), "health", health, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD);
            meta.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH, modifier);
        }
        if (armor != 0) {
            modifier = new AttributeModifier(UUID.randomUUID(), "armor", armor, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD);
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR, modifier);
        }
        if (toughness != 0) {
            modifier = new AttributeModifier(UUID.randomUUID(), "toughness", toughness, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD);
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, modifier);
        }
        if (damage != 0) {
            modifier = new AttributeModifier(UUID.randomUUID(), "damage", damage, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD);
            meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);
        }
        meta.setUnbreakable(true);

        List<String> lore = new ArrayList<>();
        if (!(ability == Ability.NONE)) {
            String action = ability.getActivation().getAction();
            lore.add(Chat.color("&6Ability: " + ability.getName() + " &e&l" + action));
            if (ability.getCooldown() != 0) {
                lore.add(Chat.color("&8Cooldown: &3" + ability.getCooldown()));
            }
            if (ability.getFlowCost() != 0) {
                lore.add(Chat.color("&8Cost: &3" + ability.getFlowCost()));
            }
        }
        lore.add(Chat.color("&r"));
        lore.add(tier.getColor() + String.valueOf(tier) + " HELMET");

        meta.setLore(lore);
        itemStack.setItemMeta(meta);

        items.put(displayName, this);

        Everhunt.items.add(this);

        try {
            ResultSet check = Everhunt.getDatabase().run("SELECT count(*) FROM tblhelmet WHERE displayname = '" + displayName + "'").executeQuery();
            check.next();
            if (check.getInt(1) < 1) {
                Everhunt.getDatabase().run("INSERT INTO tblhelmet (displayname,ability,tier,health,armor,toughness,damage,url) VALUES ('" +
                        displayName + "','" + ability + "','" + tier + "','" + health + "','" + armor + "','" + toughness + "','" + damage + "," + url + "')").executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void registerItems() {
        try {
            ResultSet resultSet = Everhunt.getDatabase().run("SELECT * FROM tblhelmet").executeQuery();

            while (resultSet.next()) {
                String displayName = resultSet.getString("displayname");
                Tier tier = Tier.valueOf(resultSet.getString("tier"));
                Ability ability = Ability.valueOf(resultSet.getString("ability"));
                double health = resultSet.getDouble("health");
                double armor = resultSet.getDouble("armor");
                double toughness = resultSet.getDouble("toughness");
                double damage = resultSet.getDouble("damage");
                URL url = resultSet.getURL("url");

                new HelmetManager(displayName, ability, tier, health, armor, toughness, damage,url);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getArmorID() {
        try {
            ResultSet resultSet = Everhunt.getDatabase().run("SELECT * FROM tblhelmet WHERE displayname = '" + displayName + "'").executeQuery();

            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int getArmorID(String displayName) {
        try {
            ResultSet resultSet = Everhunt.getDatabase().run("SELECT * FROM tblhelmet WHERE displayname = '" + displayName + "'").executeQuery();

            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public double getHealth() {
        return health;
    }

    public double getarmor() {
        return armor;
    }

    public double getToughness() {
        return toughness;
    }

    public double getDamage() {
        return damage;
    }
}

