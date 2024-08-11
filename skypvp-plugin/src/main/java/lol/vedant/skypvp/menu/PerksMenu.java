package lol.vedant.skypvp.menu;

import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.XSound;
import fr.mrmicky.fastinv.FastInv;
import fr.mrmicky.fastinv.ItemBuilder;
import lol.vedant.skypvp.SkyPVP;
import lol.vedant.skypvp.api.perks.PerkType;
import lol.vedant.skypvp.api.stats.PerkStats;
import lol.vedant.skypvp.api.utils.Utils;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static lol.vedant.skypvp.SkyPVP.config;

public class PerksMenu extends FastInv {

    private final SkyPVP plugin;
    private final Player player;
    private PerkStats perkStats;
    private PerkType activePerk;

    public PerksMenu(Player player) {
        super(27, Utils.cc("&6Your Perks"));
        this.plugin = SkyPVP.getPlugin();
        this.player = player;
        setItems();
    }


    private void setItems() {
        ItemStack bulldozerItem = new ItemBuilder(XMaterial.NETHER_WART.parseMaterial())
                .name(Utils.cc("&6Bulldozer Perk"))
                .lore(Utils.cc(Arrays.asList("&7On killing a player get", "&7Strength I effect for 3 seconds", "", "{perkStats}")))
                .build();

        ItemStack expItem = new ItemBuilder(XMaterial.ANVIL.parseMaterial())
                .name(Utils.cc("&6Experience Perk"))
                .lore(Utils.cc(Arrays.asList("&7On killing a player get", "&7a level of experience level", "", "{perkStats}")))
                .build();

        ItemStack speedItem = new ItemBuilder(XMaterial.FEATHER.parseMaterial())
                .name(Utils.cc("&6Speed Perk"))
                .lore(Utils.cc(Arrays.asList("&7On killing a player get", "&7Speed II effect for 5 seconds", "", "{perkStats}")))
                .build();

        this.perkStats = plugin.getDb().getPerks(player.getUniqueId());
        this.activePerk = plugin.getDb().getActivePerk(player.getUniqueId());

        //Bulldozer
        if(perkStats.hasBulldozer()  && activePerk != PerkType.BULLDOZER) {
            setItem(10, replacePlaceholder(bulldozerItem, Utils.cc("&aUnlocked! &eClick to equip")));
        } else if (activePerk.equals(PerkType.BULLDOZER)) {
            setItem(10, replacePlaceholder(bulldozerItem, Utils.cc("&aCurrently Active")));
        } else {
            setItem(10, replacePlaceholder(bulldozerItem, Utils.cc("&fBuy for: &6 " + config.getString("perks.bulldozer.price"))));
        }

        if(perkStats.hasExperience() && activePerk != PerkType.EXPERIENCE) {
            setItem(11, replacePlaceholder(expItem, Utils.cc("&aUnlocked! &eClick to equip")));
        } else if (activePerk.equals(PerkType.EXPERIENCE)) {
            setItem(11, replacePlaceholder(expItem, Utils.cc("&aCurrently Active")));
        } else {
            setItem(11, replacePlaceholder(expItem, Utils.cc("&fBuy for: &6 " + config.getString("perks.experience.price"))));
        }

        if(perkStats.hasSpeed()  && activePerk != PerkType.SPEED) {
            setItem(12, replacePlaceholder(speedItem, Utils.cc("&aUnlocked! &eClick to equip")));
        } else if (activePerk.equals(PerkType.SPEED)) {
            setItem(12, replacePlaceholder(speedItem, Utils.cc("&aCurrently Active")));
        } else {
            setItem(12, replacePlaceholder(speedItem, Utils.cc("&fBuy for: &6 " + config.getString("perks.speed.price"))));
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
        }

        if(perk == activePerk) {
            player.sendMessage(Utils.cc("&cThat perk is already active"));
            player.playSound(player.getLocation(), XSound.BLOCK_ANVIL_FALL.parseSound(), 1, 1);
        } else if(isPerkUnlocked(perk)) {
            plugin.getDb().setActivePerk(player.getUniqueId(), perk);
            player.sendMessage("&aYou have equipped the " + perk.name() + "perk");
            player.playSound(player.getLocation(), XSound.ENTITY_EXPERIENCE_ORB_PICKUP.parseSound(), 1, 1);
            refresh();
        } else {
            if (buyPerk(player, perk)) {
                plugin.getDb().setActivePerk(player.getUniqueId(), perk);
                player.sendMessage(Utils.cc("&aYou have purchased and equipped the " + perk.name() + " perk."));
                player.playSound(player.getLocation(), XSound.ENTITY_EXPERIENCE_ORB_PICKUP.parseSound(), 1, 1);
                refresh();
            } else {
                player.sendMessage(Utils.cc("&cYou do not have enough coins to purchase this perk."));
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
