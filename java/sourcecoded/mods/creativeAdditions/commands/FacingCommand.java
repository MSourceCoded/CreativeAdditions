package sourcecoded.mods.creativeAdditions.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import sourcecoded.mods.creativeAdditions.shared.Methods;

public class FacingCommand extends CommandBase {
	
	EntityPlayer player;
	int f;
	
	String[] facing = {"South", "South West", "West", "North West", "North", "North East", "East", "South East"};
	
	@Override
	public String getCommandName() {
		return "facing";
	}

	@Override
	public String getCommandUsage(ICommandSender var1) {
		return "/facing";
	}

	@Override
	public void processCommand(ICommandSender var1, String[] var2) {
		player = (EntityPlayer)var1;
		
		if (var2.length != 0) {
			Methods.usage(var1, this);
		} else {
			f = (int)player.rotationYaw;	//Grab the raw int of the players facing (between 0 and 360)
			
			if (f<0) {						//Makes sure we don't get a negative value
				f += 360;
			}
			
			f += 22;						//Offsets the value so it is centered					
			f %= 360;						//Makes sure we don't get an extra zone when doing a full circle
			
			f /= 45;						//Converts the int to 8 directions (0 - 7)
			notifyAdmins(player, "You are facing: " + facing[f]);	//Will print the direction according to the facing array
		}
	}

	@Override
	public int compareTo(Object arg0) {
		return 0;
	}

}
