package me.nils.everhunt.items;

import me.nils.everhunt.managers.*;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class Items {
    private final ArrayList<ItemStack> weapons;
    private final ArrayList<ItemStack> armors;
    private final ArrayList<ItemStack> items;
    private final ArrayList<ItemStack> tools;
    private final ArrayList<ItemStack> helmets;
    private final ArrayList<ItemStack> souls;
    private final ArrayList<ItemStack> dishes;

    public Items() {
        this.weapons = new ArrayList<>();
        this.armors = new ArrayList<>();
        this.items = new ArrayList<>();
        this.tools = new ArrayList<>();
        this.helmets = new ArrayList<>();
        this.souls = new ArrayList<>();
        this.dishes = new ArrayList<>();
    }

    public void add(WeaponManager weapon) {
        if (weapons.contains(weapon.getItemStack())) {
            return;
        }
        weapons.add(weapon.getItemStack());
    }

    public void add(ArmorManager armor) {
        if (armors.contains(armor.getItemStack())) {
            return;
        }
        armors.add(armor.getItemStack());
    }

    public void add(ItemManager item) {
        if (items.contains(item.getItemStack())) {
            return;
        }
        items.add(item.getItemStack());
    }

    public void add(ToolManager tool) {
        if (tools.contains(tool.getItemStack())) {
            return;
        }
        tools.add(tool.getItemStack());
    }

    public void add(HelmetManager helmet) {
        if (helmets.contains(helmet.getItemStack())) {
            return;
        }
        helmets.add(helmet.getItemStack());
    }

    public void add(SoulManager soul) {
        if (souls.contains(soul.getItemStack())) {
            return;
        }
        souls.add(soul.getItemStack());
    }

    public void add(DishManager dish) {
        if (dishes.contains(dish.getItemStack())) {
            return;
        }
        souls.add(dish.getItemStack());
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

    public ItemStack[] getHelmets() {
        ItemStack[] list = new ItemStack[helmets.size()];
        list = helmets.toArray(list);
        return list;
    }

    public ItemStack[] getSouls() {
        ItemStack[] list = new ItemStack[souls.size()];
        list = souls.toArray(list);
        return list;
    }

    public ItemStack[] getDishes() {
        ItemStack[] list = new ItemStack[dishes.size()];
        list = dishes.toArray(list);
        return list;
    }
}
