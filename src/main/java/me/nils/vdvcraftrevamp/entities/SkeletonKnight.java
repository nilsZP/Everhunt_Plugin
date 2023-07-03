package me.nils.vdvcraftrevamp.entities;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Skeleton;
import org.bukkit.inventory.ItemStack;

public class SkeletonKnight {
    public SkeletonKnight(Location loc) {
        Skeleton skeleton = (Skeleton) loc.getWorld().spawnEntity(loc, EntityType.SKELETON);

        skeleton.setCustomName("Skeleton Knight");
        skeleton.setCustomNameVisible(true);
        skeleton.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));
        skeleton.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
        skeleton.getEquipment().setHelmet(new ItemStack(Material.IRON_HELMET));
        skeleton.getEquipment().setItemInMainHand(new ItemStack(Material.IRON_SWORD));

        skeleton.setMaxHealth(50);
        skeleton.setHealth(50);
    }
}
