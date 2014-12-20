package sourcecoded.creativeA;

import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.relauncher.FMLInjectionData;
import sourcecoded.creativeA.client.camera.TweenEvents;
import sourcecoded.creativeA.common.CommonProxy;
import sourcecoded.creativeA.common.Register;

import java.io.File;

@Mod(modid = CreativeAdditionsBase.MODID, version = CreativeAdditionsBase.VERSION)
public class CreativeAdditionsBase
{
    public static final String MODID = "creativeadditions";
    public static final String VERSION = "1.8-2.0.0";

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
    }
    
    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
        proxy.registerServerCommands(event);
    }

    public static String getForgeRoot() {
        return ((File) (FMLInjectionData.data()[6])).getAbsolutePath().replace(File.separatorChar, '/');
    }
}
