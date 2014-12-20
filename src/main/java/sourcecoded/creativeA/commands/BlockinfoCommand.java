package sourcecoded.creativeA.commands;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import sourcecoded.creativeA.shared.Methods;

import java.util.Objects;

public class BlockinfoCommand extends CommandBase {

	@Override
	public String getName() {
		return "blockinfo";
	}

	@Override
	public String getCommandUsage(ICommandSender var1) {
		return "/blockinfo <x> <y> <z>";
	}

	@Override
	public void execute(ICommandSender sender, String[] args) throws CommandException {
		if (args.length != 3) {
			Methods.usage(sender, this);
		} else {
			BlockPos pos = func_175757_a(sender, args, 0, false);

			IBlockState block = sender.getEntityWorld().getBlockState(pos);
			TileEntity tile = null;
			
			NBTTagCompound nbt = new NBTTagCompound();
			
			if (sender.getEntityWorld().getTileEntity(pos) != null) {
				tile = sender.getEntityWorld().getTileEntity(pos);
				tile.writeToNBT(nbt);
				nbt.removeTag("x");
				nbt.removeTag("y");
				nbt.removeTag("z");
			} else {
				nbt.setString("id", Objects.toString(Block.blockRegistry.getNameForObject(block.getBlock())));
				nbt.setString("Data", block.getProperties().toString());			//TODO: Needs fixing
			}
			
			Methods.setClip(nbt.toString());
			
			Methods.addChatMessage((EntityPlayer) sender, nbt.toString());
			Methods.addChatMessage((EntityPlayer) sender, "NBT Copied to clipboard");
		}
	}

}
