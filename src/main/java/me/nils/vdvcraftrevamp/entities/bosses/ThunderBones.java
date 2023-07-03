package me.nils.vdvcraftrevamp.entities.bosses;

import me.nils.vdvcraftrevamp.VDVCraftRevamp;
import me.nils.vdvcraftrevamp.entities.ThunderBolt;
import me.nils.vdvcraftrevamp.items.weapons.Weapons;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class ThunderBones {
    public ThunderBones(Location loc) {
        WitherSkeleton bones = (WitherSkeleton) loc.getWorld().spawnEntity(loc, EntityType.WITHER_SKELETON);

        bones.setCustomName("Thunder Bones");
        bones.setCustomNameVisible(true);
        ItemStack azurewrath = new Weapons().AzureWrath();
        bones.getEquipment().setItemInMainHand(azurewrath);
        bones.getEquipment().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
        bones.getPersistentDataContainer().set(VDVCraftRevamp.getKey(), PersistentDataType.STRING,"boss");
        bones.clearLootTable();

        bones.setMaxHealth(100);
        bones.setHealth(100);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (bones.isDead()) {
                    return;
                }
                List<Entity> entityList = bones.getNearbyEntities(4,4,4);
                Location loc1;
                for (int i = 0; i < entityList.size(); i++) {
                    if (!(entityList.get(i) instanceof Item)) {
                        loc1 = entityList.get(i).getLocation();
                        new ThunderBolt(loc1);
                    }
                }
            }
        }.runTaskTimer(VDVCraftRevamp.getInstance(),100L,100L);
    }
}
