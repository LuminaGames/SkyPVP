package lol.vedant.skypvp.commands.admin.setup;

import lol.vedant.skypvp.SkyPVP;
import lol.vedant.skypvp.api.kit.KitSerializer;
import me.despical.commandframework.Command;
import me.despical.commandframework.CommandArguments;

public class TestKitCmd {

    @Command(
            name = "kitcreate",
            desc = "Serialize test",
            senderType = Command.SenderType.PLAYER
    )

    public void execute(CommandArguments args) {
        KitSerializer serializer = new KitSerializer(SkyPVP.getPlugin());
        serializer.saveKit("randomKit", args.getSender());
    }

}
