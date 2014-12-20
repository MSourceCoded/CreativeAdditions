package sourcecoded.creativeA.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import sourcecoded.creativeA.shared.Methods;

public class FacingCommand extends CommandBase {
	
	EntityPlayer player;
	int f;
	
	@Override
	public String getName() {
		return "facing";
	}

	@Override
	public String getCommandUsage(ICommandSender var1) {
		return "/facing";
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}

	@Override
	public void execute(ICommandSender sender, String[] args) throws CommandException {
		player = (EntityPlayer)sender;
		
		if (args.length != 0) {
			Methods.usage(sender, this);
		} else {
			Methods.addChatMessage(player, "You are facing: " + player.getHorizontalFacing().getName());
		}
	}

	@Override
	public int compareTo(Object arg0) {
		return 0;
	}

}
