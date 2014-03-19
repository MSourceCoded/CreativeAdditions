package sourcecoded.mods.creativeAdditions.common;

import sourcecoded.mods.creativeAdditions.event.PlayerEvents;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

@Mod(modid = CreativeAdditionsBase.MODID, version = CreativeAdditionsBase.VERSION, canBeDeactivated=true)
public class CreativeAdditionsBase
{
    public static final String MODID = "creativeadditions";
    public static final String VERSION = "1.1.1";
    
    @EventHandler
    public void preinit(FMLPreInitializationEvent event){
    	FMLCommonHandler.instance().bus().register(new PlayerEvents());
    	
    	Register.RegisterItems();
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event){
    }
    
    @EventHandler
    public void serverLoad(FMLServerStartingEvent event)
    {
        Register.RegisterCommands(event);
    }
}
