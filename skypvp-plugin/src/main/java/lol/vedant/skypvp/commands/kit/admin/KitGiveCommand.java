package lol.vedant.skypvp.commands.kit.admin;

import lol.vedant.skypvp.SkyPVP;
import lol.vedant.skypvp.api.utils.Utils;
import lol.vedant.skypvp.kit.KitManager;
import me.despical.commandframework.Command;
import me.despical.commandframework.CommandArguments;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class KitGiveCommand {

    @Command(
            name = "kit.give",
            aliases = {"kits.give"},
            permission = "skypvp.kits.admin",
            senderType = Command.SenderType.PLAYER
    )
    public void giveKit(CommandArguments args) {
        Player sender = args.getSender();

        if (args.getLength() < 1) {
            sender.sendMessage(Utils.cc("&cUsage: /kit give <kit> [player]"));
            return;
        }

        String kitName = args.getArgument(0);

        Player target = sender;
        if (args.getLength() >= 2) {
            target = Bukkit.getPlayerExact(args.getArgument(1));
            if (target == null) {
                sender.sendMessage(Utils.cc("&cPlayer not found."));
                return;
            }
        }
        KitManager manager = SkyPVP.getPlugin().getKitManager();

        manager.giveKit(sender, manager.getKitById(kitName));
        sender.sendMessage(Utils.cc("&aGiving kit '" + kitName + "' to " + target.getName() + "."));
    }
}
