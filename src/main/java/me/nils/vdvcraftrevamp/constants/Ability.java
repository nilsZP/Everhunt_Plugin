package me.nils.vdvcraftrevamp.constants;

public enum Ability {
    METEOR_BLAST("Meteor Blast", 1.8, 1),
    EVOCATION("Evocation", 3, 5), // TODO ADD IN LISTENER
    DIMENSION_SHATTER("Dimension Shatter", 0, 10),
    LETHAL_ABSORPTION("Lethal Absorption", 0, 20),
    THUNDER_WARP("Thunder Warp", 1.1, 2),
    DIMENSION_UNISON("Dimension Unison", 0, 10),
    SNOWBALL("Snowball",0.5,0),
    THUNDER_CLAP("Thunder Clap",2,4),
    THUNDER_FLASH("Thunder Flash",2.1,4),
    SPRING("Spring",0,2),
    UNITE("Unite",0,5),
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
