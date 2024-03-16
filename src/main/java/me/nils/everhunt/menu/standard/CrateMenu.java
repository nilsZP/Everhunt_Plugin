package me.nils.everhunt.menu.standard;

import me.nils.everhunt.Everhunt;
import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.data.EntityData;
import me.nils.everhunt.data.MarketData;
import me.nils.everhunt.data.PlayerData;
import me.nils.everhunt.entities.Wolfling;
import me.nils.everhunt.entities.loottables.Loot;
import me.nils.everhunt.managers.ItemManager;
import me.nils.everhunt.menu.Menu;
import me.nils.everhunt.menu.PlayerMenuUtility;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class CrateMenu extends Menu {
    private ItemStack crate;

    public CrateMenu(PlayerMenuUtility playerMenuUtility, ItemStack crate) {
        super(playerMenuUtility);
        this.crate = crate;
    }

    @Override
    public String getMenuName() {
        return crate.getItemMeta().getDisplayName();
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player player = playerMenuUtility.getOwner();
        String crateName = ChatColor.stripColor(crate.getItemMeta().getDisplayName());
        ItemManager item = ItemManager.items.get(crateName);

        if (e.getCurrentItem().getType().equals(Material.BARRIER)) {
            if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Close")) player.closeInventory();
        }

        if (e.getCurrentItem().getType().equals(Material.GOLD_BLOCK)) {
            if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Open")) {
                PlayerData data = PlayerData.data.get(player.getUniqueId().toString());

                if (!data.pay(item.getValue())) return;
                inventory.setItem(31, FILLER_GLASS);
                inventory.setItem(49,FILLER_GLASS);
                new BukkitRunnable() {
                    private int progress = 0;
                    private int[] list = {0,1,2,3,4,5,6,7,8,17,26,35,44,53,52,51,50,49,48,47,46,45,36,27,18,9,0};
                    @Override
                    public void run() {
                        if (progress < list.length) {
                            inventory.setItem(list[progress],makeItem(Material.YELLOW_STAINED_GLASS_PANE,ChatColor.YELLOW + "ROLLING..."));
                            if (progress > 0) {
                                inventory.setItem(list[progress-1],FILLER_GLASS);
                            }
                            progress++;
                        } else {
                            inventory.setItem(22, Loot.getLootTable(crateName).getRandom());
                            inventory.setItem(49,CLOSE_BUTTON);
                            cancel();
                        }
                    }
                }.runTaskTimer(Everhunt.getInstance(), 20L,20L);
            }
        }
    }

    @Override
    public void setMenuItems() {
        inventory.setItem(22, crate);

        inventory.setItem(31, makeItem(Material.GOLD_BLOCK, ChatColor.GOLD + "Open"));

        inventory.setItem(49, CLOSE_BUTTON);
        setFillerGlass();
    }
}
