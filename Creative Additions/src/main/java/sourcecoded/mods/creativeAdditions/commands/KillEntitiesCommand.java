package sourcecoded.mods.creativeAdditions.commands;

import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class KillEntitiesCommand extends CommandBase {

	int radius;
	
	Entity entity;
	@SuppressWarnings("rawtypes")
	List list;
	
	EntityPlayer player;
	World world;
	
	@Override
	public String getCommandName() {
		return "killEnt";
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender) {
		return "/killEnt";
	}

	@Override
	public void processCommand(ICommandSender icommandsender, String[] astring) {
		player = (EntityPlayer)icommandsender;
		world = player.getEntityWorld();
		
		radius = Integer.parseInt(astring[0]);
		
		list = world.getEntitiesWithinAABBExcludingEntity(player, AxisAlignedBB.getAABBPool().getAABB(player.posX - radius, player.posY - radius, player.posZ - radius, player.posX + radius, player.posY + radius, player.posZ + radius));

		for (int i=0; i<list.size(); i++) {
			entity = (Entity)list.get(i);
			entity.setDead();
		}
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
