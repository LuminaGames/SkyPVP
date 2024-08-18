package lol.vedant.skypvp.menu;

import fr.mrmicky.fastinv.FastInv;
import lol.vedant.skypvp.api.utils.Utils;

public class KitsMenu extends FastInv {

    public KitsMenu(int size, String title) {
        super(54, Utils.cc("&6Kits Menu"));
        setItems();

    }


    public void setItems() {

    }

    public void refresh() {
        this.getInventory().clear();
        setItems();
    }
}
