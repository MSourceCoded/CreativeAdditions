package sourcecoded.creativeA.network;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import sourcecoded.creativeA.CreativeAdditionsBase;
import sourcecoded.creativeA.client.camera.CameraCommand;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MsgPathToServer implements IMessage, IMessageHandler<MsgPathToServer, IMessage> {

    String path;
    String name;

    public MsgPathToServer() {}
    public MsgPathToServer(String JSONPath, String name) {
        this.path = JSONPath;
        this.name = name;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        path = ByteBufUtils.readUTF8String(buf);
        name = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, path);
        ByteBufUtils.writeUTF8String(buf, name);
    }

    @Override
    public IMessage onMessage(MsgPathToServer message, MessageContext ctx) {
        CameraCommand.ca.mkdir();
        File save = new File(CameraCommand.ca, message.name + CameraCommand.fileExtension);

        try {
            FileWriter writer = new FileWriter(save);
            writer.write(message.path);
            writer.close();

            CreativeAdditionsBase.proxy.sendServerMessage(ctx.getServerHandler().playerEntity.getDisplayNameString() + " just uploaded a Camera Path: " + message.name + ", type '/cam net load " + message.name + "' to load it!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
