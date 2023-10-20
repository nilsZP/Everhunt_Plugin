package me.nils.everhunt.entities;

import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.constants.MobType;
import me.nils.everhunt.data.EntityData;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;

public class Hologram {
    public Hologram(Location loc) {
        new EntityData("hologram",0,1, Ability.NONE, MobType.UTIL);
        ArmorStand hologram = (ArmorStand) loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);

        hologram.setCustomName("hologram");
        hologram.setCustomNameVisible(false);
        hologram.setVisible(false);
        hologram.setSmall(true);
        hologram.setCollidable(false);
        hologram.setMarker(true);

        hologram.setMaxHealth(1);
        hologram.setHealth(1);
    }
}
