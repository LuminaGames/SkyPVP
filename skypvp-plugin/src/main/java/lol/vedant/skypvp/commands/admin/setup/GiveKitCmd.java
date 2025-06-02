package lol.vedant.skypvp.commands.admin.setup;

import lol.vedant.skypvp.SkyPVP;
import lol.vedant.skypvp.api.kit.Kit;
import lol.vedant.skypvp.api.kit.KitSerializer;
import me.despical.commandframework.Command;
import me.despical.commandframework.CommandArguments;
import org.bukkit.entity.Player;

public class GiveKitCmd {

    @Command(
            name = "givekit",
            desc = "Gives the test kit to the player",
            senderType = Command.SenderType.PLAYER
    )
    public void execute(CommandArguments args) {
        KitSerializer serializer = new KitSerializer(SkyPVP.getPlugin());
        Player player = args.getSender();
        Kit testKit = serializer.loadKit("randomKit");
        SkyPVP.getPlugin().getKitManager().giveKit(player, testKit);
        player.sendMessage("You have been given the test kit");
    }

}
