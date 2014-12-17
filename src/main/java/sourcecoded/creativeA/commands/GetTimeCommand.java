package sourcecoded.creativeA.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import sourcecoded.creativeA.shared.Methods;

public class GetTimeCommand extends CommandBase {

	EntityPlayer player;
	World world;
	int time;
	int totalTime;
	int date;

	@Override
	public String getName() {
		return "getTime";
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender) {
		return "/getTime";
	}

	@Override
	public void execute(ICommandSender sender, String[] args) throws CommandException {
		player = ((EntityPlayer)sender);
		world = player.getEntityWorld();
		
		if (args.length != 0) {
			getCommandUsage(sender);
		} else {
            WorldServer worldserver = MinecraftServer.getServer().worldServers[0];
            totalTime = (int)worldserver.getWorldTime();
            time = totalTime % 24000;
            date = totalTime / 24000;
                        
            Methods.addChatMessage(player, " ");
            Methods.addChatMessage(player, String.valueOf("Ticks " + time));
            Methods.addChatMessage(player, String.valueOf("Seconds (real) " + (time / 20)));
            Methods.addChatMessage(player, String.valueOf("Minutes (real) " + (time / 20 / 60)));
            Methods.addChatMessage(player, String.valueOf("Days (game) " + date));
		}
	}

	@Override
	public int compareTo(Object arg0) {
		return 0;
	}

}
