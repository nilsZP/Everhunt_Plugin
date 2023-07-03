package me.nils.vdvcraftrevamp.constants;

import java.util.UUID;

public enum Ability {
    METEOR_BLAST("Meteor Blast", 20, 1),
    EVOCATION("Evocation", 40, 5),
    DIMENSION_SHATTER("Dimension Shatter", 0, 10),
    LETHAL_ABSORPTION("Lethal Absorption", 0, 10),
    THUNDER_WARP("Thunder Warp", 10, 2),
    DIMENSION_UNISON("Dimension Unison", 0, 10),
    NONE("NONE", 0, 0);

    private final String name;
    private final int abilityDamage;
    private final int cooldown;

    Ability(String name, int abilityDamage, int cooldown) {
        this.name = name;
        this.abilityDamage = abilityDamage;
        this.cooldown = cooldown;
    }

    public String getName() {
        return name;
    }

    public int getAbilityDamage() {
        return abilityDamage;
    }

    public int getCooldown() {
        return cooldown;
    }
}
