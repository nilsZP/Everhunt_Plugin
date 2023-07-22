package me.nils.vdvcraftrevamp.managers;

import me.nils.vdvcraftrevamp.VDVCraftRevamp;
import me.nils.vdvcraftrevamp.constants.Ability;
import me.nils.vdvcraftrevamp.constants.Flow;
import me.nils.vdvcraftrevamp.constants.Rarity;
import me.nils.vdvcraftrevamp.utils.Chat;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.io.File;
import java.util.*;

public class WeaponManager {
    public static final HashMap<String, WeaponManager> items = new HashMap<>();

    public Material getMaterial() {
        return material;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public Ability getAbility() {
        return ability;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    private final Material material;
    private final String displayName;
    private final Rarity rarity;
    private final Ability ability;
    private final Flow flow;
    private final double damage;

    private final ItemStack itemStack;
    private final YamlConfiguration configuration;

    public WeaponManager(Material material, String displayName, Ability ability, Rarity rarity, double damage, Flow flow) {
        this.ability = ability;
        this.displayName = displayName;
        this.material = material;
        this.rarity = rarity;
        this.flow = flow;
        this.damage = damage;

        itemStack = new ItemStack(material);
        ItemMeta meta = itemStack.getItemMeta();
        meta.displayName(Component.text(rarity.getColor() + displayName));
        meta.getPersistentDataContainer().set(VDVCraftRevamp.getKey(), PersistentDataType.STRING, displayName);

        AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(),"damage",damage, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE,modifier);
        meta.setUnbreakable(true);

        List<String> lore = new ArrayList<>();
        lore.add(Chat.color("&7Flow: ") + flow.getColor() + String.valueOf(flow));
        if (!(ability == Ability.NONE)) {
            lore.add(Chat.color("&6Ability: " + ability.getName() + " &e&lRIGHT CLICK"));
            lore.add(Chat.color("&8Cooldown: &3" + ability.getCooldown()));
        }
        lore.add(Chat.color("&r"));
        lore.add(rarity.getColor() + String.valueOf(rarity) + " WEAPON");

        meta.setLore(lore);
        itemStack.setItemMeta(meta);

        FileManager fileManager = new FileManager("weapons", displayName);
        configuration = fileManager.getFile();
        configuration.set("material", material.toString());
        configuration.set("displayName", displayName);
        configuration.set("rarity", rarity.toString());
        configuration.set("ability", ability.toString());
        configuration.set("damage", damage);
        configuration.set("flow", flow.toString());

        fileManager.save();
        items.put(displayName, this);
    }

    public static void registerItems() {
        List<File> files = FileManager.getFiles("weapons");
        if (files == null) {
            return;
        }
        for (File file : files) {
            YamlConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);

            Material material = Material.getMaterial(Objects.requireNonNull(fileConfiguration.getString("material")));
            String displayName = fileConfiguration.getString("displayName");
            Rarity rarity = Rarity.valueOf(fileConfiguration.getString("rarity"));
            Ability ability = Ability.valueOf(fileConfiguration.getString("ability"));
            double damage = fileConfiguration.getDouble("damage");
            Flow flow = Flow.valueOf(fileConfiguration.getString("flow"));

            new WeaponManager(material, displayName, ability, rarity, damage, flow);
        }
    }

    public Flow getFlow() {
        return flow;
    }

    public double getDamage() {
        return damage;
    }
}
