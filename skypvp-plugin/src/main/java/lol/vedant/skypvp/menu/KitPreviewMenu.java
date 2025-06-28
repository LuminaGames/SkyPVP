package lol.vedant.skypvp.menu;

import com.cryptomorin.xseries.XMaterial;
import fr.mrmicky.fastinv.FastInv;
import fr.mrmicky.fastinv.ItemBuilder;
import lol.vedant.skypvp.SkyPVP;
import lol.vedant.skypvp.api.kit.Kit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import java.util.Map;

public class KitPreviewMenu extends FastInv {

    SkyPVP plugin = SkyPVP.getPlugin();

    public KitPreviewMenu(Kit kit) {
        super(54, kit.getDisplayName() + " Preview");

        // Add inventory items
        for (Map.Entry<Integer, ItemStack> entry : kit.getInventory().entrySet()) {
            if(entry.getKey() > 35) {
                break;
            }
            setItem(entry.getKey(), entry.getValue());
        }

        // Add armor items
        if (kit.getKitHelmet() != null) {
            setItem(45, kit.getKitHelmet());
        }
        if (kit.getKitChestplate() != null) {
            setItem(46, kit.getKitChestplate());
        }
        if (kit.getKitLeggings() != null) {
            setItem(47, kit.getKitLeggings());
        }
        if (kit.getKitBoots() != null) {
            setItem(48, kit.getKitBoots());
        }

        // Add off-hand item
        if (kit.getOffHand() != null) {
            setItem(49, kit.getOffHand());
        }

        // Add placeholder items for empty slots
        ItemStack placeholder = new ItemBuilder(XMaterial.GRAY_STAINED_GLASS_PANE.parseMaterial()).name(" ").build();
        for (int i = 0; i < getInventory().getSize(); i++) {
            if (getInventory().getItem(i) == null) {
                setItem(i, placeholder);
            }
        }
    }
}
