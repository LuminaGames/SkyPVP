package lol.vedant.skypvp.commands.kit.admin;

import lol.vedant.skypvp.SkyPVP;
import lol.vedant.skypvp.api.kit.KitSerializer;
import lol.vedant.skypvp.api.utils.Utils;
import lol.vedant.skypvp.kit.KitManager;
import me.despical.commandframework.Command;
import me.despical.commandframework.CommandArguments;
import org.bukkit.entity.Player;

public class KitCreateCommand {

    SkyPVP plugin = SkyPVP.getPlugin();

    @Command(
            name = "kit.create",
            desc = "Create custom kits for your server",
            senderType = Command.SenderType.PLAYER,
            permission = "skypvp.kits.admin"
    )
    public void execute(CommandArguments args) {
        KitSerializer serializer = plugin.getKitSerializer();
        KitManager manager = plugin.getKitManager();
        Player player = args.getSender();

        if(args.getLength() <= 1) {
            player.sendMessage(Utils.cc("&cPlease specify a ID for the kit to create"));
            return;
        }

        String id = args.getArgument(0);

        if(manager.getKitById(id) != null) {
            player.sendMessage(Utils.cc("&cKit with this ID already exists. Please try again with different ID."));
            return;
        }

        //Save kit to the file
        serializer.saveKit(id, player);
        manager.load();

        player.sendMessage(Utils.cc("&aCreated kit with ID: " + id));
        player.sendMessage(Utils.cc("&eYou can set the kit icon with /kit icon <id>"));
        player.sendMessage(Utils.cc("&eYou can set the kit price with /kit price <id> <price>"));
    }

}
