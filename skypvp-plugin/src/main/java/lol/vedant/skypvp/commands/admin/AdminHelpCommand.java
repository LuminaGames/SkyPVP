package lol.vedant.skypvp.commands.admin;

import lol.vedant.skypvp.SkyPVP;
import lol.vedant.skypvp.api.config.Message;
import me.despical.commandframework.Command;
import me.despical.commandframework.CommandArguments;

public class AdminHelpCommand {

    @Command(
            name = "skypvp.admin",
            desc = "Admin commands for the SkyPVP plugin",
            permission = "skypvp.command.admin",
            senderType = Command.SenderType.BOTH
    )
    public void execute(CommandArguments args) {
        args.getSender().sendMessage(SkyPVP.messages.getString(Message.HELP_MESSAGE_ADMIN_1));
    }

}
