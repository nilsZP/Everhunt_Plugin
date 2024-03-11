package me.nils.everhunt.entities.bosses.kings;

import me.nils.everhunt.Everhunt;
import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.constants.MobType;
import me.nils.everhunt.constants.Tier;
import me.nils.everhunt.data.EntityData;
import me.nils.everhunt.entities.Wolfling;
import me.nils.everhunt.utils.Chat;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Random;

public class WolfKing extends EntityData {
    public WolfKing(Location loc) {
        super("Wolf King",10, Tier.BASIC, Ability.ALPHA_ROAR, MobType.BOSS);
        Wolf wolf = (Wolf) loc.getWorld().spawnEntity(loc, EntityType.WOLF);

        wolf.setAngry(true);
        List<Entity> entityList = wolf.getNearbyEntities(5,5,5);
        for (Entity entity : entityList) {
            if (entity instanceof Player player) {
                wolf.setTarget(player);
            }
        }

        wolf.setCustomName(Chat.color(String.format("%s &c%d&f/&c%d%s", super.getDisplayName(), 20, 20,"♥")));
        wolf.setCustomNameVisible(true);
        wolf.getPersistentDataContainer().set(Everhunt.getKey(),PersistentDataType.STRING,super.getDisplayName());
        wolf.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);
        wolf.setHealth(wolf.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
        wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(4);
        wolf.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(10);

        super.setEntity(wolf);

        new BukkitRunnable() {
            private boolean spawn = false;
            @Override
            public void run() {
                if (wolf.isDead()) {
                    cancel();
                    return;
                }
                wolf.getWorld().playSound(wolf.getLocation(),Sound.ENTITY_WOLF_HOWL,3F,0.5F);
                Random rand = new Random();
                boolean randomBoolean = rand.nextBoolean();
                if (randomBoolean && !spawn) {
                    for (int i = 0; i <= 3; i++) {
                        int randomX = rand.nextInt(0, 4);
                        int randomZ = rand.nextInt(0, 4);
                        new Wolfling(wolf.getLocation().add(randomX, 0, randomZ));
                        this.spawn = true;
                    }
                }
                Location loc1;
                    loc1 = wolf.getEyeLocation().toVector().add(wolf.getLocation().getDirection().multiply(2)).
                            toLocation(wolf.getWorld(), wolf.getLocation().getYaw(), wolf.getLocation().getPitch());
                    Snowball snowball = (Snowball) loc1.getWorld().spawnEntity(loc1, EntityType.SNOWBALL);

                    snowball.setVelocity(wolf.getLocation().getDirection().multiply(1.5));

                    snowball.setCustomName("roar");
                    snowball.setCustomNameVisible(false);
                    snowball.getPersistentDataContainer().set(Everhunt.getKey(), PersistentDataType.DOUBLE, wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getBaseValue() * Ability.ALPHA_ROAR.getDamageMultiplier());
                    snowball.getWorld().spawnParticle(Particle.SONIC_BOOM,snowball.getLocation(),1);
                    snowball.getWorld().playSound(snowball.getLocation(), Sound.ENTITY_WARDEN_SONIC_BOOM,3F,1F);
            }
        }.runTaskTimer(Everhunt.getInstance(),EntityData.data.get("Wolf King").getAbility().getCooldown() * 20L,EntityData.data.get("Wolf King").getAbility().getCooldown() * 20L);
    }
}
