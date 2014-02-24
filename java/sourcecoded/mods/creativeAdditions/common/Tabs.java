package sourcecoded.mods.creativeAdditions.common;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class Tabs {
	
	public static CreativeTabs creativeAdditions = new CreativeTabs("creativeAdditions") {
		@Override
		public Item getTabIconItem() {
			return Register.Remote;
		}
	};
}
