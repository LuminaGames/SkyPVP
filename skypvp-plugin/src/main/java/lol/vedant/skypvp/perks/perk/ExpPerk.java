package lol.vedant.skypvp.perks.perk;

import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.XPotion;
import fr.mrmicky.fastinv.ItemBuilder;
import lol.vedant.skypvp.SkyPVP;
import lol.vedant.skypvp.api.config.Message;
import lol.vedant.skypvp.api.perks.Perk;
import lol.vedant.skypvp.api.perks.PerkType;
import lol.vedant.skypvp.api.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import static lol.vedant.skypvp.SkyPVP.messages;

public class ExpPerk implements Perk, Listener {

    private final String name;
    private final long price;
    private static SkyPVP plugin = SkyPVP.getPlugin();

    public ExpPerk(String name, long price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public PerkType getType() {
        return PerkType.EXPERIENCE;
    }

    @Override
    public long getPrice() {
        return price;
    }

    @Override
    public void apply(Player player) {
        player.giveExpLevels(1);
    }

    @Override
    public void remove(Player player) {
        player.giveExp(-1);
    }

    @Override
    public boolean isActive(Player player) {
        return player.hasPotionEffect(XPotion.STRENGTH.getPotionEffectType());
    }

    @Override
    public ItemStack getDisplayItem() {
        return new ItemBuilder(XMaterial.ANVIL.parseMaterial())
                .name(Utils.cc(messages.getString(Message.PERK_EXPERIENCE_TITLE)))
                .lore(Utils.cc(messages.getList(Message.PERK_EXPERIENCE_DESCRIPTION)))
                .build();
    }

    @EventHandler
    public void onPlayerKill(PlayerDeathEvent e) {
        Player killer = e.getEntity().getKiller();

        if(killer == null) {
            return;
        }

        if(SkyPVP.getPlugin().getDb().getActivePerk(killer.getUniqueId()).equals(PerkType.EXPERIENCE)) {
            apply(killer);
        }
    }

}
