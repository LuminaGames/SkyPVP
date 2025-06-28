package lol.vedant.skypvp.menu;

import com.cryptomorin.xseries.XSound;
import fr.mrmicky.fastinv.FastInv;
import lol.vedant.skypvp.SkyPVP;
import lol.vedant.skypvp.api.config.Message;
import lol.vedant.skypvp.api.perks.Perk;
import lol.vedant.skypvp.api.perks.PerkType;
import lol.vedant.skypvp.api.stats.PerkStats;
import lol.vedant.skypvp.api.utils.Utils;
import lol.vedant.skypvp.perks.PerkManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

import static lol.vedant.skypvp.SkyPVP.config;
import static lol.vedant.skypvp.SkyPVP.messages;

public class PerksMenu extends FastInv {

    private final SkyPVP plugin;
    private final Player player;
    private PerkStats perkStats;
    private PerkType activePerk;
    private PerkManager manager;

    public PerksMenu(Player player) {
        super(27, Utils.cc("&6Your Perks"));
        this.plugin = SkyPVP.getPlugin();
        this.player = player;
        this.manager = plugin.getPerkManager();
        setItems();
    }


    private void setItems() {
        this.perkStats = plugin.getDb().getPerks(player.getUniqueId());
        this.activePerk = plugin.getDb().getActivePerk(player.getUniqueId());

        System.out.println(perkStats.hasBulldozer());
        System.out.println(activePerk);

        int slot = 10;
        for (PerkType perkType : PerkType.values()) {
            if (perkType == PerkType.NONE) continue;

            ItemStack perkItem = manager.getPerk(perkType).getDisplayItem();
            String pricePath = "perks." + perkType.name().toLowerCase() + ".price";
            String displayMessage;

            if (isPerkUnlocked(perkType)) {
                if (activePerk == perkType) {
                    displayMessage = Utils.cc("&aCurrently Active");
                } else {
                    displayMessage = Utils.cc("&aUnlocked! &eClick to equip");
                }
            } else {
                String price = config.getString(pricePath);
                displayMessage = Utils.cc("&fBuy for: &6" + price);
            }

            setItem(slot++, replacePlaceholder(perkItem, displayMessage));
        }
    }



    @Override
    public void onClick(InventoryClickEvent e) {
        int slot = e.getSlot();
        PerkType perk = PerkType.NONE;

        if(slot == 10) {
            perk = PerkType.BULLDOZER;
        } else if(slot == 11) {
            perk = PerkType.EXPERIENCE;
        } else if(slot == 12) {
            perk = PerkType.SPEED;
        } else if (slot == 13) {
            perk = PerkType.JUGGERNAUT;
        }

        if(perk == activePerk) {
            player.sendMessage(Utils.cc(messages.getString(Message.PERK_ALREADY_ACTIVE)));
            player.playSound(player.getLocation(), XSound.BLOCK_ANVIL_FALL.parseSound(), 1, 1);
        } else if(isPerkUnlocked(perk)) {
            plugin.getDb().setActivePerk(player.getUniqueId(), perk);
            player.sendMessage(messages.getString(Message.PERK_EQUIPPED));
            player.playSound(player.getLocation(), XSound.ENTITY_EXPERIENCE_ORB_PICKUP.parseSound(), 1, 1);
            refresh();
        } else {
            if (buyPerk(player, perk)) {
                plugin.getDb().setActivePerk(player.getUniqueId(), perk);
                player.sendMessage(Utils.cc(messages.getString(Message.PERK_PURCHASED_SUCCESSFULLY)));
                player.playSound(player.getLocation(), XSound.ENTITY_EXPERIENCE_ORB_PICKUP.parseSound(), 1, 1);
                refresh();
            } else {
                player.sendMessage(Utils.cc(messages.getString(Message.NOT_ENOUGH_BALANCE)));
                player.playSound(player.getLocation(), XSound.BLOCK_ANVIL_FALL.parseSound(), 1, 1);
            }
        }
    }

    private boolean isPerkUnlocked(PerkType perkType) {
        switch (perkType) {
            case BULLDOZER:
                return perkStats.hasBulldozer();
            case SPEED:
                return perkStats.hasSpeed();
            case EXPERIENCE:
                return perkStats.hasExperience();
            case JUGGERNAUT:
                return perkStats.hasJuggernaut();
            default:
                return false;
        }
    }

    private ItemStack replacePlaceholder(ItemStack item, String message) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return item;

        List<String> lore = meta.getLore();
        if (lore == null) return item;

        List<String> updatedLore = new ArrayList<>();
        for (String line : lore) {
            updatedLore.add(line.replace("{perkStats}", message));
        }
        meta.setLore(updatedLore);
        item.setItemMeta(meta);

        return item;
    }


    private boolean buyPerk(Player player, PerkType perkType) {
        Economy economy = plugin.getEconomy();
        String pricePath = "perks." + perkType.name().toLowerCase() + ".price";
        double price = config.getDouble(pricePath);
        double playerBalance = economy.getBalance(player);

        if (playerBalance >= price) {
            economy.withdrawPlayer(player, price);
            plugin.getDb().addPerk(player.getUniqueId(), perkType);
            return true;
        }
        return false;
    }

    public void refresh() {
        getInventory().clear();
        setItems();
    }
}
