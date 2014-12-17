package sourcecoded.creativeA.commands;

import java.util.List;

import net.minecraft.command.CommandException;
import net.minecraft.command.WrongUsageException;
import sourcecoded.creativeA.shared.Methods;
import sourcecoded.creativeA.shared.RelativesAPI;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;

public class RelativeCommand extends CommandBase {

	EntityPlayer player;
	
	public void relative(String separator, String[] args) throws WrongUsageException {
		
		double x = player.posX;
		double y = player.posY;
		double z = player.posZ;	
		
		if (args[0].equals("set")) {
			
			RelativesAPI.setRel(x, y, z);
			String initCo = (int)x + separator + (int)y + separator + (int)z;
			Methods.addChatMessage(player, "Initial coords set to: " + initCo);
			
		} else if (args[0].equals("get")) {
			
			int[] relatives = RelativesAPI.getRel(x, y, z);
			String rel = "~" + relatives[0] + separator + "~" + relatives[1] + separator + "~" + relatives[2] + separator;
			
			Methods.addChatMessage(player, "Relative Coordinates: " + rel);
			Methods.addChatMessage(player, "Coords copied to clipboard with the separator \"" + separator + "\"");
			
			Methods.setClip(rel);
			
		} else {
			Methods.usage(player, this);
		}
	}

	@Override
	public String getName() {
		return "relative";
	}

	@Override
	public String getCommandUsage(ICommandSender var1) {
		return "/relative <set|get> [separator]";
	}

	@Override
	public void execute(ICommandSender var1, String[] var2) throws CommandException {
		player = (EntityPlayer)var1;
		if (var2.length == 1) {		
			relative(" ", var2);
		} else if (var2.length == 2) {
			String separator = var2[1] + " ";
			relative(separator, var2);
		} else {
			Methods.usage(var1, this);
		}
		
	}

	@Override
	public int compareTo(Object o) {
		return 0;
	}
	
	@SuppressWarnings("rawtypes")
	public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
    {
        return par2ArrayOfStr.length == 1 ? getListOfStringsMatchingLastWord(par2ArrayOfStr, new String[] {"set", "get"}): null;
    }

}
