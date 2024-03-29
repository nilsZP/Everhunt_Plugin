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
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ToolManager {
    public static final HashMap<String, ToolManager> items = new HashMap<>();
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
    private final double speed;
    private final String url;

    private final ItemStack itemStack;

    public ToolManager(Material material, String displayName, Ability ability, Tier tier, double speed, String url) {
        this.ability = ability;
        this.displayName = displayName;
        this.material = material;
        this.tier = tier;
        this.speed = speed;
        this.url = url;

        itemStack = new ItemStack(material);
        if (material == Material.PLAYER_HEAD) {
            SkullMeta meta = (SkullMeta) itemStack.getItemMeta();
            meta.setPlayerProfile(Head.createTexture(url));
            meta.displayName(Component.text(tier.getColor() + displayName));
            meta.getPersistentDataContainer().set(Everhunt.getKey(), PersistentDataType.STRING, displayName);

            if (speed != 0) {
                AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(), "speed", speed, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.HAND);
                meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, modifier);
            }
            meta.setUnbreakable(true);

            List<String> lore = new ArrayList<>();

            if (speed != 0) {
                lore.add(Chat.color("&7Speed: &f+" + (speed*100) + "%"));
            }

            ability.addAbilityLore(lore);

            lore.add(Chat.color("&r"));
            lore.add(tier.getColor() + String.valueOf(tier) + " TOOL");

            meta.setLore(lore);

            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);

            itemStack.setItemMeta(meta);
        } else {
            ItemMeta meta = itemStack.getItemMeta();
            meta.getPersistentDataContainer().set(Everhunt.getKey(), PersistentDataType.STRING, displayName);
            meta.displayName(Component.text(tier.getColor() + displayName));

            if (speed != 0) {
                AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(), "speed", speed, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.HAND);
                meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, modifier);
            }
            meta.setUnbreakable(true);

            List<String> lore = new ArrayList<>();

            if (speed != 0) {
                lore.add(Chat.color("&7Speed: &f+" + (speed*100) + "%"));
            }

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
            lore.add(tier.getColor() + String.valueOf(tier) + " TOOL");

            meta.setLore(lore);

            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);

            itemStack.setItemMeta(meta);
        }

        items.put(displayName,this);

        Everhunt.items.add(this);

        try {
            ResultSet check = Everhunt.getDatabase().run("SELECT count(*) FROM tbltool WHERE displayname = '" + displayName + "'").executeQuery();
            check.next();
            if (check.getInt(1) < 1) {
                Everhunt.getDatabase().run("INSERT INTO tbltool (material, displayname, ability, tier, speed,url) VALUES ('" + material + "','" + displayName + "','" + ability + "','" +
                        tier + "','" + speed + "','" + url + "')").executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void registerItems() {
        try {
            ResultSet resultSet = Everhunt.getDatabase().run("SELECT * FROM tbltool").executeQuery();

            while (resultSet.next()) {
                Material material = Material.valueOf(resultSet.getString("material"));
                String displayName = resultSet.getString("displayname");
                Tier tier = Tier.valueOf(resultSet.getString("tier"));
                Ability ability = Ability.valueOf(resultSet.getString("ability"));
                double speed = resultSet.getDouble("speed");
                String url = resultSet.getString("url");

                new ToolManager(material,displayName,ability,tier,speed,url);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
