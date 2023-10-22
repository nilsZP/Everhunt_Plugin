package me.nils.everhunt.entities.bosses.kings;

import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.constants.MobType;
import me.nils.everhunt.data.EntityData;
import me.nils.everhunt.entities.Hologram;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;

import java.util.List;

public class WolfKing extends EntityData {
    public WolfKing(Location loc) {
        super("Wolf King",10,40, Ability.NONE, MobType.BOSS);
        Wolf wolf = (Wolf) loc.getWorld().spawnEntity(loc, EntityType.WOLF);

        wolf.setAngry(true);
        List<Entity> entityList = wolf.getNearbyEntities(5,5,5);
        for (Entity entity : entityList) {
            if (entity instanceof Player player) {
                wolf.setTarget(player);
            }
        }

        wolf.setCustomName("Wolf King");
        wolf.setCustomNameVisible(false);
        wolf.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(40);
        wolf.setHealth(wolf.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
        wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(4);
        wolf.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(10);

        Hologram.addHologram(wolf);
    }
}
