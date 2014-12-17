package sourcecoded.creativeA.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CommandBlockCommand extends CommandBase {

	@Override
	public String getName() {
		return "cmd";
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender) {
		return "/cmd";
	}

	@Override
	public void execute(ICommandSender sender, String[] args) throws CommandException {
		EntityPlayer player = (EntityPlayer)sender;
		Item cmd = getItemByText(player, "minecraft:command_block");
		ItemStack itemstack = new ItemStack(cmd);
		EntityItem entity = player.dropPlayerItemWithRandomChoice(itemstack, false);
		entity.setNoPickupDelay();
	}

	@Override
	public int compareTo(Object o) {
		return 0;
	}

}
