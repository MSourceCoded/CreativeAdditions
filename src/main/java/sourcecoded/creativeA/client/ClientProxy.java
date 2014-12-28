package sourcecoded.creativeA.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import sourcecoded.creativeA.client.camera.CameraCommand;
import sourcecoded.creativeA.client.camera.renderer.TweenLocationRenderer;
import sourcecoded.creativeA.commands.FacingCommand;
import sourcecoded.creativeA.commands.RandomNCommand;
import sourcecoded.creativeA.common.CommonProxy;
import sourcecoded.creativeA.common.Register;

public class ClientProxy extends CommonProxy {

    @Override
    public void registerRenderers() {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Register.Remote, 0, new ModelResourceLocation("creativeadditions:remote", "inventory"));
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Register.BlockHelper, 0, new ModelResourceLocation("creativeadditions:setBlock", "inventory"));
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Register.BlockHelper, 1, new ModelResourceLocation("creativeadditions:fallingSand", "inventory"));
        ModelBakery.addVariantName(Register.BlockHelper, "creativeadditions:setBlock", "creativeadditions:fallingSand");
    }

    public EntityPlayerSP getClientPlayer() {
        return Minecraft.getMinecraft().thePlayer;
    }

    @Override
    public void registerClientCommands() {
        ClientCommandHandler.instance.registerCommand(new RandomNCommand());
        ClientCommandHandler.instance.registerCommand(new FacingCommand());

        ClientCommandHandler.instance.registerCommand(new CameraCommand());
    }

    @Override
    public void registerMisc() {
        MinecraftForge.EVENT_BUS.register(new TweenLocationRenderer());
        FMLCommonHandler.instance().bus().register(new TweenLocationRenderer());
    }

}