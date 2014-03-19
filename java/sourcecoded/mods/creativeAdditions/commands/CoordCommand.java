package sourcecoded.mods.creativeAdditions.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import sourcecoded.mods.creativeAdditions.shared.Methods;

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
		notifyAdmins(player, "Current Position: " + store[0] + " " + store[1] + " " + store[2]);
		notifyAdmins(player, "Positions copied to the clipboard with the separator \" " + separator + "\"");
	}
	
	@Override
	public String getCommandName() {
		return "coords";
	}

	@Override
	public String getCommandUsage(ICommandSender var1) {
		return "/coords [separator]";
	}
	
	@Override
	public void processCommand(ICommandSender var1, String[] var2) {
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
