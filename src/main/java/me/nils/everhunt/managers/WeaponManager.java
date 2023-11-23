package me.nils.everhunt.managers;

import me.nils.everhunt.Everhunt;
import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.constants.Tier;
import me.nils.everhunt.data.PlayerData;
import me.nils.everhunt.utils.Chat;
import me.nils.everhunt.utils.Database;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class WeaponManager {
    public static final HashMap<String, WeaponManager> items = new HashMap<>();
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
    private final String displayName;
    private final Tier tier;
    private final Ability ability;
    private final double damage;

    private final ItemStack itemStack;

    public WeaponManager(Material material, String displayName, Ability ability, Tier tier, double damage) {
        this.ability = ability;
        this.displayName = displayName;
        this.material = material;
        this.tier = tier;
        this.damage = damage;

        itemStack = new ItemStack(material);
        ItemMeta meta = itemStack.getItemMeta();
        meta.getPersistentDataContainer().set(Everhunt.getKey(),PersistentDataType.STRING,displayName);
        meta.displayName(Component.text(tier.getColor() + displayName));

        AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(),"damage",damage, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE,modifier);
        meta.setUnbreakable(true);

        List<String> lore = new ArrayList<>();

        if (damage != 0) {
            lore.add(Chat.color("&7Damage: &4+" + damage));
        }

        if (!(ability == Ability.NONE)) {
            String action = ability.getActivation().getAction();
            lore.add(Chat.color("&r"));
            lore.add(Chat.color("&6Ability: " + ability.getName() + " &e&l" + action));
            if (ability.getCooldown() != 0) {
                lore.add(Chat.color("&8Cooldown: &3" + ability.getCooldown()));
            }
            if (ability.getFlowCost() != 0) {
                lore.add(Chat.color("&8Cost: &3" + ability.getFlowCost()));
            }
        }
        lore.add(Chat.color("&r"));
        lore.add(tier.getColor() + String.valueOf(tier) + " WEAPON");

        meta.setLore(lore);

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);

        itemStack.setItemMeta(meta);

        items.put(displayName,this);

        Everhunt.items.add(this);

        try {
            ResultSet check = Everhunt.getDatabase().run("SELECT count(*) FROM tblweapon WHERE displayname = '" + displayName + "'").executeQuery();
            check.next();
            if (check.getInt(1) < 1) {
                Everhunt.getDatabase().run("INSERT INTO tblweapon (material, displayname, ability, tier, damage) VALUES ('" + material + "','" + displayName + "','" + ability + "','" +
                        tier + "','" + damage + "')").executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void registerItems() {
        try {
            ResultSet resultSet = Everhunt.getDatabase().run("SELECT * FROM tblweapon").executeQuery();

            while (resultSet.next()) {
                Material material = Material.valueOf(resultSet.getString("material"));
                String displayName = resultSet.getString("displayname");
                Tier tier = Tier.valueOf(resultSet.getString("tier"));
                Ability ability = Ability.valueOf(resultSet.getString("ability"));
                double damage = resultSet.getDouble("damage");

                new WeaponManager(material,displayName,ability,tier,damage);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getWeaponID() {
        try {
            ResultSet resultSet = Everhunt.getDatabase().run("SELECT * FROM tblweapon WHERE displayname = '" + displayName + "'").executeQuery();

            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int getWeaponID(String displayName) {
        try {
            ResultSet resultSet = Everhunt.getDatabase().run("SELECT * FROM tblweapon WHERE displayname = '" + displayName + "'").executeQuery();

            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public double getDamage() {
        return damage;
    }
}
