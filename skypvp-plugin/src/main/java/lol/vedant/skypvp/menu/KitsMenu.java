package lol.vedant.skypvp.menu;

import com.cryptomorin.xseries.XMaterial;
import fr.mrmicky.fastinv.FastInv;
import fr.mrmicky.fastinv.ItemBuilder;
import lol.vedant.skypvp.SkyPVP;
import lol.vedant.skypvp.api.kit.Kit;
import lol.vedant.skypvp.api.utils.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@SuppressWarnings("ALL")
public class KitsMenu extends FastInv {

    private final List<Kit> kits;
    private final int page;
    private final Map<String, Kit> kitsMap;
    private final Player player;

    public KitsMenu(Player player, Map<String, Kit> kitsMap, int page) {
        super(54, Utils.cc("&6Kits Menu"));
        this.kits = new ArrayList<>(kitsMap.values());
        this.page = page;
        this.kitsMap = kitsMap;
        this.player = player;
        setItems();
    }

    private void setItems() {
        int startIndex = (page - 1) * 10;
        int endIndex = Math.min(startIndex + 10, kits.size());

        List<String> unlockedKits = SkyPVP.getPlugin().getDb().getKitStats(player.getUniqueId());

        for (int i = startIndex; i < endIndex; i++) {
            Kit kit = kits.get(i);
            boolean unlocked = unlockedKits != null && unlockedKits.contains(kit.getId());
            boolean free = kit.getPrice() == 0;

            String displayName = kit.getDisplayName() != null ? kit.getDisplayName() : kit.getId();

            List<String> lore = new ArrayList<>();
            if (unlocked) {
                lore.add(Utils.cc("&aUnlocked! &7Left-click to equip"));
            } else if (free) {
                lore.add(Utils.cc("&aFree! &7Left-click to claim"));
            } else {
                lore.add(Utils.cc("&7Left-click to buy"));
                lore.add(Utils.cc("&ePrice: &f" + kit.getPrice()));
            }
            lore.add(Utils.cc("&7Right-click to preview"));

            Material iconMaterial = kit.getDisplayIcon() != null ? kit.getDisplayIcon().getType() : XMaterial.IRON_SWORD.parseMaterial();

            ItemStack displayItem = new ItemBuilder(iconMaterial)
                    .name(Utils.cc(displayName))
                    .lore(Utils.cc(lore))
                    .build();

            int slot = i - startIndex;
            setItem(slot, displayItem, e -> {
                if (e.isLeftClick()) {
                    if (unlocked || free) {
                        SkyPVP.getPlugin().getKitManager().giveKit(player, kit);
                    } else {
                        new ConfirmPurchaseMenu(kit).open(player);
                    }
                } else if (e.isRightClick()) {
                    new KitPreviewMenu(kit).open(player);
                }
            });
        }

        // Navigation buttons
        if (page > 1) {
            setItem(45, new ItemBuilder(Material.ARROW).name("&aPrevious Page").build(),
                    e -> new KitsMenu(player, kitsMap, page - 1).open((Player) e.getWhoClicked()));
        }
        if (endIndex < kits.size()) {
            setItem(53, new ItemBuilder(Material.ARROW).name("&aNext Page").build(),
                    e -> new KitsMenu(player, kitsMap, page + 1).open((Player) e.getWhoClicked()));
        }

        // Fill remaining slots with placeholder
        ItemStack placeholder = new ItemBuilder(XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()).name(" ").build();
        for (int i = 0; i < getInventory().getSize(); i++) {
            if (getInventory().getItem(i) == null) {
                setItem(i, placeholder);
            }
        }
    }

}
