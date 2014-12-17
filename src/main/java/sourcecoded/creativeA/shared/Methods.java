package sourcecoded.creativeA.shared;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;

public class Methods {
	public static Clipboard userClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	
	public static MinecraftServer server = MinecraftServer.getServer();
	public static IChatComponent playerUsername = Minecraft.getMinecraft().thePlayer.getDisplayName();

	//Will throw a usage exception. ARGS: CommandSender, Command (this)
	public static void usage(ICommandSender icommandsender, ICommand command) throws WrongUsageException {
		throw new WrongUsageException(command.getCommandUsage(icommandsender));
	}
	
	//Will add a string to the user's ClipBoard. ARGS: toClip
	public static void setClip(String string) {
		StringSelection clip = new StringSelection(String.valueOf(string));
		userClipboard.setContents(clip, clip);
	}
	
	//Send a chat message to the player
	public static void addChatMessage(EntityPlayer player, String input) {
		player.addChatMessage(new ChatComponentText(input));
	}
	
	//Convert an array of strings to an array of integers
	public static int[] convertStringInt(String[] str) {
		int[] returned = new int[str.length];
		for (int i = 0; i<str.length; i++) {
			returned[i] = Integer.parseInt(str[i]);
		}
		return returned;
	}
}

