package me.nils.everhunt.items;

import me.nils.everhunt.Everhunt;
import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.constants.Tier;
import me.nils.everhunt.data.FlowData;
import me.nils.everhunt.data.PlayerData;
import me.nils.everhunt.managers.HelmetManager;
import me.nils.everhunt.utils.Chat;
import me.nils.everhunt.utils.Head;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PlayerStats {
    public static final HashMap<Player, PlayerStats> items = new HashMap<>();

    private final Player player;
    private final double toughness;
    private final double health;
    private final double damage;

    private final ItemStack itemStack;

    public PlayerStats(Player player) {
        this.player = player;

        PlayerData pData = PlayerData.data.get(player.getUniqueId().toString());

        int level = pData.getXp() / 100;

        double base = level;

        toughness = base / 10;
        damage = base / 5;

        while (base %5 != 0) {
            base--;
        }

        if (base != 0) {
            health = base / 5;
        } else {
            health = 0;
        }

        double luck;

        while (base %2 != 0) {
            base--;
        }

        if (base != 0) {
            luck = base / 2;
        } else {
            luck = 0;
        }

        UUID uuid = player.getUniqueId();

        FlowData data = FlowData.data.get(player);

        itemStack = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) itemStack.getItemMeta();
        meta.setPlayerProfile(player.getPlayerProfile());
        meta.displayName(Component.text(player.getName()));
        meta.getPersistentDataContainer().set(Everhunt.getKey(), PersistentDataType.STRING, player.getName());

        AttributeModifier modifier;
        if (health != 0) {
            modifier = new AttributeModifier(UUID.randomUUID(), "health", health, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD);
            meta.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH, modifier);
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

        lore.add(Chat.color("&7Health: &a+" + health));


        lore.add(Chat.color("&7Toughness: &8+" + toughness));


        lore.add(Chat.color("&7Damage: &4+" + damage));

        lore.add(Chat.color("&7Luck: &2+" + luck));

        lore.add(Chat.color("&7Flow: &3" + data.getFlowAmount()));

        meta.setLore(lore);

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);

        itemStack.setItemMeta(meta);

        items.put(player, this);
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
}
