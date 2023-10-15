package me.nils.everhunt.constants;

import org.bukkit.inventory.meta.trim.TrimPattern;

public enum Pattern {
    COAST(TrimPattern.COAST),
    DUNE(TrimPattern.DUNE),
    EYE(TrimPattern.EYE),
    HOST(TrimPattern.HOST),
    RAISER(TrimPattern.RAISER),
    RIB(TrimPattern.RIB),
    SENTRY(TrimPattern.SENTRY),
    SHAPER(TrimPattern.SHAPER),
    SILENCE(TrimPattern.SILENCE),
    SNOUT(TrimPattern.SNOUT),
    SPIRE(TrimPattern.SPIRE),
    TIDE(TrimPattern.TIDE),
    VEX(TrimPattern.VEX),
    WARD(TrimPattern.WARD),
    WAYFINDER(TrimPattern.WAYFINDER),
    WILD(TrimPattern.WILD),
    NONE(null);


    private final TrimPattern pattern;

    Pattern(TrimPattern pattern) {
        this.pattern = pattern;
    }

    public TrimPattern getPattern() {
        return pattern;
    }
}
