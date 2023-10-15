package me.nils.everhunt.entities.bosses;

import me.nils.everhunt.Everhunt;
import me.nils.everhunt.entities.ThunderBolt;
import me.nils.everhunt.items.weapons.AzureWrath;
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
        ItemStack azurewrath = new AzureWrath().getItemStack();
        bones.getEquipment().setItemInMainHand(azurewrath);
        bones.getEquipment().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
        bones.getPersistentDataContainer().set(Everhunt.getKey(), PersistentDataType.STRING,"boss");
        bones.clearLootTable();

        bones.setMaxHealth(100);
        bones.setHealth(100);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (bones.isDead()) {
                    cancel();
                }
                List<Entity> entityList = bones.getNearbyEntities(4,4,4);
                Location loc1;
                for (int i = 0; i < entityList.size(); i++) {
                    if (!(entityList.get(i) instanceof Item)) {
                        loc1 = entityList.get(i).getLocation();
                        double damage = 5;
                        new ThunderBolt(loc1, damage);
                    }
                }
            }
        }.runTaskTimer(Everhunt.getInstance(),100L,100L);
    }
}
