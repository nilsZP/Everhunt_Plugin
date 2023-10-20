package me.nils.everhunt.entities;

import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.constants.MobType;
import me.nils.everhunt.data.EntityData;
import org.bukkit.Location;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.EntityType;

public class Springer {
    public Springer(Location loc) {
        new EntityData("Springer", 1, 6, Ability.NONE, MobType.ENEMY);
        CaveSpider springer = (CaveSpider) loc.getWorld().spawnEntity(loc, EntityType.CAVE_SPIDER);

        springer.setCustomName("Springer");
        springer.setCustomNameVisible(false);

        springer.setMaxHealth(6);
        springer.setHealth(6);
    }
}
