package me.nils.everhunt.menu.standard;

import me.nils.everhunt.constants.ItemType;
import me.nils.everhunt.data.CostNPCData;
import me.nils.everhunt.data.PlayerData;
import me.nils.everhunt.managers.ItemManager;
import me.nils.everhunt.menu.PaginatedMenu;
import me.nils.everhunt.menu.PlayerMenuUtility;
import me.nils.everhunt.utils.Chat;
import me.nils.everhunt.utils.Condition;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class NPCSellMenu extends PaginatedMenu {
    private final ArrayList<ItemStack> sellList;
    public NPCSellMenu(PlayerMenuUtility playerMenuUtility, ItemStack... sellList) {
        super(playerMenuUtility);
        for (ItemStack itemStack : sellList) {
                if (itemStack == null) {
                    itemStack = new ItemStack(Material.AIR);
                }

                ItemMeta meta = itemStack.getItemMeta();
                if (meta != null) {
                    if (CostNPCData.data.get(ChatColor.stripColor(meta.getDisplayName())) != null) {
                        int cost = CostNPCData.data.get(ChatColor.stripColor(meta.getDisplayName())).getCost();

                        List<String> lore = meta.getLore();
                        lore.add(Chat.color("&7Cost: &e" + cost));
                    }
                }
        }
        this.sellList = sellList;
    }

    @Override
    public String getMenuName() {
        return "Sell Menu";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        String uuid = player.getUniqueId().toString();

        handlePages(player,sellList,e);

        if (inventory.contains(e.getCursor())) {
            CostNPCData data = CostNPCData.data.get(e.getCurrentItem().displayName().toString());

            PlayerData pData = PlayerData.data.get(uuid);

            pData.pay(data.getCost());

            player.getInventory().addItem(e.getCursor());
        } else {
            if (Condition.isCustom(ItemType.ITEM,ChatColor.stripColor(e.getCursor().getItemMeta().getDisplayName()))) {
                ItemManager item = ItemManager.items.get(ChatColor.stripColor(e.getCursor().getItemMeta().getDisplayName()));
                player.getInventory().remove(e.getCursor());
                PlayerData.data.get(uuid).addCoins(item.getValue());
            }
        }
    }

    @Override
    public void setMenuItems() {
        addMenuBorder();
        insertContents(sellList);
    }
}
