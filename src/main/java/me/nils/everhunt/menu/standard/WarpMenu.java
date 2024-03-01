package me.nils.everhunt.menu.standard;

import me.nils.everhunt.data.TeleportData;
import me.nils.everhunt.managers.ItemManager;
import me.nils.everhunt.menu.Menu;
import me.nils.everhunt.menu.PlayerMenuUtility;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Objects;

public class WarpMenu extends Menu {
    public WarpMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "Warp Menu";
    }

    @Override
    public int getSlots() {
        return 27;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        String uuid = player.getUniqueId().toString();
        player.closeInventory();
        player.teleport(Objects.requireNonNull(TeleportData.getCoords(uuid, Objects.requireNonNull(e.getCurrentItem()).getItemMeta().getLocalizedName())));
    }

    @Override
    public void setMenuItems() {
        String uuid = playerMenuUtility.getOwner().getUniqueId().toString();
        ArrayList<String> teleportList = TeleportData.getLocations(uuid);
        ArrayList<ItemStack> menuItems = new ArrayList<>();

        for (String loc : teleportList) {
            menuItems.add(ItemManager.items.get(loc).getItemStack());
        }

        ItemStack[] contents = new ItemStack[menuItems.size()];
        contents = menuItems.toArray(contents);

        inventory.setContents(contents);
        setFillerGlass();
    }
}
