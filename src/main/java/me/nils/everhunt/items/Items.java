package me.nils.everhunt.items;

import me.nils.everhunt.managers.*;
import me.nils.everhunt.utils.Condition;
import org.bukkit.ChatColor;
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

    public static ItemStack getBase(String item) {
        return new ItemStack(switch (Condition.getType(item)) {
            case ITEM -> ItemManager.items.get(item).getItemStack();
            case DISH -> DishManager.items.get(item).getItemStack();
            case SOUL -> SoulManager.souls.get(item).getItemStack();
            case TOOL -> ToolManager.items.get(item).getItemStack();
            case ARMOR -> ArmorManager.items.get(item).getItemStack();
            case HELMET -> HelmetManager.items.get(item).getItemStack();
            case WEAPON -> WeaponManager.items.get(item).getItemStack();
        });
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

    public ArrayList<ItemStack> getAll() {
        ArrayList<ItemStack> list = new ArrayList<>();

        list.addAll(weapons);
        list.addAll(items);
        list.addAll(armors);
        list.addAll(souls);
        list.addAll(helmets);
        list.addAll(dishes);
        list.addAll(tools);

        return list;
    }

    public ArrayList<ItemStack> getCrates() {
        ArrayList<ItemStack> list = new ArrayList<>();

        for (ItemStack itemStack : items) {
            if (itemStack.hasItemMeta()) {
                if (ChatColor.stripColor(itemStack.getItemMeta().getDisplayName()).contains("Crate")) {
                    list.add(itemStack);
                }
            }
        }

        return list;
    }
}
