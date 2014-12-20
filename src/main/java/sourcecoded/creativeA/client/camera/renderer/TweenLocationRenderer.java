package sourcecoded.creativeA.client.camera.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
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
    EntityPlayer player;
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
        mc.setRenderViewEntity(player);
        rendered = false;
    }

    public void renderFake(TweenPosition position, TickEvent.RenderTickEvent event) {
        Minecraft mc = Minecraft.getMinecraft();

        this.player = mc.thePlayer;

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
        }

        mc.setRenderViewEntity(player);

        Tween.lastRenderPoint = position;

        rendered = true;
    }

    public void renderTag(String tag, double xp, double yp, double zp, EntityPlayer player, float ptt) {
        float f = 1.6F;
        float f1 = 0.016666668F * f;

        float x = (float) (xp - (player.prevPosX + (player.posX - player.prevPosX) * ptt));
        float y = (float) (yp - (player.prevPosY + (player.posY - player.prevPosY) * ptt));
        float z = (float) (zp - (player.prevPosZ + (player.posZ - player.prevPosZ) * ptt));

        GL11.glPushMatrix();
        GL11.glTranslatef((float)(x + 0.5F), (float)(y + 1.5F), (float)(z + 0.5F));
        GL11.glNormal3f(0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-this.rm.playerViewY, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(this.rm.playerViewX, 1.0F, 0.0F, 0.0F);
        GL11.glScalef(-f1, -f1, f1);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glTranslatef(0.0F, 0.25F / f1, 0.0F);
        GL11.glDepthMask(false);
        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        Tessellator tessellator = Tessellator.getInstance();
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        int ij = fr.getStringWidth(tag) / 2;
        WorldRenderer render = tessellator.getWorldRenderer();
        render.startDrawingQuads();
        render.setColorRGBA_F(1F, 0F, 1F, 0.25F);
        render.addVertex((double) (-ij - 1), -1.0D, 0.0D);
        render.addVertex((double)(-ij - 1), 8.0D, 0.0D);
        render.addVertex((double)(ij + 1), 8.0D, 0.0D);
        render.addVertex((double) (ij + 1), -1.0D, 0.0D);
        tessellator.draw();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDepthMask(true);
        fr.drawString(tag, -fr.getStringWidth(tag) / 2, 0, 16777215);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glPopMatrix();
    }

}
