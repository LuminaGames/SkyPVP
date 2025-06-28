package lol.vedant.skypvp.commands.kit.admin;

import lol.vedant.skypvp.SkyPVP;
import lol.vedant.skypvp.api.kit.KitSerializer;
import lol.vedant.skypvp.api.utils.Utils;
import lol.vedant.skypvp.kit.KitManager;
import me.despical.commandframework.Command;
import me.despical.commandframework.CommandArguments;
import org.bukkit.entity.Player;

public class KitEditCommand {

    SkyPVP plugin = SkyPVP.getPlugin();

    @Command(
            name = "kit.edit",
            aliases = {"kits.edit"},
            senderType = Command.SenderType.PLAYER,
            permission = "skypvp.kits.admin"
    )
    public void editKit(CommandArguments args) {
        Player player = args.getSender();
        KitSerializer serializer = plugin.getKitSerializer();
        KitManager manager = plugin.getKitManager();

        if(args.getLength() <= 1) {
            player.sendMessage(Utils.cc("&cPlease specify ID for the kit to edit"));
            return;
        }

        String id = args.getArgument(0);

        if(manager.getKitById(id) == null) {
            player.sendMessage(Utils.cc("&cKit with this ID does not exists or is not loaded properly."));
            return;
        }

        serializer.saveKit(id, player);
        manager.load();

        player.sendMessage("&aYour inventory has been saved for kit: &f " + id);

    }

}
