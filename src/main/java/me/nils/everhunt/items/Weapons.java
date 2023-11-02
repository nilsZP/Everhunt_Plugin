package me.nils.everhunt.items;

import me.nils.everhunt.items.weapons.*;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Date;

public class Weapons {
    public static ItemStack[] getAll() {
        ArrayList<ItemStack> list = new ArrayList<>();
        list.add(new AzureWrath().getItemStack());
        list.add(new DaggerOfShatteredDimensions().getItemStack());
        list.add(new DaggerOfUnitedDimensions().getItemStack());
        list.add(new Evokus().getItemStack());
        list.add(new LuciaI().getItemStack());
        list.add(new LuciaII().getItemStack());
        list.add(new LuciaIII().getItemStack());
        list.add(new LuciaIV().getItemStack());
        list.add(new LuciaV().getItemStack());
        list.add(new LuciaVI().getItemStack());
        list.add(new MeteorBlade().getItemStack());
        list.add(new Nixeus().getItemStack());
        list.add(new SnowShovel().getItemStack());
        list.add(new WoodenBat().getItemStack());

        ItemStack[] weapons = new ItemStack[list.size()];
        weapons = list.toArray(weapons);
        return weapons;
    }
}
