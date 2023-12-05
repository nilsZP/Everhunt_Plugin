package me.nils.everhunt.constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Ability {
    METEOR_BLAST("Meteor Blast", 1.3, 1,Activation.RIGHT_CLICK,10),
    EVOCATION("Evocation", 2, 5,Activation.RIGHT_CLICK,6),
    DIMENSION_SHATTER("Dimension Shatter", 0, 10,Activation.RIGHT_CLICK,50),
    LETHAL_ABSORPTION("Lethal Absorption", 0, 20,Activation.RIGHT_CLICK,2),
    THUNDER_WARP("Thunder Warp", 1.1, 2,Activation.THROW,5),
    DIMENSION_UNISON("Dimension Unison", 0, 10,Activation.RIGHT_CLICK,50),
    SNOWBALL("Snowball",0.5,0,Activation.RIGHT_CLICK,1,"&7shoots a snowball dealing &c50% &7damage."),
    CLAP("Clap",0.8,4,Activation.LEFT_CLICK,0,"&7Hit the enemy with extra force dealing &c+80% &7damage."),
    THUNDER_CLAP("Thunder Clap",1.2,4,Activation.LEFT_CLICK,0),
    THUNDER_FLASH("Thunder Flash",1.5,4,Activation.LEFT_CLICK,0),
    SPRING("Spring",0,2,Activation.PASSIVE,3),
    UNITE("Unite",0,5,Activation.PASSIVE,20),
    MECHANICAL_SHOT("Mechanical Shot", 0.8,1,Activation.PASSIVE,2),
    ALPHA_ROAR("Alpha roar",2,5,Activation.PASSIVE,0),
    BREAD_MAKER("Bread Maker",0,0,Activation.PASSIVE,0),
    POWER_OF_THE_SUN("Power Of The Sun",0.6,4,Activation.PASSIVE,0),
    INFECTATION("Infectation",0,5,Activation.PASSIVE,0),
    FOOL("Fool",0.5,5,Activation.FULL_SET,0,"&7Fool the enemy by reducing damage taken by &c50%","&7And dealing &c25% &7to the enemy."),
    NONE("NONE", 0, 0,Activation.PASSIVE,0);

    private final String name;
    private final double damageMultiplier;
    private final int cooldown;
    private final Activation activation;
    private final int flowCost;
    private final List<String> description;

    Ability(String name, double damageMultiplier, int cooldown, Activation activation, int flowCost, String... description) {
        this.name = name;
        this.damageMultiplier = damageMultiplier;
        this.cooldown = cooldown;
        this.activation = activation;
        this.flowCost = flowCost;
        this.description = Arrays.asList(description);
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

    public List<String> getDescription() {
        return description;
    }
}
