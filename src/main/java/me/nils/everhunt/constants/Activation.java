package me.nils.everhunt.constants;

public enum Activation {
    RIGHT_CLICK("RIGHT CLICK"),
    LEFT_CLICK("LEFT CLICK"),
    THROW("THROW"),
    FULL_SET("FULL SET"),
    PASSIVE("PASSIVE"),
    DROP("DROP");

    private final String action;

    Activation(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }
}
