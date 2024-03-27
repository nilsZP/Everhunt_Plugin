package me.nils.everhunt.constants;

public enum Rank {
    LEADER(4),
    CO_LEADER(3),
    MODERATOR(2),
    MEMBER(1);

    private final int hierarchy;

    Rank(int hierarchy) {
        this.hierarchy = hierarchy;
    }

    public int getHierarchy() {
        return hierarchy;
    }
}
