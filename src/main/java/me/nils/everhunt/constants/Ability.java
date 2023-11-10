package me.nils.everhunt.constants;

public enum Ability {
    METEOR_BLAST("Meteor Blast", 1.3, 1,Activation.RIGHT_CLICK,10),
    EVOCATION("Evocation", 2, 5,Activation.RIGHT_CLICK,6),
    DIMENSION_SHATTER("Dimension Shatter", 0, 10,Activation.RIGHT_CLICK,50),
    LETHAL_ABSORPTION("Lethal Absorption", 0, 20,Activation.RIGHT_CLICK,2),
    THUNDER_WARP("Thunder Warp", 1.1, 2,Activation.THROW,5),
    DIMENSION_UNISON("Dimension Unison", 0, 10,Activation.RIGHT_CLICK,50),
    SNOWBALL("Snowball",0.5,0,Activation.RIGHT_CLICK,1),
    THUNDER_CLAP("Thunder Clap",1.2,4,Activation.LEFT_CLICK,0),
    THUNDER_FLASH("Thunder Flash",1.5,4,Activation.LEFT_CLICK,0),
    SPRING("Spring",0,2,Activation.PASSIVE,3),
    UNITE("Unite",0,5,Activation.PASSIVE,20),
    MECHANICAL_SHOT("Mechanical Shot", 0.8,1,Activation.PASSIVE,2),
    ALPHA_ROAR("Alpha roar",2,5,Activation.PASSIVE,0),
    BREAD_MAKER("Bread Maker",0,0,Activation.PASSIVE,0),
    POWER_OF_THE_SUN("Power Of The Sun",0.6,4,Activation.PASSIVE,0),
    INFECTATION("Infectation",0,5,Activation.PASSIVE,0),
    NONE("NONE", 0, 0,Activation.PASSIVE,0);

    private final String name;
    private final double damageMultiplier;
    private final int cooldown;
    private final Activation activation;
    private final int flowCost;

    Ability(String name, double damageMultiplier, int cooldown, Activation activation, int flowCost) {
        this.name = name;
        this.damageMultiplier = damageMultiplier;
        this.cooldown = cooldown;
        this.activation = activation;
        this.flowCost = flowCost;
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

    public int getFlowCost() {
        return flowCost;
    }
}
