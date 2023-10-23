package me.nils.everhunt.managers;

import me.nils.everhunt.Everhunt;
import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.constants.Tier;
import me.nils.everhunt.utils.Chat;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ItemManager {
    public static final HashMap<String, ItemManager> items = new HashMap<>();
    public Material getMaterial() {
        return material;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Tier getTier() {
        return tier;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    private final Material material;
    private final String displayName;
    private final Tier tier;
    private final ItemStack itemStack;

    public ItemManager(Material material, String displayName, Tier tier) {
        this.displayName = displayName;
        this.material = material;
        this.tier = tier;

        itemStack = new ItemStack(material);
        ItemMeta meta = itemStack.getItemMeta();
        meta.displayName(Component.text(tier.getColor() + displayName));
        meta.getPersistentDataContainer().set(Everhunt.getKey(), PersistentDataType.STRING, displayName);
        meta.setUnbreakable(true);

        List<String> lore = new ArrayList<>();
        lore.add(Chat.color("&r"));
        lore.add(tier.getColor() + String.valueOf(tier) + " ITEM");

        meta.setLore(lore);
        itemStack.setItemMeta(meta);

        items.put(displayName,this);

        try {
            ResultSet check = Everhunt.getDatabase().run("SELECT count(*) FROM tblweapon WHERE displayname = '" + displayName + "'").executeQuery();
            check.next();
            if (check.getInt(1) < 1) {
                Everhunt.getDatabase().run("INSERT INTO tblitem (material, displayname, tier) VALUES ('" + material + "','" + displayName + "','" + tier + "')").executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void registerItems() {
        try {
            ResultSet resultSet = Everhunt.getDatabase().run("SELECT * FROM tblitem").executeQuery();

            while (resultSet.next()) {
                Material material = Material.valueOf(resultSet.getString("material"));
                String displayName = resultSet.getString("displayname");
                Tier tier = Tier.valueOf(resultSet.getString("tier"));

                new ItemManager(material,displayName,tier);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getItemID() {
        try {
            ResultSet resultSet = Everhunt.getDatabase().run("SELECT * FROM tblitem WHERE displayname = '" + displayName + "'").executeQuery();

            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int getItemID(String displayName) {
        try {
            ResultSet resultSet = Everhunt.getDatabase().run("SELECT * FROM tblitem WHERE displayname = '" + displayName + "'").executeQuery();

            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
