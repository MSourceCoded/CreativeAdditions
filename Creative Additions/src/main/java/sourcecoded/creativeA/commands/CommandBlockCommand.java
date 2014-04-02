package sourcecoded.creativeA.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CommandBlockCommand extends CommandBase {

	@Override
	public String getCommandName() {
		return "cmd";
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender) {
		return "/cmd";
	}

	@Override
	public void processCommand(ICommandSender icommandsender, String[] astring) {
		EntityPlayer player = (EntityPlayer)icommandsender;
		Item cmd = getItemByText(player, "minecraft:command_block");
		ItemStack itemstack = new ItemStack(cmd);
		EntityItem entity = player.dropPlayerItemWithRandomChoice(itemstack, false);
		entity.delayBeforeCanPickup = 0;
	}

	@Override
	public int compareTo(Object o) {
		return 0;
	}

}
