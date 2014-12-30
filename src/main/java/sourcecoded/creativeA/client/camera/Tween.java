package sourcecoded.creativeA.client.camera;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

import java.util.ArrayList;
import java.util.Iterator;

public class Tween {

    private static ArrayList<TweenPosition> positions = new ArrayList<TweenPosition>();
    private static EntityPlayerSP fakeViewEntity;
    private static boolean tween = false;
    private static long startTime = 0L;
    private static int tweenTime = 0;
    private static int tweenTimePer;
    private static long endTime;
    private static long totalElapse;
    public static TweenPosition lastRenderPoint;
    public static boolean smooth = false;

    public static void add(TweenPosition pos) {
        positions.add(pos);
    }

    public static void remove(int id) {
        positions.remove(id);
    }

    public static ArrayList<TweenPosition> getWaypoints() {
        return positions;
    }

    public static void setWaypoints(ArrayList<TweenPosition> positions) {
        Tween.positions = positions;
    }

    public static void initFake() {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.theWorld != null)
            //fakeViewEntity = new EntityClientPlayerMP(mc, mc.theWorld, new Session(mc.thePlayer.getName(), mc.thePlayer.getGameProfile().getId().toString().replace("-", ""), "CreativeAdditions", "mojang"), mc.thePlayer.sendQueue, null);
            fakeViewEntity = new EntityPlayerSP(mc, mc.theWorld, mc.thePlayer.sendQueue, null);
        else
            fakeViewEntity = null;
    }

    public static boolean running() {
        return tween;
    }

    public static EntityPlayerSP getView() {
        if (fakeViewEntity == null) initFake();
        return fakeViewEntity;
    }

    public static EntityPlayer getClientPlayer() {
        return Minecraft.getMinecraft().thePlayer;
    }

    public static boolean valid() {
        return getView() != null;
    }

    public static void beginTween(int seconds) {
        startTime = System.currentTimeMillis();
        tween = true;
        tweenTime = seconds * 1000;
        endTime = startTime + tweenTime;

        int timePerPosition = tweenTime / (positions.size() - 1);
        tweenTimePer = timePerPosition;
        for (int i = 0; i < positions.size(); i++) {
            TweenPosition pos = positions.get(i);
            pos.timeOffset = i * timePerPosition;
            pos.triggered = false;
        }

        getClientPlayer().addChatComponentMessage(new ChatComponentText("Camera Tween Begun"));
    }

    public static TweenPosition getCurrentPosition() {
        long currentTime = System.currentTimeMillis();
        long allElapsed = currentTime - startTime;
        int tweenPos = (int) (allElapsed / (double) tweenTimePer);
        long elapsedInPeriod = (currentTime - startTime) - (tweenTimePer * tweenPos);
        
        if (tweenPos > positions.size() - 2)
        {
            stop();
            return null;
        }

        TweenPosition basePos = positions.get(tweenPos);

        if (!basePos.triggered) {
            basePos.onPointActivated();
            basePos.triggered = true;
        }

        TweenPosition targetPos = positions.get(tweenPos + 1);
        long startOfPath = basePos.timeOffset;
        long endOfPath = targetPos.timeOffset;
        float currentProgress = (float)elapsedInPeriod / (float)(endOfPath - startOfPath);
        
        if (smooth)
        {
        	TweenPosition preBasePos;
        	TweenPosition afterTargetPos;
        	
        	if (tweenPos < 1 )
        	{
        		preBasePos = basePos;
        	} else {
        		preBasePos = positions.get(tweenPos - 1);
        	}
        	
        	
        	if (tweenPos > positions.size() - 3)
        	{
        		afterTargetPos = targetPos;
        	} else {
        		afterTargetPos = positions.get(tweenPos + 2);
        	}
        	
        	return TweenPosition.cubicInterpolate(preBasePos, basePos, targetPos, afterTargetPos, currentProgress);
        } else {
        	return TweenPosition.interpolate(basePos, targetPos, currentProgress);
        }
    }

    public static void stop() {
        totalElapse = 0;
        tween = false;
        getClientPlayer().addChatComponentMessage(new ChatComponentText("Camera Tween Ended"));
    }

}
