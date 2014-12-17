package sourcecoded.creativeA.commands;

import java.util.ArrayList;

import net.minecraft.command.CommandException;
import net.minecraft.command.WrongUsageException;
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
	public String getName() {
		return "randomN";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/randomN <minimum> <maximum> <iterator>";
	}

	@Override
	public int getRequiredPermissionLevel() {
        return 0;
    }

	@Override
	public void execute(ICommandSender sender, String[] args) throws CommandException {
		player = (EntityPlayer)sender;
		world = player.getEntityWorld();
		
		if (args.length != 3) {
			Methods.usage(sender, this);
		}else{
			gen.clear();
			
			int minimum = Integer.parseInt(args[0]);
			int maximum = Integer.parseInt(args[1]);
			int iterator = Integer.parseInt(args[2]);
			
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
