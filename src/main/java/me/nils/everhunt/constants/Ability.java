package me.nils.everhunt.constants;

public enum Ability {
    METEOR_BLAST("Meteor Blast", 1.3, 1,Activation.RIGHT_CLICK),
    EVOCATION("Evocation", 2, 5,Activation.RIGHT_CLICK),
    DIMENSION_SHATTER("Dimension Shatter", 0, 10,Activation.RIGHT_CLICK),
    LETHAL_ABSORPTION("Lethal Absorption", 0, 20,Activation.RIGHT_CLICK),
    THUNDER_WARP("Thunder Warp", 1.1, 2,Activation.THROW),
    DIMENSION_UNISON("Dimension Unison", 0, 10,Activation.RIGHT_CLICK),
    SNOWBALL("Snowball",0.5,0,Activation.RIGHT_CLICK),
    THUNDER_CLAP("Thunder Clap",1.2,4,Activation.LEFT_CLICK),
    THUNDER_FLASH("Thunder Flash",1.5,4,Activation.LEFT_CLICK),
    SPRING("Spring",0,2,Activation.SNEAK),
    UNITE("Unite",0,5,Activation.SNEAK),
    MECHANICAL_SHOT("Mechanical Shot", 0.8,1,Activation.SNEAK),
    ALPHA_ROAR("Alpha roar",2,5,Activation.PASSIVE),
    NONE("NONE", 0, 0,Activation.SNEAK);

    private final String name;
    private final double damageMultiplier;
    private final int cooldown;
    private final Activation activation;

    Ability(String name, double damageMultiplier, int cooldown, Activation activation) {
        this.name = name;
        this.damageMultiplier = damageMultiplier;
        this.cooldown = cooldown;
        this.activation = activation;
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

    public Activation getActivation() {
        return activation;
    }
}
