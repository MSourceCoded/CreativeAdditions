package sourcecoded.creativeA.client.camera;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.client.FMLClientHandler;

import java.util.ArrayList;

public class TweenPosition {

    double x;
    double y;
    double z;

    double camH;
    double camV;

    double rotH;
    double rotV;

    public long timeOffset;

    public ArrayList<String> executables = new ArrayList<String>();

    public boolean triggered = false;

    public TweenPosition(double x, double y, double z, double camH, double camV, double rotH, double rotV) {
        this.x = x;
        this.y = y;
        this.z = z;

        this.camH = camH;
        this.camV = camV;

        this.rotH = rotH;
        this.rotV = rotV;
    }

    public static TweenPosition create() {
        EntityPlayer player = FMLClientHandler.instance().getClientPlayerEntity();
        return new TweenPosition(player.posX, player.posY, player.posZ, player.cameraYaw, player.cameraPitch, player.rotationYaw, player.rotationPitch);
    }

    public static TweenPosition interpolate(TweenPosition base, TweenPosition target, float progress) {
        double x = base.getX() + ((target.getX() - base.getX()) * progress);
        double y = base.getY() + ((target.getY() - base.getY()) * progress);
        double z = base.getZ() + ((target.getZ() - base.getZ()) * progress);

        double camH = base.getCamH() + ((target.getCamH() - base.getCamH()) * progress);
        double camV = base.getCamV() + ((target.getCamV() - base.getCamV()) * progress);

        double rotH = base.getRotH() + ((target.getRotH() - base.getRotH()) * progress);
        double rotV = base.getRotV() + ((target.getRotV() - base.getRotV()) * progress);

        return new TweenPosition(x, y, z, camH, camV, rotH, rotV);
    }
    
    public static TweenPosition cubicInterpolate(TweenPosition before, TweenPosition base, TweenPosition target, TweenPosition after, float progress) {
        
        double x = cubic1DInterpolate(before.getX(), base.getX(), target.getX(), after.getX(), progress);
        double y = cubic1DInterpolate(before.getY(), base.getY(), target.getY(), after.getY(), progress);
        double z = cubic1DInterpolate(before.getZ(), base.getZ(), target.getZ(), after.getZ(), progress);

        double camH = cubic1DInterpolate(before.getCamH(), base.getCamH(), target.getCamH(), after.getCamH(), progress);
        double camV = cubic1DInterpolate(before.getCamV(), base.getCamV(), target.getCamV(), after.getCamV(), progress);

        double rotH = cubic1DInterpolate(before.getRotH(), base.getRotH(), target.getRotH(), after.getRotH(), progress);
        double rotV = cubic1DInterpolate(before.getRotV(), base.getRotV(), target.getRotV(), after.getRotV(), progress);

        return new TweenPosition(x, y, z, camH, camV, rotH, rotV);
    }
    
    private static double cubic1DInterpolate(double x0, double x1, double x2, double x3, double progress)
    {
    	double progress2 = progress*progress;
    	double a0 = x3 - x2 - x0 + x1;
    	double a1 = x0 - x1 - a0;
    	double a2 = x2 - x0;
    	double a3 = x1;

	   return( a0*progress*progress2 + a1*progress2 + a2*progress + a3);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public double getCamH() {
        return camH;
    }

    public double getCamV() {
        return camV;
    }

    public double getRotV() {
        return rotV;
    }

    public double getRotH() {
        return rotH;
    }

    public void onPointActivated() {
        EntityPlayer player = Tween.getClientPlayer();
        Minecraft mc = Minecraft.getMinecraft();

        for (String msg : executables) {
            mc.ingameGUI.getChatGUI().addToSentMessages(msg);
            mc.thePlayer.sendChatMessage(msg);
        }
    }

    public JsonObject toJSON(int id) {
        JsonObject object = new JsonObject();
        object.add("id", new JsonPrimitive(id));
        object.add("x", new JsonPrimitive(x));
        object.add("y", new JsonPrimitive(y));
        object.add("z", new JsonPrimitive(z));
        object.add("camH", new JsonPrimitive(camH));
        object.add("camV", new JsonPrimitive(camV));
        object.add("rotH", new JsonPrimitive(rotH));
        object.add("rotV", new JsonPrimitive(rotV));

        JsonArray exec = new JsonArray();
        for (String s : executables)
            exec.add(new JsonPrimitive(s));

        object.add("executables", exec);
        return object;
    }

    public static TweenPosition fromJSON(JsonObject o) {
        TweenPosition position = new TweenPosition(g(o, "x"), g(o, "y"), g(o, "z"), g(o, "camH"), g(o, "camV"), g(o, "rotH"), g(o, "rotV"));

        JsonArray exec = o.get("executables").getAsJsonArray();
        for (int i = 0; i < exec.size(); i++)
            position.executables.add(exec.get(i).getAsString());

        return position;
    }

    static double g(JsonObject object, String member) {
        return object.get(member).getAsDouble();
    }
}
