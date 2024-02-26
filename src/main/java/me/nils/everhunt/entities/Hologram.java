package me.nils.everhunt.entities;

import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.constants.MobType;
import me.nils.everhunt.constants.Tier;
import me.nils.everhunt.data.EntityData;
import me.nils.everhunt.utils.Chat;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

public class Hologram extends EverEntity {
    private LivingEntity entity;
    public Hologram(Location location, LivingEntity entity) {
        super(location);
        this.entity = entity;
    }

    public static void addHologram(LivingEntity entity) {
        entity.addPassenger(new Hologram(entity.getLocation(),entity).getEntity());
    }

    @Override
    public String getDisplayName() {
        int maxHealth = (int) entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
        return Chat.color(String.format("%s &c%d&f/&c%d%s", entity.getName(), Math.round(entity.getHealth()), maxHealth,"♥"));
    }

    @Override
    public String getEntityName() {
        return "Hologram";
    }

    @Override
    public boolean hasHologram() {
        return false;
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.ARMOR_STAND;
    }

    @Override
    public Tier getTier() {
        return Tier.BASIC;
    }

    @Override
    public double getHealth() {
        return 1;
    }

    @Override
    public double getDamage() {
        return 0;
    }

    @Override
    public double getArmor() {
        return 0;
    }

    @Override
    public MobType getMobType() {
        return MobType.UTIL;
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public Ability getAbility() {
        return Ability.NONE;
    }

    @Override
    public void setUniqueAttributes() {
        ArmorStand hologram = (ArmorStand) entity;
        hologram.setCustomNameVisible(true);
        hologram.setVisible(false);
        hologram.setSmall(true);
        hologram.setCollidable(false);
        hologram.setMarker(true);
    }
}
