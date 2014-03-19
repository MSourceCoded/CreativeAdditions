package sourcecoded.mods.creativeAdditions.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class GetTimeCommand extends CommandBase {

	EntityPlayer player;
	World world;
	int time;
	int totalTime;
	int date;
	
	@Override
	public String getCommandName() {
		return "getTime";
	}
	
	@Override
	public String getCommandUsage(ICommandSender icommandsender) {
		return "/getTime";
	}
	 
	@Override
	public void processCommand(ICommandSender icommandsender, String[] astring) {
		
		player = ((EntityPlayer)icommandsender);
		world = player.getEntityWorld();
		
		if (astring.length != 0) {
			getCommandUsage(icommandsender);
		} else {
			
            WorldServer worldserver = MinecraftServer.getServer().worldServers[0];
            totalTime = (int)worldserver.getWorldTime();
            time = totalTime % 24000;
            date = totalTime / 24000;
                        
            notifyAdmins(player, " ");
            notifyAdmins(player, String.valueOf("Ticks " + time));
            notifyAdmins(player, String.valueOf("Seconds (real) " + (time / 20)));
            notifyAdmins(player, String.valueOf("Minutes (real) " + (time / 20 / 60)));
            notifyAdmins(player, String.valueOf("Days (game) " + date));
		}
	}

	@Override
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

}
