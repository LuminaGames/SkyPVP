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

    public KitsMenu(Map<String, Kit> kitsMap, int page) {
        super(54, Utils.cc("&6Kits Menu"));
        this.kits = new ArrayList<>(kitsMap.values());
        this.page = page;
        this.kitsMap = kitsMap;
        setItems();
    }

    private void setItems() {
        int startIndex = (page - 1) * 10;
        int endIndex = Math.min(startIndex + 10, kits.size());

        for (int i = startIndex; i < endIndex; i++) {
            Kit kit = kits.get(i);

            ItemStack displayItem;

            if(kit.getDisplayIcon() == null) {
                displayItem = new ItemBuilder(XMaterial.IRON_SWORD.parseMaterial())
                        .name(kit.getDisplayName())
                        .lore(Utils.cc(Arrays.asList("&7Left-click to buy", "&7Right-click to preview", "&ePrice: &f" + kit.getPrice())))
                        .build();
            } else {
                displayItem = new ItemBuilder(kit.getDisplayIcon())
                        .name(kit.getDisplayName())
                        .lore(Utils.cc(Arrays.asList("&7Left-click to buy", "&7Right-click to preview", "&ePrice: &f" + kit.getPrice())))
                        .build();
            }

            setItem(i - startIndex, displayItem, e -> {
                Player player = (Player) e.getWhoClicked();
                List<String> unlockedKits = SkyPVP.getPlugin().getDb().getKitStats(player.getUniqueId());
                if (e.isLeftClick()) {
                    if(unlockedKits != null && unlockedKits.contains(kit.getId())) {
                        SkyPVP.getPlugin().getKitManager().giveKit(player, kit);
                    } else if(kit.getPrice() == 0) {
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
            setItem(45, new ItemBuilder(Material.ARROW).name("&aPrevious Page").build(), e -> new KitsMenu(kitsMap, page - 1).open((Player) e.getWhoClicked()));
        }
        if (endIndex < kits.size()) {
            setItem(53, new ItemBuilder(Material.ARROW).name("&aNext Page").build(), e -> new KitsMenu(kitsMap, page + 1).open((Player) e.getWhoClicked()));
        }

        // Placeholder items for empty slots
        ItemStack placeholder = new ItemBuilder(XMaterial.GRAY_STAINED_GLASS_PANE.parseMaterial()).name(" ").build();
        for (int i = 0; i < getInventory().getSize(); i++) {
            if (getInventory().getItem(i) == null) {
                setItem(i, placeholder);
            }
        }
    }
}
