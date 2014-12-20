package sourcecoded.creativeA.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import sourcecoded.creativeA.shared.Methods;

public class CoordCommand extends CommandBase {

	double[] store = {0,0,0};
	String storeString;
	
	EntityPlayer player;
	
	public void doCommands(String separator) {
		store[0] = Math.floor(player.posX) + 0.5;
		store[1] = Math.floor(player.posY);
		store[2] = Math.floor(player.posZ) + 0.5;

		storeString = String.valueOf(store[0]) + separator + String.valueOf(store[1]) + separator + String.valueOf(store[2] + separator);
		
		notify(separator);
	}
	
	public void notify(String separator) {
		Methods.setClip(storeString);
		Methods.addChatMessage(player, "Current Position: " + store[0] + " " + store[1] + " " + store[2]);
		Methods.addChatMessage(player, "Positions copied to the clipboard with the separator \" " + separator + "\"");
	}

	@Override
	public String getName() {
		return "coords";
	}

	@Override
	public String getCommandUsage(ICommandSender var1) {
		return "/coords [separator]";
	}

	@Override
	public void execute(ICommandSender var1, String[] var2) throws CommandException {
		player = (EntityPlayer)var1;
		if (var2.length != 1 && var2.length != 0) {
			Methods.usage(var1, this);
		} else if (var2.length == 0) {
			doCommands(" ");
		} else {
			String separator = var2[0] + " ";
			doCommands(separator);
		}
	}

	@Override
	public int compareTo(Object arg0) {
		return 0;
	}

}
