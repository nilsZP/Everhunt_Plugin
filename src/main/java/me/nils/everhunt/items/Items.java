package me.nils.everhunt.items;

import me.nils.everhunt.items.weapons.*;
import me.nils.everhunt.managers.ArmorManager;
import me.nils.everhunt.managers.ItemManager;
import me.nils.everhunt.managers.ToolManager;
import me.nils.everhunt.managers.WeaponManager;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class Items {
    private final ArrayList<ItemStack> weapons;
    private final ArrayList<ItemStack> armors;
    private final ArrayList<ItemStack> items;
    private final ArrayList<ItemStack> tools;

    public Items() {
        this.weapons = new ArrayList<>();
        this.armors = new ArrayList<>();
        this.items = new ArrayList<>();
        this.tools = new ArrayList<>();
    }

    public void add(WeaponManager weapon) {
        weapons.add(weapon.getItemStack());
    }

    public void add(ArmorManager armor) {
        armors.add(armor.getItemStack());
    }

    public void add(ItemManager item) {
        items.add(item.getItemStack());
    }

    public void add(ToolManager tool) {
        tools.add(tool.getItemStack());
    }

    public ItemStack[] getWeapons() {
        ItemStack[] list = new ItemStack[weapons.size()];
        list = weapons.toArray(list);
        return list;
    }

    public ItemStack[] getArmor() {
        ItemStack[] list = new ItemStack[armors.size()];
        list = armors.toArray(list);
        return list;
    }

    public ItemStack[] getItems() {
        ItemStack[] list = new ItemStack[items.size()];
        list = items.toArray(list);
        return list;
    }

    public ItemStack[] getTools() {
        ItemStack[] list = new ItemStack[tools.size()];
        list = tools.toArray(list);
        return list;
    }
}
