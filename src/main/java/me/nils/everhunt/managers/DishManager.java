package me.nils.everhunt.managers;

import me.nils.everhunt.Everhunt;
import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.constants.Tier;
import me.nils.everhunt.items.Items;
import me.nils.everhunt.utils.Chat;
import me.nils.everhunt.utils.Head;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DishManager {
    public static final HashMap<String, DishManager> items = new HashMap<>();
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
    private final int nutrition;
    private final String url;

    public DishManager(Material material, String displayName, Tier tier, int nutrition, String url) {
        this.displayName = displayName;
        this.material = material;
        this.tier = tier;
        this.nutrition = nutrition;
        this.url = url;

        itemStack = new ItemStack(material);
        if (material == Material.PLAYER_HEAD) {
            SkullMeta meta = (SkullMeta) itemStack.getItemMeta();
            meta.setPlayerProfile(Head.createTexture(url));
            meta.getPersistentDataContainer().set(Everhunt.getKey(),PersistentDataType.STRING,displayName);
            meta.displayName(Component.text(tier.getColor() + displayName));
            meta.setLocalizedName(displayName);
            meta.setUnbreakable(true);

            List<String> lore = new ArrayList<>();

            if (tier != Tier.MENU && nutrition != 0) {
                lore.add(Chat.color("&fHealing factor: &d" + nutrition));
            }

            Ability.EAT.addAbilityLore(lore);

            lore.add(Chat.color("&r"));
            lore.add(tier.getColor() + String.valueOf(tier) + " ITEM");

            meta.setLore(lore);

            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);

            itemStack.setItemMeta(meta);
        } else {
            ItemMeta meta = itemStack.getItemMeta();
            meta.getPersistentDataContainer().set(Everhunt.getKey(),PersistentDataType.STRING,displayName);
            meta.displayName(Component.text(tier.getColor() + displayName));
            meta.setLocalizedName(displayName);
            meta.setUnbreakable(true);

            List<String> lore = new ArrayList<>();

            if (tier != Tier.MENU && nutrition != 0) {
                lore.add(Chat.color("&fHealing factor: &d" + nutrition));
            }

            Ability.EAT.addAbilityLore(lore);

            lore.add(Chat.color("&r"));
            lore.add(tier.getColor() + String.valueOf(tier) + " DISH");

            meta.setLore(lore);

            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);

            itemStack.setItemMeta(meta);
        }

        items.put(displayName,this);

        Everhunt.items.add(this);

        try {
            ResultSet check = Everhunt.getDatabase().run("SELECT count(*) FROM tbldish WHERE displayname = '" + displayName + "'").executeQuery();
            check.next();
            if (check.getInt(1) < 1) {
                Everhunt.getDatabase().run("INSERT INTO tbldish (material, displayname, tier, nutrition, url) VALUES ('" + material + "','" + displayName + "','" + tier + "','" + nutrition + "','" + url + "')").executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public static void registerItems() {
        try {
            ResultSet resultSet = Everhunt.getDatabase().run("SELECT * FROM tbldish").executeQuery();

            while (resultSet.next()) {
                Material material = Material.valueOf(resultSet.getString("material"));
                String displayName = resultSet.getString("displayname");
                Tier tier = Tier.valueOf(resultSet.getString("tier"));
                int nutrition = resultSet.getInt("nutrition");
                String url = resultSet.getString("url");

                new DishManager(material,displayName,tier,nutrition,url);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ItemStack getViaNutrition(int nutrition) {
        try {
            ResultSet resultSet = Everhunt.getDatabase().run("SELECT * FROM tbldish WHERE  nutrition = " + nutrition).executeQuery();

            if (resultSet.next()) {
                String displayName = resultSet.getString("displayname");

                return Items.getBase(displayName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new ItemStack(Material.AIR);
        }

        return new ItemStack(Material.AIR);
    }

    public int getNutrition() {
        return nutrition;
    }
}
