package sourcecoded.creativeA.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetHandler {

    public static SimpleNetworkWrapper wrapper;

    public static void init() {
        wrapper = NetworkRegistry.INSTANCE.newSimpleChannel("sc|creativeadditions");

        //Send to Server
        wrapper.registerMessage(MsgPathToServer.class, MsgPathToServer.class, 0, Side.SERVER);
        wrapper.registerMessage(MsgPathRequest.class, MsgPathRequest.class, 1, Side.SERVER);

        //Send to Client
        wrapper.registerMessage(MsgPathFromServer.class, MsgPathFromServer.class, 10, Side.CLIENT);
    }
}
