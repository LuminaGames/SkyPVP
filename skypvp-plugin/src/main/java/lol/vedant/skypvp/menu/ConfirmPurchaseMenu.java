package lol.vedant.skypvp.menu;

import com.cryptomorin.xseries.XMaterial;
import fr.mrmicky.fastinv.FastInv;
import fr.mrmicky.fastinv.ItemBuilder;
import lol.vedant.skypvp.api.kit.Kit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@SuppressWarnings("ALL")
public class ConfirmPurchaseMenu extends FastInv {

    public ConfirmPurchaseMenu(Kit kit) {
        super(27, "Confirm Purchase");

        ItemStack confirmItem = new ItemBuilder(XMaterial.EMERALD.parseMaterial())
                .name("&aConfirm Purchase")
                .lore("&7Price: " + kit.getPrice())
                .build();

        ItemStack cancelItem = new ItemBuilder(XMaterial.REDSTONE_BLOCK.parseMaterial())
                .name("&cCancel")
                .build();

        setItem(11, confirmItem, e -> buyKit((Player) e.getWhoClicked(), kit));
        setItem(15, cancelItem, e -> e.getWhoClicked().closeInventory());

        // Placeholder items for empty slots
        ItemStack placeholder = new ItemBuilder(XMaterial.GRAY_STAINED_GLASS_PANE.parseMaterial()).name(" ").build();
        for (int i = 0; i < getInventory().getSize(); i++) {
            if (getInventory().getItem(i) == null) {
                setItem(i, placeholder);
            }
        }
    }

    private void buyKit(Player player, Kit kit) {
        // Implement your buying logic here
        player.sendMessage("You have bought the kit: " + kit.getId());
        player.closeInventory();
    }
}
