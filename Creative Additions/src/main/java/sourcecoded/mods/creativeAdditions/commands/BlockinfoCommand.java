package sourcecoded.mods.creativeAdditions.commands;

import net.minecraft.block.Block;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import sourcecoded.mods.creativeAdditions.shared.Methods;

public class BlockinfoCommand extends CommandBase {

	@Override
	public String getCommandName() {
		return "blockinfo";
	}

	@Override
	public String getCommandUsage(ICommandSender var1) {
		return "/blockinfo <x> <y> <z>";
	}

	@Override
	public void processCommand(ICommandSender var1, String[] var2) {
		if (var2.length != 3) {
			Methods.usage(var1, this);
		} else {
			int x = (int)var1.getPlayerCoordinates().posX;
			int y = (int)var1.getPlayerCoordinates().posY;
			int z = (int)var1.getPlayerCoordinates().posZ;
			
			x = (int) CommandBase.func_110666_a(var1, (double)x, var2[0]);
			y = (int) CommandBase.func_110666_a(var1, (double)y, var2[1]);
			z = (int) CommandBase.func_110666_a(var1, (double)z, var2[2]);
			
			Block block = var1.getEntityWorld().getBlock(x, y, z);
			TileEntity tile = null;
			
			NBTTagCompound nbt = new NBTTagCompound();
			
			if (var1.getEntityWorld().getTileEntity(x, y, z) != null) {
				tile = var1.getEntityWorld().getTileEntity(x, y, z);				
				tile.writeToNBT(nbt);
				nbt.removeTag("x");
				nbt.removeTag("y");
				nbt.removeTag("z");
			} else {
				nbt.setString("id", Block.blockRegistry.getNameForObject(block));
				nbt.setInteger("Data", var1.getEntityWorld().getBlockMetadata(x, y, z));
			}
			
			Methods.setClip(nbt.toString());
			
			Methods.addChatMessage((EntityPlayer) var1, nbt.toString());
			Methods.addChatMessage((EntityPlayer) var1, "NBT Copied to clipboard");
		}
	}

}
