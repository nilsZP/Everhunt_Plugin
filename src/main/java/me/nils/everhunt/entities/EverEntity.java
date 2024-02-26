package me.nils.everhunt.entities;

import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.constants.MobType;
import me.nils.everhunt.constants.Tier;
import me.nils.everhunt.data.EntityData;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public abstract class EverEntity {
    protected Location location;
    protected LivingEntity entity;
    public static final HashMap<EntityData, EverEntity> data = new HashMap<>();

    public EverEntity(Location location) {
        this.location = location;
    }

    public void spawn() {
        EntityData entityData = new EntityData(getEntityName(),getLevel(),getTier(),getAbility(),getMobType());
        LivingEntity entity = (LivingEntity) location.getWorld().spawnEntity(location,getEntityType());
        entity.setCustomName(getEntityName());
        entity.setCustomNameVisible(false);
        if (hasHologram()) {
            Hologram.addHologram(entity);
        }
        entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(getHealth());
        entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(getDamage());
        entity.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(getArmor());
        setUniqueAttributes();
        activateAbility(getAbility());

        this.entity = entity;
        data.put(entityData,this);
    }

    public abstract String getDisplayName();
    public abstract String getEntityName();
    public abstract boolean hasHologram();
    public abstract EntityType getEntityType();
    public abstract Tier getTier();
    public abstract double getHealth();
    public abstract double getDamage();
    public abstract double getArmor();
    public abstract MobType getMobType();
    public abstract int getLevel();
    public abstract Ability getAbility();
    public abstract void setUniqueAttributes();

    @NotNull
    public Location getLocation() {
        return location;
    }

    public LivingEntity getEntity() {
        return entity;
    }

    public void activateAbility(Ability ability) {

    }
}
