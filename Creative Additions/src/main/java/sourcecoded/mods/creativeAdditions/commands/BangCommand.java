package sourcecoded.mods.creativeAdditions.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import sourcecoded.mods.creativeAdditions.shared.Methods;

public class BangCommand extends CommandBase {

	@Override
	public String getCommandName() {
		return "bang";
	}

	@Override
	public String getCommandUsage(ICommandSender var1) {
		return "/bang <x> <y> <z> <power>";
	}

	@Override
	public void processCommand(ICommandSender var1, String[] var2) {
		if (var2.length != 4) {
			Methods.usage(var1, this);
		} else {
			World world = Methods.playerWorld;
			EntityPlayer player = Methods.playerEntity;
			
			double x = func_110666_a(var1, player.posX, var2[0]);
			double y = func_110666_a(var1, player.posY, var2[1]);
			double z = func_110666_a(var1, player.posZ, var2[2]);
			
			float power = Float.parseFloat(var2[3]);
			
			world.createExplosion(player, x, y, z, power, true);
		}
	}

	@Override
	public int compareTo(Object o) {
		return 0;
	}
	
}
