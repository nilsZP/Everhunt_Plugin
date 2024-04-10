package me.nils.everhunt.entities.bosses;

import me.nils.everhunt.Everhunt;
import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.constants.MobType;
import me.nils.everhunt.constants.Tier;
import me.nils.everhunt.data.EntityData;
import me.nils.everhunt.entities.abilities.ThunderBolt;
import me.nils.everhunt.items.Items;
import me.nils.everhunt.managers.WeaponManager;
import me.nils.everhunt.utils.Chat;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class ThunderBones extends EntityData {
    public ThunderBones(Location loc) {
        super("Thunder Bones",Tier.SOUL, MobType.BOSS);
        WitherSkeleton bones = (WitherSkeleton) loc.getWorld().spawnEntity(loc, EntityType.WITHER_SKELETON);

        bones.setCustomName(Chat.color(String.format("%s &c%d&f/&c%d%s", super.getDisplayName(), 300, 300,"♥")));
        bones.setCustomNameVisible(true);
        bones.getPersistentDataContainer().set(Everhunt.getKey(),PersistentDataType.STRING,super.getDisplayName());
        bones.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(300);
        bones.setHealth(bones.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
        bones.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(10);
        bones.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(10);
        bones.getEquipment().setHelmet(Items.getBase("ThunderBones Head"));
        bones.getEquipment().setItemInMainHand(Items.getBase("AzureWrath"));

        super.setEntity(bones);

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
