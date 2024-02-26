package me.nils.everhunt.entities;

import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.constants.MobType;
import me.nils.everhunt.constants.Tier;
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

public class UndeadScarecrow extends EverEntity {
    public UndeadScarecrow(Location location) {
        super(location);
    }

    @Override
    public String getDisplayName() {
        return getEntityName();
    }

    @Override
    public String getEntityName() {
        return "Undead Scarecrow";
    }

    @Override
    public boolean hasHologram() {
        return true;
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.ZOMBIE;
    }

    @Override
    public Tier getTier() {
        return Tier.BASIC;
    }

    @Override
    public double getHealth() {
        return 15;
    }

    @Override
    public double getDamage() {
        return 4;
    }

    @Override
    public double getArmor() {
        return 4;
    }

    @Override
    public MobType getMobType() {
        return MobType.ENEMY;
    }

    @Override
    public int getLevel() {
        return 5;
    }

    @Override
    public Ability getAbility() {
        return Ability.NONE;
    }

    @Override
    public void setUniqueAttributes() {
        entity.getEquipment().setBoots(ArmorManager.items.get("Farmers Boots").getItemStack());
        entity.getEquipment().setChestplate(ArmorManager.items.get("Farmers Shirt").getItemStack());
        entity.getEquipment().setLeggings(ArmorManager.items.get("Farmers Pants").getItemStack());

        ItemStack helmet = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta helmetMeta = (SkullMeta) helmet.getItemMeta();

        helmetMeta.setPlayerProfile(Head.createTexture("https://textures.minecraft.net/texture/d21aaf17d55689f55a23a5d0cb55b75901e8bb44e6d485f51edfedf07e05ccbe"));

        helmet.setItemMeta(helmetMeta);

        entity.getEquipment().setHelmet(helmet);
        entity.getEquipment().setItemInMainHand(new ItemStack(Material.STICK));
    }
}
