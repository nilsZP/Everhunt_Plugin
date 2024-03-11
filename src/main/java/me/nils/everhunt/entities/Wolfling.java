package me.nils.everhunt.entities;

import me.nils.everhunt.Everhunt;
import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.constants.MobType;
import me.nils.everhunt.constants.Tier;
import me.nils.everhunt.data.EntityData;
import me.nils.everhunt.utils.Chat;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class Wolfling extends EntityData {
    public Wolfling(Location loc) {
        super("Wolfling", 1, Tier.BASIC, Ability.NONE, MobType.ENEMY);
        Wolf wolf = (Wolf) loc.getWorld().spawnEntity(loc, EntityType.WOLF);

        wolf.setCustomName(Chat.color(String.format("%s &c%d&f/&c%d%s", super.getDisplayName(), 8, 8,"♥")));
        wolf.setCustomNameVisible(true);
        wolf.getPersistentDataContainer().set(Everhunt.getKey(), PersistentDataType.STRING,super.getDisplayName());

        wolf.setBaby();

        List<Entity> entityList = wolf.getNearbyEntities(5,5,5);
        for (Entity entity : entityList) {
            if (entity instanceof Player player) {
                wolf.setTarget(player);
            }
        }

        wolf.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(8);
        wolf.setHealth(wolf.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
        wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(2);
        wolf.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(5);

        super.setEntity(wolf);
    }
}
