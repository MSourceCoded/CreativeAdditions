package sourcecoded.creativeA.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import sourcecoded.creativeA.CreativeAdditionsBase;
import sourcecoded.creativeA.client.camera.CameraCommand;
import sourcecoded.creativeA.client.camera.Tween;

import java.io.*;

public class MsgPathFromServer implements IMessage, IMessageHandler<MsgPathFromServer, IMessage> {

    String name;

    public MsgPathFromServer() {}
    public MsgPathFromServer(String name) {
        this.name = name;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        name = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, name);
    }

    @Override
    public IMessage onMessage(MsgPathFromServer message, MessageContext ctx) {
        File save = new File(CameraCommand.ca, message.name + CameraCommand.fileExtension);

        try {
            BufferedReader reader = new BufferedReader(new FileReader(save));
            String nextLine;
            String jsonString = "";
            while ((nextLine = reader.readLine()) != null) {
                jsonString += nextLine;
            }

            Tween.setWaypoints(CameraCommand.loadPath(jsonString));

            reader.close();

            CreativeAdditionsBase.proxy.getClientPlayer().addChatMessage(new ChatComponentText("Loaded Camera Path from Network JSON: " + message.name));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
