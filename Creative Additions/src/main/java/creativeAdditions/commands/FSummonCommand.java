package creativeAdditions.commands;

import java.util.ArrayList;
import java.util.List;

import creativeAdditions.shared.Methods;
import creativeAdditions.shared.Summoner;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.EntityList;
import net.minecraft.world.World;

public class FSummonCommand extends CommandBase {
	
	@Override
	public String getCommandName() {
		return "fsummon";
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender) {
		return "/fsummon <entity> <count> <x> <y> <z> [data]";
	}

	@Override
	public void processCommand(ICommandSender icommandsender, String[] astring) {
		World world = Methods.playerWorld;
		 
		if (astring.length != 5 && astring.length != 6) {						//Confirms that all variables are in place
			Methods.usage(icommandsender, this);
		} else {
			ArrayList<String> args = new ArrayList<String>();

			int count = Integer.parseInt(astring[1]);
			
			args.add(astring[0]);
			args.add(astring[2]);
			args.add(astring[3]);
			args.add(astring[4]);
			
			if (astring.length == 5) {
				args.add("{}");		
			} else if (astring.length == 6) {
				args.add(astring[5]);
			}
			
			String[] pass = new String[args.size()];
			pass = args.toArray(pass);
			
			
			for (int i=0; i<count; i++) {
				Summoner.processSummon(icommandsender, pass, world);
			}
		}	
	}
	
	@SuppressWarnings("rawtypes")
	public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
    {
		return par2ArrayOfStr.length == 1 ? getListOfStringsMatchingLastWord(par2ArrayOfStr, this.listEnt()) : null;
    }

	@SuppressWarnings("unchecked")
	protected String[] listEnt()
    {
        return (String[])EntityList.func_151515_b().toArray(new String[0]);
    }
	
	@Override
	public int compareTo(Object o) {
		return 0;
	}
}
