package sourcecoded.creativeA.client.camera;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

import java.util.ArrayList;
import java.util.Iterator;

public class Tween {

    private static ArrayList<TweenPosition> positions = new ArrayList<TweenPosition>();
    private static ArrayList<TweenPosition> removablePositions;
    private static EntityPlayerSP fakeViewEntity;
    private static boolean tween = false;
    private static long startTime = 0L;
    private static int tweenTime = 0;
    private static int tweenTimePer;
    private static long endTime;
    private static TweenPosition lastLandmark;
    private static long totalElapse;
    public static TweenPosition lastRenderPoint;

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

        removablePositions = new ArrayList<TweenPosition>();
        removablePositions.addAll(positions);
        getClientPlayer().addChatComponentMessage(new ChatComponentText("Camera Tween Begun"));
    }

    public static TweenPosition getCurrentPosition() {
        long currentTime = System.currentTimeMillis();
        long allElapsed = currentTime - startTime;
        long elapsedTime = (currentTime - startTime) - totalElapse;

        Iterator<TweenPosition> it = removablePositions.iterator();
        while (it.hasNext()) {
            TweenPosition pos = it.next();
            if (allElapsed >= (pos.timeOffset + tweenTimePer)) {
                it.remove();
                totalElapse += elapsedTime;
                elapsedTime = (currentTime - startTime) - totalElapse;
            }
        }

        if (removablePositions.size() <= 1 || currentTime > endTime) {
            stop();
            return null;
        }

        lastLandmark = removablePositions.get(0);

        if (!lastLandmark.triggered) {
            lastLandmark.onPointActivated();
            lastLandmark.triggered = true;
        }

        TweenPosition nextPosition = removablePositions.get(1);
        long startOfPath = lastLandmark.timeOffset;
        long endOfPath = nextPosition.timeOffset;
        float currentProgress = (float)elapsedTime / (float)(endOfPath - startOfPath);
        return TweenPosition.interpolate(lastLandmark, nextPosition, currentProgress);
    }

    public static void stop() {
        totalElapse = 0;
        tween = false;
        removablePositions.clear();
        getClientPlayer().addChatComponentMessage(new ChatComponentText("Camera Tween Ended"));
    }

}
