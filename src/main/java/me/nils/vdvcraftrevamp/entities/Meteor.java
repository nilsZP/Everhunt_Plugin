package me.nils.vdvcraftrevamp.entities;

import me.nils.vdvcraftrevamp.VDVCraftRevamp;
import me.nils.vdvcraftrevamp.constants.Ability;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.persistence.PersistentDataType;

public class Meteor {
    public Meteor(Location loc, double damage) {
        Fireball meteor = (Fireball) loc.getWorld().spawnEntity(loc, EntityType.FIREBALL);

        meteor.setCustomName("Meteor");
        meteor.setYield(3.0F);

        meteor.getPersistentDataContainer().set(VDVCraftRevamp.getKey(), PersistentDataType.DOUBLE, damage);
    }
}
