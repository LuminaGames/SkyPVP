package lol.vedant.skypvp.perks.perk;

import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.XPotion;
import fr.mrmicky.fastinv.ItemBuilder;
import lol.vedant.skypvp.api.config.Message;
import lol.vedant.skypvp.api.perks.Perk;
import lol.vedant.skypvp.api.perks.PerkType;
import lol.vedant.skypvp.api.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import static lol.vedant.skypvp.SkyPVP.messages;

public class JuggernautPerk implements Perk, Listener {

    private final int price;
    private final String name;

    public JuggernautPerk(String name, int price) {
        this.price = price;
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public PerkType getType() {
        return PerkType.JUGGERNAUT;
    }

    @Override
    public long getPrice() {
        return price;
    }

    @Override
    public void apply(Player player) {
        player.addPotionEffect(XPotion.RESISTANCE.buildPotionEffect(200, 0));
    }

    @Override
    public void remove(Player player) {
        player.getActivePotionEffects().clear();
    }

    @Override
    public boolean isActive(Player player) {
        return player.hasPotionEffect(XPotion.RESISTANCE.getPotionEffectType());
    }

    @Override
    public ItemStack getDisplayItem() {
        return new ItemBuilder(XMaterial.DIAMOND_CHESTPLATE.parseMaterial())
                .name(Utils.cc(messages.getString(Message.PERK_JUGGERNAUT_TITLE)))
                .lore(Utils.cc(messages.getList(Message.PERK_JUGGERNAUT_DESCRIPTION)))
                .build();
    }
}
