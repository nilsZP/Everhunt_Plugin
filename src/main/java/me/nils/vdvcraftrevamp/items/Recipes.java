package me.nils.vdvcraftrevamp.items;

import me.nils.vdvcraftrevamp.VDVCraftRevamp;
import me.nils.vdvcraftrevamp.items.materials.Materials;
import me.nils.vdvcraftrevamp.items.weapons.Weapons;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class Recipes {
    public static void DaggerRecipe() {
        ItemStack item = new Weapons().Dagger();

        NamespacedKey key = new NamespacedKey(VDVCraftRevamp.getInstance(),"DaggerRecipe");

        ShapedRecipe recipe = new ShapedRecipe(key, item);

        recipe.shape(" S ", " I ", "   ");

        recipe.setIngredient('S', new ItemStack(Material.STICK));
        recipe.setIngredient('I', new ItemStack(Material.IRON_INGOT));

        Bukkit.addRecipe(recipe);
    }

    public static void DaggerURecipe() {
        ItemStack item = new Weapons().DaggerOfShatteredDimensions();

        NamespacedKey key = new NamespacedKey(VDVCraftRevamp.getInstance(),"DaggerURecipe");

        ShapedRecipe recipe = new ShapedRecipe(key, item);

        recipe.shape("S S", "SRS", "SDS");

        recipe.setIngredient('S', new Materials().ShatteredShard());
        recipe.setIngredient('R', new Materials().RippedDimension());
        recipe.setIngredient('D', new Weapons().Dagger());
        // TODO fix daggerpart of recipe not working

        Bukkit.addRecipe(recipe);
    }
    public static void loadRecipes() {
        DaggerRecipe();
        DaggerURecipe();
    }
}
