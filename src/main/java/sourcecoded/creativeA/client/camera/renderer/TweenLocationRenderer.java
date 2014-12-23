package sourcecoded.creativeA.client.camera.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.opengl.GL11;
import sourcecoded.creativeA.client.camera.Tween;
import sourcecoded.creativeA.client.camera.TweenPosition;

import java.util.List;

public class TweenLocationRenderer {

    RenderManager rm;
    FontRenderer fr;
    EntityPlayer oldplayer;
    boolean rendered;

    @SubscribeEvent
    public void render(RenderWorldLastEvent event) {
        if (rm == null)
            rm = Minecraft.getMinecraft().getRenderManager();
        if (fr == null)
            fr = rm.getFontRenderer();

        List<TweenPosition> waypoints = Tween.getWaypoints();
        if (waypoints != null && !Tween.running()) {
            for (int i = 0; i < waypoints.size(); i++) {
                TweenPosition pos = waypoints.get(i);
                renderTag("Cam: " + i, pos.getX(), pos.getY(), pos.getZ(), Minecraft.getMinecraft().thePlayer, event.partialTicks);
            }
        }
    }

    @SubscribeEvent
    public void renderGUI(TickEvent.RenderTickEvent event) {
        if (Tween.running() && event.phase == TickEvent.Phase.START) {
            TweenPosition position = Tween.getCurrentPosition();
            if (position != null) {
                renderFake(position, event);
            }
        } else if (event.phase == TickEvent.Phase.END && rendered) {
            renderFakePost();
        }
    }

    public void renderFakePost() {
        Minecraft mc = Minecraft.getMinecraft();
        mc.setRenderViewEntity(oldplayer);
        rendered = false;
    }

    public void renderFake(TweenPosition position, TickEvent.RenderTickEvent event) {
        Minecraft mc = Minecraft.getMinecraft();

        this.oldplayer = mc.thePlayer;

        EntityPlayer player = Tween.getView();
        player.posX = position.getX();
        player.posY = position.getY();
        player.posZ = position.getZ();

        player.rotationYaw = (float) position.getRotH();
        player.rotationPitch = (float) position.getRotV();

        player.cameraYaw = (float) position.getCamH();
        player.cameraPitch = (float) position.getCamV();

        if (Tween.lastRenderPoint != null) {
            player.lastTickPosX = Tween.lastRenderPoint.getX();
            player.lastTickPosY = Tween.lastRenderPoint.getY();
            player.lastTickPosZ = Tween.lastRenderPoint.getZ();
            player.prevRotationYaw = (float) Tween.lastRenderPoint.getRotH();
            player.prevRotationPitch = (float) Tween.lastRenderPoint.getRotV();

            player.prevCameraYaw = (float) Tween.lastRenderPoint.getCamH();
            player.prevCameraPitch = (float) Tween.lastRenderPoint.getCamV();

            EntityFX.interpPosX = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double)event.renderTickTime;
            EntityFX.interpPosY = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double)event.renderTickTime;
            EntityFX.interpPosZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double)event.renderTickTime;
        }

        mc.setRenderViewEntity(player);

        Tween.lastRenderPoint = position;

        rendered = true;
    }

    public void renderTag(String tag, double xp, double yp, double zp, EntityPlayer player, float ptt) {

        float x = (float) (xp - (player.prevPosX + (player.posX - player.prevPosX) * ptt));
        float y = (float) (yp - (player.prevPosY + (player.posY - player.prevPosY) * ptt));
        float z = (float) (zp - (player.prevPosZ + (player.posZ - player.prevPosZ) * ptt));

        float f = 1.6F;
        float f1 = 0.016666668F * f;
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)x + 0.0F, (float)y + 0.5F, (float)z);
        GL11.glNormal3f(0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-this.rm.playerViewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(this.rm.playerViewX, 1.0F, 0.0F, 0.0F);
        GlStateManager.scale(-f1, -f1, f1);
        GlStateManager.disableLighting();
        GlStateManager.depthMask(false);
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        byte b0 = 0;

        GlStateManager.disableTexture2D();
        worldrenderer.startDrawingQuads();
        int j = fr.getStringWidth(tag) / 2;
        worldrenderer.setColorRGBA_F(1F, 0.0F, 0.7F, 0.25F);
        worldrenderer.addVertex((double)(-j - 1), (double) (-1 + b0), 0.0D);
        worldrenderer.addVertex((double)(-j - 1), (double) (8 + b0), 0.0D);
        worldrenderer.addVertex((double)(j + 1), (double)(8 + b0), 0.0D);
        worldrenderer.addVertex((double)(j + 1), (double)(-1 + b0), 0.0D);
        tessellator.draw();
        GlStateManager.enableTexture2D();
        fr.drawString(tag, -fr.getStringWidth(tag) / 2, b0, 553648127);
        GlStateManager.enableDepth();
        GlStateManager.depthMask(true);
        fr.drawString(tag, -fr.getStringWidth(tag) / 2, b0, -1);
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.popMatrix();
    }

}
