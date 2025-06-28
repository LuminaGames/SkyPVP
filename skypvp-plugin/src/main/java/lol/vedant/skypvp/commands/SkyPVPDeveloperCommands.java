package lol.vedant.skypvp.commands;

import lol.vedant.skypvp.SkyPVP;
import lol.vedant.skypvp.api.kit.Kit;
import lol.vedant.skypvp.api.perks.Perk;
import lol.vedant.skypvp.kit.KitManager;
import lol.vedant.skypvp.perks.PerkManager;
import me.despical.commandframework.Command;
import me.despical.commandframework.CommandArguments;
import org.bukkit.entity.Player;

public class SkyPVPDeveloperCommands {
    /*
    Commands used for debugging purposes for the plugins.
    May help server admins for debugging issues with the plugin
     */

    KitManager manager = SkyPVP.getPlugin().getKitManager();
    PerkManager pManager = SkyPVP.getPlugin().getPerkManager();

    @Command(
            name = "skypvp.dumploadedkits",
            desc = "Dump loaded kits",
            senderType = Command.SenderType.BOTH,
            permission = "skypvp.kits.admin"
    )
    public void dumpLoadedKits(CommandArguments args) {
        Player player = args.getSender();

        for (Kit kit : manager.getLoadedKits().values()) {
            player.sendMessage("");
            player.sendMessage("ID: " + kit.getId());
            player.sendMessage("DisplayName: " + kit.getDisplayName());
            player.sendMessage("Price: " + kit.getPrice());
            player.sendMessage("Custom Data: " + kit.getOptions().toString());
            player.sendMessage("");
        }
    }

    @Command(
            name = "skypvp.dumploadedperks",
            desc = "Dump loaded perks",
            senderType = Command.SenderType.BOTH,
            permission = "skypvp.kits.admin"
    )
    public void dumPerks(CommandArguments args) {
        Player player = args.getSender();

        for (Perk perk : pManager.getPerks().values()) {
            player.sendMessage("");
            player.sendMessage("DisplayName: " + perk.getName());
            player.sendMessage("Type: " + perk.getType());
            player.sendMessage("Price: " + perk.getPrice());
            player.sendMessage("Display Item: " + perk.getDisplayItem().getType());
            player.sendMessage("");
        }
    }

}
