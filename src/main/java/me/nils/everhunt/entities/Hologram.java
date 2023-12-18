package me.nils.everhunt.entities;

import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.constants.MobType;
import me.nils.everhunt.data.EntityData;
import me.nils.everhunt.utils.Chat;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

public class Hologram {
    public static ArmorStand Hologram(Location loc, LivingEntity entity) {
        new EntityData("hologram",0,1, Ability.NONE, MobType.UTIL);
        ArmorStand hologram = (ArmorStand) loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);

        int maxHealth = EntityData.data.get(entity.getName()).getMaxHealth();
        hologram.setCustomName(Chat.color(String.format("%s &c%d&f/&c%d%s", entity.getName(), Math.round(entity.getHealth()), maxHealth,"♥")));
        hologram.setCustomNameVisible(true);
        hologram.setVisible(false);
        hologram.setSmall(true);
        hologram.setCollidable(false);
        hologram.setMarker(true);

        hologram.setMaxHealth(1);
        hologram.setHealth(1);

        return hologram;
    }

    public static void addHologram(LivingEntity entity) {
        entity.addPassenger(Hologram(entity.getLocation(),entity));
    }
}
