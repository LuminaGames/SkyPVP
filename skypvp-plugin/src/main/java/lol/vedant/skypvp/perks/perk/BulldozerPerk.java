package lol.vedant.skypvp.perks.perk;

import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.XPotion;
import fr.mrmicky.fastinv.ItemBuilder;
import lol.vedant.skypvp.SkyPVP;
import lol.vedant.skypvp.api.config.Message;
import lol.vedant.skypvp.api.perks.Perk;
import lol.vedant.skypvp.api.perks.PerkType;
import lol.vedant.skypvp.api.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import static lol.vedant.skypvp.SkyPVP.messages;

public class BulldozerPerk implements Perk, Listener {

    private final String name;
    private final long price;

    public BulldozerPerk(String name, long price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public PerkType getType() {
        return PerkType.BULLDOZER;
    }

    @Override
    public long getPrice() {
        return price;
    }

    @Override
    public void apply(Player player) {
        player.addPotionEffect(new PotionEffect(XPotion.STRENGTH.getPotionEffectType(), 100, 0));
    }

    @Override
    public void remove(Player player) {
        player.getActivePotionEffects().clear();
    }

    @Override
    public boolean isActive(Player player) {
        return player.hasPotionEffect(XPotion.STRENGTH.getPotionEffectType());
    }

    @Override
    public ItemStack getDisplayItem() {
        return new ItemBuilder(XMaterial.NETHER_WART.parseMaterial())
                .name(Utils.cc(messages.getString(Message.PERK_BULLDOZER_TITLE)))
                .lore(Utils.cc(messages.getList(Message.PERK_BULLDOZER_DESCRIPTION)))
                .build();
    }

    @EventHandler
    public void onPlayerKill(PlayerDeathEvent e) {
        Player player = e.getEntity();
        Player killer = player.getKiller();

        if(killer == null) {
            return;
        }

        if(SkyPVP.getPlugin().getDb().getActivePerk(killer.getUniqueId()).equals(PerkType.BULLDOZER)) {
            apply(killer);
        }

        player.sendMessage(Utils.cc("&aYou got 5 seconds of strength for killing &c" + player.getName()));
    }
}
