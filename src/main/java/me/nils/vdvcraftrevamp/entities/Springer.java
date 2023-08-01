package me.nils.vdvcraftrevamp.entities;

import org.bukkit.Location;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.EntityType;

public class Springer {
    public Springer(Location loc) {
        CaveSpider springer = (CaveSpider) loc.getWorld().spawnEntity(loc, EntityType.CAVE_SPIDER);

        springer.setCustomName("Springer");
        springer.setCustomNameVisible(true);

        springer.setMaxHealth(6);
        springer.setHealth(6);
    }
}
