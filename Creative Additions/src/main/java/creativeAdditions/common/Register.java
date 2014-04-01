package creativeAdditions.common;

import net.minecraft.item.Item;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import creativeAdditions.commands.BangCommand;
import creativeAdditions.commands.BlockinfoCommand;
import creativeAdditions.commands.CommandBlockCommand;
import creativeAdditions.commands.CoordCommand;
import creativeAdditions.commands.FSummonCommand;
import creativeAdditions.commands.FacingCommand;
import creativeAdditions.commands.GetTimeCommand;
import creativeAdditions.commands.HeadCommand;
import creativeAdditions.commands.KillEntitiesCommand;
import creativeAdditions.commands.PlatformerCommand;
import creativeAdditions.commands.RandomNCommand;
import creativeAdditions.commands.RelativeCommand;
import creativeAdditions.item.BlockHelperItem;
import creativeAdditions.item.RemoteItem;

public class Register {		
	public static final Item Remote = new RemoteItem();
	public static final Item BlockHelper = new BlockHelperItem();
	
	public static void RegisterItems() {
		GameRegistry.registerItem(Remote, "Remote");
		GameRegistry.registerItem(BlockHelper, "BlockHelper");
	}
	
	public static void RegisterCommands(FMLServerStartingEvent event) {
		event.registerServerCommand(new GetTimeCommand());
        event.registerServerCommand(new FSummonCommand());
        event.registerServerCommand(new KillEntitiesCommand());
        event.registerServerCommand(new PlatformerCommand());
        event.registerServerCommand(new CommandBlockCommand());
        event.registerServerCommand(new RandomNCommand());
        event.registerServerCommand(new CoordCommand());
        event.registerServerCommand(new RelativeCommand());
        event.registerServerCommand(new FacingCommand());
        event.registerServerCommand(new HeadCommand());
        event.registerServerCommand(new BangCommand());
        event.registerServerCommand(new BlockinfoCommand());
	}
}