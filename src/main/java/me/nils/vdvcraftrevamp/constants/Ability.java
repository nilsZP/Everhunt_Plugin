package me.nils.vdvcraftrevamp.constants;

public enum Ability {
    METEOR_BLAST("Meteor Blast", 1.8, 1),
    EVOCATION("Evocation", 3, 5),
    DIMENSION_SHATTER("Dimension Shatter", 0, 10),
    LETHAL_ABSORPTION("Lethal Absorption", 0, 10),
    THUNDER_WARP("Thunder Warp", 1.1, 2),
    DIMENSION_UNISON("Dimension Unison", 0, 10),
    SNOWBALL("Snowball",0.5,0), // TODO add this ability in abilitylistener
    THUNDER_CLAP("Thunder Clap",2,4), // TODO add ability in listener
    THUNDER_FLASH("Thunder Flash",2.1,4), // TODO add ability
    NONE("NONE", 0, 0);

    private final String name;
    private final double damageMultiplier;
    private final int cooldown;

    Ability(String name, double damageMultiplier, int cooldown) {
        this.name = name;
        this.damageMultiplier = damageMultiplier;
        this.cooldown = cooldown;
    }

    public String getName() {
        return name;
    }

    public double getDamageMultiplier() {
        return damageMultiplier;
    }

    public int getCooldown() {
        return cooldown;
    }
}
