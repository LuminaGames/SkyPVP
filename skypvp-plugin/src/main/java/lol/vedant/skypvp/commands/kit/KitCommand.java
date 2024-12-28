package lol.vedant.skypvp.commands.kit;

import lol.vedant.skypvp.SkyPVP;
import lol.vedant.skypvp.menu.KitsMenu;
import me.despical.commandframework.Command;
import me.despical.commandframework.CommandArguments;
import org.bukkit.entity.Player;

public class KitCommand {

    private SkyPVP plugin;

    public KitCommand(SkyPVP plugin) {
        this.plugin = plugin;
    }

    @Command(
            name = "kit",
            desc = "Buy or Preview kits in the SkyPvP gamemode",
            senderType = Command.SenderType.PLAYER
    )
    public void execute(CommandArguments args) {
        Player player = args.getSender();

        new KitsMenu(plugin.getKitManager().getLoadedKits(), 1).open(player);
    }

}
