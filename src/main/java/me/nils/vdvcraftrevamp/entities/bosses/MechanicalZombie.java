package me.nils.vdvcraftrevamp.entities.bosses;

import me.nils.vdvcraftrevamp.constants.Ability;
import me.nils.vdvcraftrevamp.constants.MobType;
import me.nils.vdvcraftrevamp.data.EntityData;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;

public class MechanicalZombie extends EntityData {
    public MechanicalZombie(Location loc) {
        super("Mechanical Zombie", Ability.NONE, MobType.BOSS, Flow.BURNING);
        Zombie zombie = (Zombie) loc.getWorld().spawnEntity(loc, EntityType.ZOMBIE);

        ItemStack helmet = new ItemStack(Material.CHAINMAIL_HELMET);
        ArmorMeta meta = (ArmorMeta) helmet.getItemMeta();
        ArmorTrim trim = new ArmorTrim(TrimMaterial.COPPER, TrimPattern.SENTRY);
        meta.setTrim(trim);
        helmet.setItemMeta(meta);

        ItemStack chestplate = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
        meta = (ArmorMeta) chestplate.getItemMeta();
        meta.setTrim(trim);
        chestplate.setItemMeta(meta);

        ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
        LeatherArmorMeta meta1 = (LeatherArmorMeta) leggings.getItemMeta();
        meta1.setColor(Color.ORANGE);
        leggings.setItemMeta(meta1);

        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        meta1 = (LeatherArmorMeta) boots.getItemMeta();
        meta1.setColor(Color.BLACK);
        boots.setItemMeta(meta1);

        zombie.getEquipment().setHelmet(helmet);
        zombie.getEquipment().setChestplate(chestplate);
        zombie.getEquipment().setLeggings(leggings);
        zombie.getEquipment().setBoots(boots);


        // TODO add weapon
        zombie.setCustomName("Mechanical Zombie");
        zombie.setCustomNameVisible(true);


        zombie.setMaxHealth(300);
        zombie.setHealth(300);
    }
}
