package lol.vedant.skypvp.commands.admin.setup;

import lol.vedant.skypvp.api.utils.Utils;
import me.despical.commandframework.Command;
import me.despical.commandframework.CommandArguments;
import org.bukkit.command.CommandSender;

public class SetupCommand {

    @Command(
            name = "skypvp.setup",
            desc = "Get help related to setup for skypvp plugin",
            permission = "skypvp.commands.admin"
    )
    public void execute(CommandArguments args) {
        CommandSender sender = args.getSender();
        sender.sendMessage(Utils.cc("&6&lSkyPVP &7Setup Guide"));
        sender.sendMessage("");
        sender.sendMessage(Utils.cc("&6/skypvp setspawn &7- &fSets the spawn of the arena"));
        sender.sendMessage(Utils.cc("&6/skypvp spawnarea &7- &fSelect the spawn area using a stick."));
    }

}
