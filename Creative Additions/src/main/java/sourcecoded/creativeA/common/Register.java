package sourcecoded.creativeA.common;

import sourcecoded.creativeA.commands.BangCommand;
import sourcecoded.creativeA.commands.BlockinfoCommand;
import sourcecoded.creativeA.commands.CommandBlockCommand;
import sourcecoded.creativeA.commands.CoordCommand;
import sourcecoded.creativeA.commands.FSummonCommand;
import sourcecoded.creativeA.commands.FacingCommand;
import sourcecoded.creativeA.commands.GetTimeCommand;
import sourcecoded.creativeA.commands.HeadCommand;
import sourcecoded.creativeA.commands.KillEntitiesCommand;
import sourcecoded.creativeA.commands.PlatformerCommand;
import sourcecoded.creativeA.commands.RandomNCommand;
import sourcecoded.creativeA.commands.RelativeCommand;
import sourcecoded.creativeA.item.BlockHelperItem;
import sourcecoded.creativeA.item.RemoteItem;
import net.minecraft.item.Item;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.registry.GameRegistry;

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