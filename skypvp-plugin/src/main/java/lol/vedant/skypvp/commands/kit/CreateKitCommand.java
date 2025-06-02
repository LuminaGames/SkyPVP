package lol.vedant.skypvp.commands.kit;

import lol.vedant.skypvp.SkyPVP;
import lol.vedant.skypvp.api.kit.KitSerializer;
import lol.vedant.skypvp.api.utils.Utils;
import me.despical.commandframework.Command;
import me.despical.commandframework.CommandArguments;

public class CreateKitCommand {

    SkyPVP plugin = SkyPVP.getPlugin();

    @Command(
            name = "kit.create",
            desc = "Create kits for your server",
            senderType = Command.SenderType.PLAYER
    )
    public void execute(CommandArguments args) {

        KitSerializer kitSerializer = new KitSerializer(plugin);
        String kitId = args.getArgument(0);
        //if(args.getLength() < 2) {
        //    args.getSender().sendMessage("No args provided");
        //    return;
        //}

        if(plugin.getKitManager().getLoadedKits().containsKey(kitId)) {
            args.getSender().sendMessage("Kit already exists");
            args.getSender().sendMessage(kitId);
            return;

        }

        //Create save the player's inventory and armor and reload the kits
        kitSerializer.saveKit(kitId, args.getSender());

        //Reload all kits so new kit gets loaded
        plugin.getKitManager().load();
        args.getSender().sendMessage(Utils.cc("&aKit created successfully: " + kitId));

    }

}
