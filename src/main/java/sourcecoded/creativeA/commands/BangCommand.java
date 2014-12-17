package sourcecoded.creativeA.commands;

import net.minecraft.block.BlockPackedIce;
import net.minecraft.command.CommandException;
import net.minecraft.util.BlockPos;
import sourcecoded.creativeA.shared.Methods;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class BangCommand extends CommandBase {

	@Override
	public String getName() {
		return "bang";
	}

	@Override
	public String getCommandUsage(ICommandSender var1) {
		return "/bang <x> <y> <z> <power>";
	}

	@Override
	public void execute(ICommandSender var1, String[] var2) throws CommandException {
		if (var2.length != 4) {
			Methods.usage(var1, this);
		} else {
			World world = var1.getEntityWorld();
			EntityPlayer player = (EntityPlayer) var1;

			BlockPos pos = func_175757_a(var1, var2, 0, false);

			double x = pos.getX();
			double y = pos.getY();
			double z = pos.getZ();
			
			float power = Float.parseFloat(var2[3]);
			
			world.createExplosion(player, x, y, z, power, true);
		}
	}

	@Override
	public int compareTo(Object o) {
		return 0;
	}
	
}
