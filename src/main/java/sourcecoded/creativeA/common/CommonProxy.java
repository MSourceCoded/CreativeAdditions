package sourcecoded.creativeA.common;

import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import sourcecoded.creativeA.commands.*;

public class CommonProxy {

    public void registerRenderers() {
    }

    public void registerServerCommands(FMLServerStartingEvent event) {
        event.registerServerCommand(new GetTimeCommand());
        event.registerServerCommand(new CommandBlockCommand());
        event.registerServerCommand(new CoordCommand());
        event.registerServerCommand(new RelativeCommand());
        event.registerServerCommand(new HeadCommand());
        event.registerServerCommand(new BangCommand());
        event.registerServerCommand(new BlockinfoCommand());
    }

    public void registerClientCommands() {}
}