package sourcecoded.creativeA.commands;

import java.util.List;

import sourcecoded.creativeA.shared.Methods;
import net.minecraft.block.Block;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * 
 * Keep in mind the majority of the commented code is for the 1.6.4 code.
 *
 */

public class PlatformerCommand extends CommandBase {

	EntityPlayer player;
	World world;
	
	int x;
	int y;
	int z;
	
	int blockID;
	int radius;
	
	String direction;
	
	@Override
	public String getCommandName() {
		return "platformer";
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender) {
		return "/platformer <x> <y> <z> <block> <radius> <direction>";
	}

	@Override
	public void processCommand(ICommandSender icommandsender, String[] astring) {
		
		if (astring.length != 6) {
			Methods.usage(icommandsender, this);
		} else {
		
			player = (EntityPlayer)icommandsender;
			world = player.getEntityWorld();
			
			Block block = CommandBase.getBlockByText(icommandsender, astring[3]);
			
			radius = Integer.parseInt(astring[4]);
			
			direction = astring[5].toLowerCase();
			
			if (astring[0].contains("~") && (astring[0].length() >= 2)) {				//This is just to check for relative co-ord input
				x = (int)player.posX + Integer.parseInt((astring[0].substring(1))); 
			} else if (astring[0].contains("~")) {
				x = (int)player.posX;
			} else {
				x = Integer.parseInt(astring[0]);				
			}
			
			if (astring[1].contains("~") && (astring[1].length() >= 2)) {
				y = (int)player.posY + Integer.parseInt((astring[1].substring(1))); 
			} else if (astring[1].contains("~")) {
				y = (int)player.posY;
			} else {
				y = Integer.parseInt(astring[1]);				
			}
			
			if (astring[2].contains("~") && (astring[2].length() >= 2)) {
				z = (int)player.posZ + Integer.parseInt((astring[2].substring(1))); 
			} else if (astring[2].contains("~")) {
				z = (int)player.posZ;
			} else {
				z = Integer.parseInt(astring[2]);				
			}
			
			if (direction.equals("center")) {
				for (int i=0; i<=radius*2; i++) {
					for (int j=0; j<=radius*2; j++) {
						//int hRad = (radius-1);
						//world.setBlock(hRad+x-j, y, hRad+z-i+1, blockID);
						world.setBlock(radius+x-j, y, radius+z-i, block);
					}
				}
			} else if (direction.equals("north")) {
				for (int i=0; i<=radius*2; i++) {
					for (int j=0; j<=radius*2; j++) {
						//int hRad = (radius-1);
						//world.setBlock(hRad+x-j, y, z-i, blockID);
						world.setBlock(radius+x-j, y, z-i, block);
					}
				}
			} else if (direction.equals("south")) {
				for (int i=0; i<=radius*2; i++) {
					for (int j=0; j<=radius*2; j++) {
						//int hRad = (radius-1);
						//world.setBlock(hRad+x-j, y, z+i, blockID);
						world.setBlock(radius+x-j, y, z+i, block);
					}
				}
			} else if (direction.equals("east")) {
				for (int i=0; i<=radius*2; i++) {
					for (int j=0; j<=radius*2; j++) {
						//int hRad = (radius);
						//world.setBlock(x+j-1, y, hRad+z-i, blockID);
						world.setBlock(x+j, y, radius+z-i, block);
					}
				}
			} else if (direction.equals("west")) {
				for (int i=0; i<=radius*2; i++) {
					for (int j=0; j<=radius*2; j++) {
						//int hRad = (radius);
						//world.setBlock(x-j-1, y, hRad+z-i, blockID);
						world.setBlock(x-j, y, radius+z-i, block);
					}
				}
			} else {
				Methods.usage(icommandsender, this);
			}
		
		}
	}
	
	@SuppressWarnings("rawtypes")
	public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
    {
        return par2ArrayOfStr.length == 6 ? getListOfStringsMatchingLastWord(par2ArrayOfStr, new String[] {"center", "north", "south", "east", "west"}): par2ArrayOfStr.length == 4 ? getListOfStringsFromIterableMatchingLastWord(par2ArrayOfStr, Block.blockRegistry.getKeys()): null;
    }

	@Override
	public int compareTo(Object o) {
		return 0;
	}

}
