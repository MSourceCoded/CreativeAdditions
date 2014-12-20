package sourcecoded.creativeA.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import sourcecoded.creativeA.shared.Methods;

import java.util.List;

public class HeadCommand extends CommandBase {

	@Override
	public String getName() {
		return "head";
	}

	@Override
	public String getCommandUsage(ICommandSender var1) {
		return "/head <username>";
	}

	@Override
	public void execute(ICommandSender var1, String[] var2) throws CommandException {
		if (var2.length != 1) {
			Methods.usage(var1, this);
		} else {
			EntityPlayer player = (EntityPlayer)var1;
			ItemStack head = new ItemStack(Items.skull, 1, 3);

			head.setTagCompound(new NBTTagCompound());
			head.getTagCompound().setString("SkullOwner", var2[0]);
			
			player.inventory.addItemStackToInventory(head);
		}
	}

	@Override
	public int compareTo(Object arg0) {
		return 0;
	}

	protected String[] getPlayers()
    {
        return MinecraftServer.getServer().getAllUsernames();
    }
	
	@SuppressWarnings("rawtypes")
	public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
    {
		return par2ArrayOfStr.length == 1 ? getListOfStringsMatchingLastWord(par2ArrayOfStr, this.getPlayers()) : null;
    }

	
}
