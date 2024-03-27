package me.nils.everhunt.entities;

import me.nils.everhunt.Everhunt;
import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.constants.MobType;
import me.nils.everhunt.constants.Tier;
import me.nils.everhunt.data.EntityData;
import me.nils.everhunt.utils.Chat;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.EntityType;
import org.bukkit.persistence.PersistentDataType;

public class Springer extends EntityData {
    public Springer(Location loc) {
        super("Springer", Tier.BASIC, MobType.ENEMY);
        CaveSpider springer = (CaveSpider) loc.getWorld().spawnEntity(loc, EntityType.CAVE_SPIDER);

        springer.setCustomName(Chat.color(String.format("%s &c%d&f/&c%d%s", super.getDisplayName(), 6, 6,"♥")));
        springer.setCustomNameVisible(true);

        springer.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(6);
        springer.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(2);
        springer.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(0);

        springer.getPersistentDataContainer().set(Everhunt.getKey(), PersistentDataType.STRING,super.getDisplayName());

        super.setEntity(springer);
    }
}
