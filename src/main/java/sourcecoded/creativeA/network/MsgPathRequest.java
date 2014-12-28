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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MsgPathRequest implements IMessage, IMessageHandler<MsgPathRequest, IMessage> {

    String name;

    public MsgPathRequest() {}
    public MsgPathRequest(String name) {
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
    public IMessage onMessage(MsgPathRequest message, MessageContext ctx) {
        NetHandler.wrapper.sendTo(new MsgPathFromServer(message.name), ctx.getServerHandler().playerEntity);

        return null;
    }
}
