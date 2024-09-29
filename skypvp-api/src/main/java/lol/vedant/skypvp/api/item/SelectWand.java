package lol.vedant.skypvp.api.item;

import lol.vedant.skypvp.api.utils.Utils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class SelectWand extends ItemStack {

    public SelectWand() {
        super(Material.BLAZE_ROD);
        ItemMeta itemMeta = this.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setDisplayName(Utils.cc("&a&lSelect Wand"));
            List<String> lore = new ArrayList<>();
            lore.add(Utils.cc("&7Use this wand to select the"));
            lore.add(Utils.cc("&7spawn region"));
            itemMeta.setLore(lore);
            this.setItemMeta(itemMeta);
        }
    }
}
