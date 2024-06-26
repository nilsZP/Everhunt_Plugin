package me.nils.everhunt.constants;

import me.nils.everhunt.utils.Chat;

import java.util.Arrays;
import java.util.List;

public enum Ability {
    METEOR_BLAST("Meteor Blast", 1.3, 1,Activation.RIGHT_CLICK,10,false,0,"&7Shoots a small meteor dealing &c130% &7damage."),
    EVOCATION("Evocation", 2, 5,Activation.RIGHT_CLICK,6,true,10,"&7Summons fangs under your enemy dealing &c200% &7damage."),
    DIMENSION_SHATTER("Dimension Shatter", 0, 10,Activation.RIGHT_CLICK,50,false,0),
    LETHAL_ABSORPTION("Lethal Absorption", 0, 20,Activation.RIGHT_CLICK,2,false,0),
    THUNDER_WARP("Thunder Warp", 1.1, 0,Activation.THROW,2,false,0,"&7Teleport and summon thunder on enemies.","&7The thunder deals &c110% &7damage."),
    DIMENSION_UNISON("Dimension Unison", 0, 10,Activation.RIGHT_CLICK,50,false,0),
    SNOWBALL("Snowball",0.5,0,Activation.RIGHT_CLICK,1,false,0,"&7shoots a snowball dealing &c50% &7damage."),
    CLAP("Clap",0.8,4,Activation.LEFT_CLICK,0,false,0,"&7Hit the enemy with extra force dealing &c80% &7damage."),
    THUNDER_CLAP("Thunder Clap",1.2,4,Activation.LEFT_CLICK,0,false,0,"&7Summons a thunderbolt dealing &c120% &7damage."),
    THUNDER_FLASH("Thunder Flash",1.5,4,Activation.LEFT_CLICK,0,false,0,"&7Summons a thunderbolt dealing &c150% &7damage.","&7Teleports you 5 blocks forwards."),
    SPRING("Spring",0,2,Activation.PASSIVE,3,false,0),
    UNITE("Unite",0,5,Activation.PASSIVE,20,false,0),
    MECHANICAL_SHOT("Mechanical Shot", 0.8,0,Activation.RIGHT_CLICK,2,false,0,"&7Shoots a Mechanical Bullet dealing &c80% &7damage."),
    BREAD_MAKER("Bread Maker",0,0,Activation.PASSIVE,0,false,0,"&7Gives a &c10% &7chance of generating bread.","Activates when farming wheat."),
    POWER_OF_THE_SUN("Power Of The Sun",0.6,4,Activation.PASSIVE,0,false,0),
    INFECTATION("Infectation",0,5,Activation.PASSIVE,0,false,0),
    FOOL("Fool",0.5,5,Activation.FULL_SET,0,false,0,"&7Fool the enemy by reducing damage taken by &c50%","&7And dealing &c25% &7to the enemy."),
    DIMENSIONAL_FREEZE("Dimensional Freeze",1,10,Activation.RIGHT_CLICK,20,false,0,"&7Freeze your enemy in place while dealing &c100% &7damage."),
    FORTUNATE("Fortunate",0,0,Activation.PASSIVE,0,false,0,"&7Gives you a &c1% &7chance of doubling you farming drops."),
    SUMMON("Summon",0,10,Activation.DROP,40,true,5,"&7Reincarnates the soul and makes it attack the monster you're looking at.","&7Some souls will only work as decoys.","&7Hostile souls will start attacking you,","&7if their target has been eliminated."),
    EAT("Eat",0,0,Activation.RIGHT_CLICK,0,false,0,"&7Consume this dish and gain regeneration and instant health."),
    VOIDWALK("Void Walk",0,10,Activation.FULL_SET,0,false,4,"&7Make enemies nearby you float.","&7This can also make you float if you sneakjump.","&7This ability activates on sneakjump or sneakhit."),
    NONE("NONE", 0, 0,Activation.PASSIVE,0,false,0);

    private final String name;
    private final double damageMultiplier;
    private final int cooldown;
    private final Activation activation;
    private final int flowCost;
    private final boolean target;
    private final int range;
    private final List<String> description;

    Ability(String name, double damageMultiplier, int cooldown, Activation activation, int flowCost, boolean target, int range, String... description) {
        this.name = name;
        this.damageMultiplier = damageMultiplier;
        this.cooldown = cooldown;
        this.activation = activation;
        this.flowCost = flowCost;
        this.target = target;
        this.range = range;
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

    public int getRange() {
        return range;
    }

    public void addAbilityLore(List<String> lore) {
        if (!(name.equals("NONE"))) {
            String action = activation.getAction();
            lore.add(Chat.color("&r"));
            lore.add(Chat.color("&6Ability: " + name + " &e&l" + action));
            for (String text : description) {
                lore.add(Chat.color(text));
            }
            if (cooldown != 0) {
                lore.add(Chat.color("&8Cooldown: &3" + cooldown));
            }
            if (flowCost != 0) {
                lore.add(Chat.color("&8Cost: &3" + flowCost));
            }
            if (range != 0) {
                lore.add(Chat.color("&8Range: &3" + range));
            }
        }
    }
}
