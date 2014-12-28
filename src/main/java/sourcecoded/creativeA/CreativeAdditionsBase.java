package sourcecoded.creativeA;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.relauncher.FMLInjectionData;
import sourcecoded.creativeA.common.CommonProxy;
import sourcecoded.creativeA.common.Register;
import sourcecoded.creativeA.network.NetHandler;

import java.io.File;

@Mod(modid = CreativeAdditionsBase.MODID, version = CreativeAdditionsBase.VERSION)
public class CreativeAdditionsBase
{
    public static final String MODID = "creativeadditions";
    public static final String VERSION = "@VERSION@";

    @SidedProxy(clientSide = "sourcecoded.creativeA.client.ClientProxy", serverSide = "sourcecoded.creativeA.common.CommonProxy")
    public static CommonProxy proxy;
    
    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event){
    	Register.RegisterItems();
    }
    
    @Mod.EventHandler
    public void init(FMLInitializationEvent event){
        proxy.registerClientCommands();
        proxy.registerRenderers();
        proxy.registerMisc();

        NetHandler.init();
    }
    
    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
        proxy.registerServerCommands(event);
    }

    public static String getForgeRoot() {
        return ((File) (FMLInjectionData.data()[6])).getAbsolutePath().replace(File.separatorChar, '/');
    }
}
