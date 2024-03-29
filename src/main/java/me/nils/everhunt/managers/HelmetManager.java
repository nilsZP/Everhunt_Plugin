package me.nils.everhunt.managers;

import me.nils.everhunt.Everhunt;
import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.constants.Tier;
import me.nils.everhunt.utils.Chat;
import me.nils.everhunt.utils.Head;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

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
    private final double flow;
    private final String url;


    private final ItemStack itemStack;

    public HelmetManager(String displayName, Ability ability, Tier tier, double health, double armor, double toughness, double damage,double flow, String url) {
        this.ability = ability;
        this.displayName = displayName;
        this.tier = tier;
        this.armor = armor;
        this.toughness = toughness;
        this.health = health;
        this.damage = damage;
        this.flow = flow;
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
        if (flow != 0) {
            modifier = new AttributeModifier(UUID.randomUUID(), "flow", flow, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD);
            meta.addAttributeModifier(Attribute.GENERIC_MAX_ABSORPTION, modifier);
        }
        meta.setUnbreakable(true);

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
        lore.add(tier.getColor() + String.valueOf(tier) + " HELMET");

        meta.setLore(lore);

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);

        itemStack.setItemMeta(meta);

        items.put(displayName, this);

        Everhunt.items.add(this);

        try {
            ResultSet check = Everhunt.getDatabase().run("SELECT count(*) FROM tblhelmet WHERE displayname = '" + displayName + "'").executeQuery();
            check.next();
            if (check.getInt(1) < 1) {
                Everhunt.getDatabase().run("INSERT INTO tblhelmet (displayname,ability,tier,health,armor,toughness,damage,flow,url) VALUES ('" +
                        displayName + "','" + ability + "','" + tier + "','" + health + "','" + armor + "','" + toughness + "','" + damage + "','" + flow + "','" + url + "')").executeUpdate();
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
                double flow = resultSet.getDouble("flow");
                String url = resultSet.getString("url");

                new HelmetManager(displayName, ability, tier, health, armor, toughness, damage,flow,url);
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

    public double getToughness() {
        return toughness;
    }

    public double getDamage() {
        return damage;
    }
}

