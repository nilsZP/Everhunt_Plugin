package me.nils.vdvcraftrevamp.items;

import me.nils.vdvcraftrevamp.VDVCraftRevamp;
import me.nils.vdvcraftrevamp.items.materials.Materials;
import me.nils.vdvcraftrevamp.items.weapons.Weapons;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class Recipes {

	/*
        This solution is not ideal, it uses this itemstack to reference
        the exact same instance of the dagger itemstack in both recipes.

        Whenever reloading/restarting the server, the dagger
        in the #DaggerURecipe will not work anymore.
    */
	static ItemStack dagger;

	public static void DaggerRecipe() {
		dagger = new Weapons().Dagger();

		NamespacedKey key = new NamespacedKey(VDVCraftRevamp.getInstance(), "DaggerRecipe");

		ShapedRecipe recipe = new ShapedRecipe(key, dagger);

		recipe.shape(" S ", " I ", "   ");

		recipe.setIngredient('S', new ItemStack(Material.STICK));
		recipe.setIngredient('I', new ItemStack(Material.IRON_INGOT));

		Bukkit.addRecipe(recipe);
	}

	public static void DaggerURecipe() {
		ItemStack item = new Weapons().DaggerOfShatteredDimensions();

		NamespacedKey key = new NamespacedKey(VDVCraftRevamp.getInstance(), "DaggerURecipe");

		ShapedRecipe recipe = new ShapedRecipe(key, item);

		recipe.shape("S S", "SRS", "SDS");

		recipe.setIngredient('S', new Materials().ShatteredShard());
		recipe.setIngredient('R', new Materials().RippedDimension());
		recipe.setIngredient('D', dagger);
		// TODO fix daggerpart of recipe not working

		Bukkit.addRecipe(recipe);
	}

	public static void loadRecipes() {
		DaggerRecipe();
		DaggerURecipe();
	}
}
