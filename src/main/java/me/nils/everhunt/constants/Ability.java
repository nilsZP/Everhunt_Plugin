package me.nils.everhunt.constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Ability {
    METEOR_BLAST("Meteor Blast", 1.3, 1,Activation.RIGHT_CLICK,10,false),
    EVOCATION("Evocation", 2, 5,Activation.RIGHT_CLICK,6,false),
    DIMENSION_SHATTER("Dimension Shatter", 0, 10,Activation.RIGHT_CLICK,50,false),
    LETHAL_ABSORPTION("Lethal Absorption", 0, 20,Activation.RIGHT_CLICK,2,false),
    THUNDER_WARP("Thunder Warp", 1.1, 2,Activation.THROW,5,false),
    DIMENSION_UNISON("Dimension Unison", 0, 10,Activation.RIGHT_CLICK,50,false),
    SNOWBALL("Snowball",0.5,0,Activation.RIGHT_CLICK,1,false,"&7shoots a snowball dealing &c50% &7damage."),
    CLAP("Clap",0.8,4,Activation.LEFT_CLICK,0,false,"&7Hit the enemy with extra force dealing &c+80% &7damage."),
    THUNDER_CLAP("Thunder Clap",1.2,4,Activation.LEFT_CLICK,0,false),
    THUNDER_FLASH("Thunder Flash",1.5,4,Activation.LEFT_CLICK,0,false),
    SPRING("Spring",0,2,Activation.PASSIVE,3,false),
    UNITE("Unite",0,5,Activation.PASSIVE,20,false),
    MECHANICAL_SHOT("Mechanical Shot", 0.8,1,Activation.PASSIVE,2,false),
    ALPHA_ROAR("Alpha roar",2,5,Activation.PASSIVE,0,false),
    BREAD_MAKER("Bread Maker",0,0,Activation.PASSIVE,0,false),
    POWER_OF_THE_SUN("Power Of The Sun",0.6,4,Activation.PASSIVE,0,false),
    INFECTATION("Infectation",0,5,Activation.PASSIVE,0,false),
    FOOL("Fool",0.5,5,Activation.FULL_SET,0,false,"&7Fool the enemy by reducing damage taken by &c50%","&7And dealing &c25% &7to the enemy."),
    DIMENSIONAL_FREEZE("Dimensional Freeze",1,10,Activation.RIGHT_CLICK,20,false,"&7Freeze your enemy in place while dealing &c100% &7damage."),
    NONE("NONE", 0, 0,Activation.PASSIVE,0,false);

    private final String name;
    private final double damageMultiplier;
    private final int cooldown;
    private final Activation activation;
    private final int flowCost;
    private final boolean target;
    private final List<String> description;

    Ability(String name, double damageMultiplier, int cooldown, Activation activation, int flowCost, boolean target, String... description) {
        this.name = name;
        this.damageMultiplier = damageMultiplier;
        this.cooldown = cooldown;
        this.activation = activation;
        this.flowCost = flowCost;
        this.target = target;
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

    public boolean needsTarget() {
        return target;
    }
}
