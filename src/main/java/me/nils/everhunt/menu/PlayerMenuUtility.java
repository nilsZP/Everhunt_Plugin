package me.nils.everhunt.menu;

import me.nils.everhunt.items.Items;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerMenuUtility {
    private Player owner;
    private String search;
    private String inputType;
    private boolean input;
    // you can add more attributes so that you can save variables between menus

    public PlayerMenuUtility(Player owner) {
        this.owner = owner;
        this.input = false;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getInputType() {
        return inputType;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    public boolean isInput() {
        return input;
    }

    public void setInput(boolean input) {
        this.input = input;
    }
}
