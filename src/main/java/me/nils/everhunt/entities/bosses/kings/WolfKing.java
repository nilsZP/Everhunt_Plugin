package me.nils.everhunt.entities.bosses.kings;

import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.constants.MobType;
import me.nils.everhunt.data.EntityData;
import org.bukkit.Location;
import org.bukkit.entity.*;

import java.util.List;

public class WolfKing extends EntityData {
    public WolfKing(Location loc) {
        super("Wolf King",10,25, Ability.NONE, MobType.BOSS);
        Wolf wolf = (Wolf) loc.getWorld().spawnEntity(loc, EntityType.WOLF);

        wolf.setAngry(true);
        List<Entity> entityList = wolf.getNearbyEntities(5,5,5);
        for (Entity entity : entityList) {
            if (entity instanceof Player player) {
                wolf.setTarget(player);
            }
        }

        wolf.setCustomName("Wolf King");
        wolf.setCustomNameVisible(true);


        wolf.setMaxHealth(25);
        wolf.setHealth(25);
    }
}
