package me.nils.everhunt.entities;

import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.constants.MobType;
import me.nils.everhunt.data.EntityData;
import me.nils.everhunt.managers.ArmorManager;
import me.nils.everhunt.utils.Head;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class UndeadScarecrow extends EntityData {
    public UndeadScarecrow(Location loc) {
        super("Undead Scarecrow",5,15, Ability.NONE, MobType.ENEMY);
        Zombie scare = (Zombie) loc.getWorld().spawnEntity(loc, EntityType.ZOMBIE);

        scare.setCustomName("Skeleton Knight");
        scare.setCustomNameVisible(true);
        scare.getEquipment().setBoots(ArmorManager.items.get("Farmers Boots").getItemStack());
        scare.getEquipment().setChestplate(ArmorManager.items.get("Farmers Shirt").getItemStack());
        scare.getEquipment().setLeggings(ArmorManager.items.get("Farmers Pants").getItemStack());

        ItemStack helmet = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta helmetMeta = (SkullMeta) helmet.getItemMeta();

        helmetMeta.setPlayerProfile(Head.createTexture("https://textures.minecraft.net/texture/d21aaf17d55689f55a23a5d0cb55b75901e8bb44e6d485f51edfedf07e05ccbe"));

        helmet.setItemMeta(helmetMeta);

        scare.getEquipment().setHelmet(helmet);
        scare.getEquipment().setItemInMainHand(new ItemStack(Material.STICK));

        scare.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(15);
        scare.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(4);
        scare.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(4);

        Hologram.addHologram(scare);
    }
}
