package lol.vedant.skypvp.menu;

import com.cryptomorin.xseries.XMaterial;
import fr.mrmicky.fastinv.FastInv;
import fr.mrmicky.fastinv.ItemBuilder;
import lol.vedant.skypvp.SkyPVP;
import lol.vedant.skypvp.api.stats.PlayerStats;
import lol.vedant.skypvp.api.utils.Utils;
import org.bukkit.entity.Player;

public class StatsMenu extends FastInv {

    private Player player;
    private SkyPVP plugin = SkyPVP.getPlugin();

    public StatsMenu(Player player) {
        super(27, Utils.cc("&6Your Stats"));
        this.player = player;

        PlayerStats stats = plugin.getDb().getStats(player.getUniqueId());

        setItem(10, new ItemBuilder(XMaterial.IRON_SWORD.parseMaterial())
                .name(Utils.cc("&bKills"))
                .lore(Utils.cc("&f" + stats.getKills()))
                .build()
        );

        setItem(11, new ItemBuilder(XMaterial.DIAMOND_SWORD.parseMaterial())
                .name(Utils.cc("&eVoid Kills"))
                .lore(Utils.cc("&f" + stats.getVoidKills()))
                .build()
        );

        setItem(12, new ItemBuilder(XMaterial.BOW.parseMaterial())
                .name(Utils.cc("&bBow Kills"))
                .lore(Utils.cc("&f" + stats.getBowKills()))
                .build()
        );

        setItem(13, new ItemBuilder(XMaterial.SKELETON_SKULL.parseMaterial())
                .name(Utils.cc("&cDeaths"))
                .lore(Utils.cc("&f" + stats.getDeaths()))
                .build()
        );

        setItem(14, new ItemBuilder(XMaterial.EXPERIENCE_BOTTLE.parseMaterial())
                .name(Utils.cc("&bExperience"))
                .lore(Utils.cc("&f" + stats.getExp()))
                .build()
        );

    }
}
