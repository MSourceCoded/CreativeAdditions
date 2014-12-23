package sourcecoded.creativeA.common;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import sourcecoded.creativeA.item.BlockHelperItem;
import sourcecoded.creativeA.item.RemoteItem;

public class Register {		
	public static final Item Remote = new RemoteItem();
	public static final Item BlockHelper = new BlockHelperItem();
	
	public static void RegisterItems() {
		GameRegistry.registerItem(Remote, "remote");
		//GameRegistry.registerItem(BlockHelper, "BlockHelper");
	}
	
}