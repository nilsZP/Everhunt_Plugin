package me.nils.everhunt.menu.standard;

import me.nils.everhunt.Everhunt;
import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.constants.ItemType;
import me.nils.everhunt.constants.Tier;
import me.nils.everhunt.data.MarketData;
import me.nils.everhunt.data.PlayerData;
import me.nils.everhunt.managers.ItemManager;
import me.nils.everhunt.managers.WeaponManager;
import me.nils.everhunt.menu.Menu;
import me.nils.everhunt.menu.PlayerMenuUtility;
import me.nils.everhunt.utils.Chat;
import me.nils.everhunt.utils.Condition;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class WeaponCreationMenu extends Menu {
    private String name;
    private int price;
    public WeaponCreationMenu(PlayerMenuUtility playerMenuUtility, String name) {
        super(playerMenuUtility);
        this.price = 700;

        if (Condition.isCustom(name)) {
            playerMenuUtility.setInput(true);
            playerMenuUtility.setInputType("Set Name");
            playerMenuUtility.getOwner().closeInventory();
            Chat.guide(playerMenuUtility.getOwner(), "Type a name in chat");
        } else {
            this.name = name;
        }
    }

    @Override
    public String getMenuName() {
        return "The Forge";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        String material = playerMenuUtility.getMaterial();
        String ability = playerMenuUtility.getAbility();

        if (e.getCurrentItem().getType().equals(Material.BARRIER)) {
            if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Close")) player.closeInventory();
            if (inventory.getItem(21).getType() != Material.WHITE_STAINED_GLASS_PANE) {
                player.getInventory().addItem(inventory.getItem(21));
            }
            if (inventory.getItem(23).getType() != Material.WHITE_STAINED_GLASS_PANE) {
                player.getInventory().addItem(inventory.getItem(23));
            }

            playerMenuUtility.setMaterial(null);
            playerMenuUtility.setAbility(null);

            return;
        }

        if (e.getCurrentItem().getType().equals(Material.CHERRY_SIGN)) {
            if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Set Name")) {
                playerMenuUtility.setInput(true);
                playerMenuUtility.setInputType("Set Name");
                player.closeInventory();
                Chat.guide(player,"Type a name in chat");

                return;
            }
        }

        if (e.getCurrentItem().getType().equals(Material.EMERALD_BLOCK)) {
            if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("CONFIRM")) {
                if (inventory.getItem(21) == null || inventory.getItem(21).getItemMeta() == null) return;
                if (inventory.getItem(23) == null || inventory.getItem(23).getItemMeta() == null) return;
                if (name.isBlank() || name.isEmpty()) return;
                if (material.isBlank() || material.isEmpty()) return;
                if (ability == null) return;

                char[] chars = {material.charAt(0),material.charAt(1)};
                String karat = (material.charAt(1) == 'k') ? String.valueOf(material.charAt(0)) : new String(chars);
                double quality = Double.parseDouble(karat);

                PlayerData data = PlayerData.data.get(player.getUniqueId().toString());

                if (!data.pay(price)) return;
                playerMenuUtility.setMaterial(null);
                playerMenuUtility.setAbility(null);
                player.getInventory().removeItem(inventory.getItem(21),inventory.getItem(23));
                player.getInventory().addItem(new WeaponManager(Material.GOLDEN_SWORD,name,WeaponManager.items.get(ability).getAbility(),Tier.SOUL,quality,quality*10,(quality/3)*10).getItemStack());
                player.closeInventory();

                return;
            }
        }

        if (!inventory.contains(e.getCursor())) {
            ItemStack itemStack = e.getCursor();
            if (!itemStack.hasItemMeta()) return;

            String name = ChatColor.stripColor(itemStack.getItemMeta().getDisplayName());

            if (Condition.isCustom(ItemType.ITEM,name)) {
                if (ItemManager.items.get(name).getTier() != Tier.SOUL) return;
                inventory.setItem(21,itemStack);
                playerMenuUtility.setMaterial(name);
                e.getCursor().subtract();
            } else if (Condition.isCustom(ItemType.WEAPON,name)) {
                inventory.setItem(23,itemStack);
                playerMenuUtility.setAbility(name);
                e.getCursor().subtract();
            }

            return;
        }
    }

    @Override
    public void setMenuItems() {
        if (playerMenuUtility.getMaterial() == null) {
            inventory.setItem(21, makeItem(Material.WHITE_STAINED_GLASS_PANE, "MATERIAL", ChatColor.RED + "BEWARE THE MATERIAL MUST BE A TYPE OF GOLD!"));
        } else {
            inventory.setItem(21,ItemManager.items.get(playerMenuUtility.getMaterial()).getItemStack());
        }
        inventory.setItem(22,makeItem(Material.CHERRY_SIGN,"SET NAME"));
        if (playerMenuUtility.getAbility() == null) {
            inventory.setItem(23, makeItem(Material.WHITE_STAINED_GLASS_PANE, "ABILITY", ChatColor.RED + "BEWARE ONLY LEFT AND RIGHT CLICK ABILITIES WORK!"));
        } else {
            inventory.setItem(23,WeaponManager.items.get(playerMenuUtility.getAbility()).getItemStack());
        }
        inventory.setItem(49,CLOSE_BUTTON);
        inventory.setItem(31,makeItem(Material.EMERALD_BLOCK, ChatColor.GREEN + "CONFIRM","Cost " + price + " coins!"));
        setFillerGlass();
    }
}
