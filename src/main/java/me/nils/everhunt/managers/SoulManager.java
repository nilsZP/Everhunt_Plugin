package me.nils.everhunt.managers;

import me.nils.everhunt.Everhunt;
import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.constants.SoulType;
import me.nils.everhunt.utils.Chat;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class SoulManager {
    public static final HashMap<String, SoulManager> souls = new HashMap<>();
    public Material getMaterial() {
        return material;
    }

    public String getDisplayName() {
        return displayName;
    }

    public SoulType getType() {
        return type;
    }

    public Ability getAbility() {
        return ability;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    private final Material material;
    private final String displayName;
    private final SoulType type;
    private final Ability ability;
    private final double damage;
    private final double flow;
    private final double crit;

    private final ItemStack itemStack;

    public SoulManager(Material material, String displayName, Ability ability, SoulType type, double damage, double flow,double crit) {
        this.ability = ability;
        this.displayName = displayName;
        this.material = material;
        this.type = type;
        this.damage = damage;
        this.flow = flow;
        this.crit = crit;

        itemStack = new ItemStack(material);
        ItemMeta meta = itemStack.getItemMeta();
        meta.getPersistentDataContainer().set(Everhunt.getKey(),PersistentDataType.STRING,displayName);
        meta.displayName(Component.text(type.getColor() + displayName));

        AttributeModifier modifier;
        if (damage != 0) {
            modifier = new AttributeModifier(UUID.randomUUID(),"damage",damage, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.OFF_HAND);
            meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE,modifier);
        }
        if (flow != 0) {
            modifier = new AttributeModifier(UUID.randomUUID(), "flow", flow, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.OFF_HAND);
            meta.addAttributeModifier(Attribute.GENERIC_MAX_ABSORPTION, modifier);
        }
        if (crit != 0) {
            modifier = new AttributeModifier(UUID.randomUUID(), "crit", crit, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.OFF_HAND);
            meta.addAttributeModifier(Attribute.GENERIC_LUCK, modifier);
        }
        meta.setUnbreakable(true);

        List<String> lore = new ArrayList<>();

        if (damage != 0) {
            lore.add(Chat.color("&7Damage: &4+" + damage*100 + "%"));
        }
        if (crit != 0) lore.add(Chat.color("&7Crit Chance: &2+" + crit + "%"));
        if (flow != 0) {
            lore.add(Chat.color("&7Flow: &3+" + flow));
        }

        ability.addAbilityLore(lore);

        lore.add(Chat.color("&r"));
        lore.add(type.getColor() + String.valueOf(type) + " SOUL");

        meta.setLore(lore);

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);

        itemStack.setItemMeta(meta);

        souls.put(displayName,this);

        Everhunt.items.add(this);

        try {
            ResultSet check = Everhunt.getDatabase().run("SELECT count(*) FROM tblsoul WHERE displayname = '" + displayName + "'").executeQuery();
            check.next();
            if (check.getInt(1) < 1) {
                Everhunt.getDatabase().run("INSERT INTO tblsoul (material, displayname, ability, type, damage,flow,crit) VALUES ('" + material + "','" + displayName + "','" + ability + "','" +
                        type + "','" + damage + "','" + flow + "','" + crit + "')").executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void registerItems() {
        try {
            ResultSet resultSet = Everhunt.getDatabase().run("SELECT * FROM tblsoul").executeQuery();

            while (resultSet.next()) {
                Material material = Material.valueOf(resultSet.getString("material"));
                String displayName = resultSet.getString("displayname");
                SoulType type = SoulType.valueOf(resultSet.getString("type"));
                Ability ability = Ability.valueOf(resultSet.getString("ability"));
                double damage = resultSet.getDouble("damage");
                double flow = resultSet.getDouble("flow");
                double crit = resultSet.getDouble("crit");

                new SoulManager(material,displayName,ability,type,damage,flow,crit);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public double getDamage() {
        return damage;
    }
}

