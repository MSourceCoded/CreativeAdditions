package sourcecoded.mods.creativeAdditions.common;

import net.minecraft.item.Item;
import sourcecoded.mods.creativeAdditions.commands.BangCommand;
import sourcecoded.mods.creativeAdditions.commands.BlockinfoCommand;
import sourcecoded.mods.creativeAdditions.commands.CommandBlockCommand;
import sourcecoded.mods.creativeAdditions.commands.CoordCommand;
import sourcecoded.mods.creativeAdditions.commands.FSummonCommand;
import sourcecoded.mods.creativeAdditions.commands.FacingCommand;
import sourcecoded.mods.creativeAdditions.commands.GetTimeCommand;
import sourcecoded.mods.creativeAdditions.commands.HeadCommand;
import sourcecoded.mods.creativeAdditions.commands.KillEntitiesCommand;
import sourcecoded.mods.creativeAdditions.commands.PlatformerCommand;
import sourcecoded.mods.creativeAdditions.commands.RandomNCommand;
import sourcecoded.mods.creativeAdditions.commands.RelativeCommand;
import sourcecoded.mods.creativeAdditions.item.BlockHelperItem;
import sourcecoded.mods.creativeAdditions.item.RemoteItem;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.registry.GameRegistry;

public class Register {		
	public static final Item Remote = new RemoteItem();
	public static final Item BlockHelper = new BlockHelperItem();
	
	public static void RegisterItems() {
		GameRegistry.registerItem(Remote, "Remote");
		GameRegistry.registerItem(BlockHelper, "BlockHelper");
	}
	
	protected static void RegisterCommands(FMLServerStartingEvent event) {
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