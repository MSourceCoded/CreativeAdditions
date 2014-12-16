package sourcecoded.creativeA.commands;

import java.util.ArrayList;

import sourcecoded.creativeA.shared.Methods;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class RandomNCommand extends CommandBase {

	EntityPlayer player;
	World world;
	
	ArrayList<Integer> gen = new ArrayList<Integer>();
	
	private int random(int min, int max) {
		int result = (int) (min + Math.floor(Math.random() * ((max+1) - min)));
		return result;
	}

	@Override
	public String getCommandName() {
		return "randomN";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/randomN <minimum> <maximum> <iterator>";
	}

	@Override
	public int getRequiredPermissionLevel()
    {
        return 0;
    }

	@Override
	public void processCommand(ICommandSender sender, String[] astring) {
		player = (EntityPlayer)sender;
		world = player.getEntityWorld();
		
		if (astring.length != 3) {
			Methods.usage(sender, this);
		}else{
			gen.clear();
			
			int minimum = Integer.parseInt(astring[0]);
			int maximum = Integer.parseInt(astring[1]);
			int iterator = Integer.parseInt(astring[2]);
			
			for (int i=0;i<iterator;i++) {
				gen.add(random(minimum, maximum));
			}
			Methods.addChatMessage(player, "Random: " + String.valueOf(gen));
			Methods.addChatMessage(player, "");
		}
	}

	@Override
	public int compareTo(Object arg0) {
		return 0;
	}

}
