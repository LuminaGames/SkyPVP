package lol.vedant.skypvp.commands.kit;

import lol.vedant.skypvp.SkyPVP;
import lol.vedant.skypvp.api.kit.Kit;
import lol.vedant.skypvp.menu.KitPreviewMenu;
import me.despical.commandframework.Command;
import me.despical.commandframework.CommandArguments;
import org.bukkit.entity.Player;


public class KitPreviewCommand {

    private final SkyPVP plugin;

    public KitPreviewCommand(SkyPVP plugin) {
        this.plugin = plugin;
    }

    @Command(
            name = "kit.preview",
            desc = "Preview a kit",
            senderType = Command.SenderType.PLAYER
    )
    public void execute(CommandArguments args) {
        String id = args.getArgument(0);
        Player player = args.getSender();
        player.sendMessage("Showing preview for " + id);
        Kit kit = plugin.getKitManager().getKitById(id);

        new KitPreviewMenu(kit).open(args.getSender());

    }

}
