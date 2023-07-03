package me.nils.vdvcraftrevamp.items;

import me.nils.vdvcraftrevamp.VDVCraftRevamp;
import me.nils.vdvcraftrevamp.items.weapons.Weapons;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class Recipes {
 /*   public static void DaggerOfShatteredDimensionsRecipe() {
        // TODO fix recipe not working
        ItemStack item = new DaggerOfShatteredDimensions().getItem();

        NamespacedKey key = VDVCraftRevamp.getKey();

        ShapedRecipe recipe = new ShapedRecipe(key, item);

        recipe.shape("SBS", "SRS", "SHS");

        recipe.setIngredient('B', new DaggerBlade().getItem());
        recipe.setIngredient('R', new RippedDimension().getItem());
        recipe.setIngredient('H', new DaggerHandle().getItem());
        recipe.setIngredient('S', new ShatteredShard().getItem());

        Bukkit.addRecipe(recipe);
    } */

    public static void DaggerRecipe() {
        ItemStack item = new Weapons().Dagger();

        NamespacedKey key = new NamespacedKey(VDVCraftRevamp.getInstance(),"DaggerRecipe");

        ShapedRecipe recipe = new ShapedRecipe(key, item);

        recipe.shape(" S ", " I ", "   ");

        recipe.setIngredient('S', new ItemStack(Material.STICK));
        recipe.setIngredient('I', new ItemStack(Material.IRON_INGOT));

        Bukkit.addRecipe(recipe);
    }
    public static void loadRecipes() {
//        DaggerOfShatteredDimensionsRecipe();
        DaggerRecipe();
    }
}
