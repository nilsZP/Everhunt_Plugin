package me.nils.everhunt.menu;

import me.nils.everhunt.items.Items;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerMenuUtility {
    private Player owner;
    private ItemStack item;
    private String search;
    // you can add more attributes so that you can save variables between menus

    public PlayerMenuUtility(Player owner) {
        this.owner = owner;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public ItemStack getItem() {
        return item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
