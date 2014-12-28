package sourcecoded.creativeA.client.camera;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import sourcecoded.creativeA.CreativeAdditionsBase;
import sourcecoded.creativeA.network.MsgPathRequest;
import sourcecoded.creativeA.network.MsgPathToServer;
import sourcecoded.creativeA.network.NetHandler;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CameraCommand extends CommandBase {

    public static String fileExtension = ".path";

    public static File ca = new File(CreativeAdditionsBase.getForgeRoot(), "creativeadditions");

    @Override
    public String getName() {
        return "cam";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/cam <start:stop:add:remove:clear:save:load:execute:net> [pointID | seconds | name] [command]";
    }

    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public void execute(ICommandSender sender, String[] args) throws CommandException {
        if (args.length == 0) throw new WrongUsageException(getCommandUsage(sender));

        if (args[0].equalsIgnoreCase("start")) {
            int tweenTime = Integer.parseInt(args[1]);
            tweenTime = Math.abs(tweenTime);
            if (tweenTime == 0) throw new WrongUsageException("Your tween time can't be 0 >:C");
            Tween.beginTween(tweenTime);
        } else if (args[0].equalsIgnoreCase("stop")) {
            Tween.stop();
        } else if (args[0].equalsIgnoreCase("add")) {
            TweenPosition position = TweenPosition.create();
            Tween.add(position);
        } else if (args[0].equalsIgnoreCase("remove")) {
            int tweenID = Integer.parseInt(args[1]);
            Tween.remove(tweenID);
        } else if (args[0].equalsIgnoreCase("clear")) {
            Tween.getWaypoints().clear();
        } else if (args[0].equalsIgnoreCase("save")) {
            save(sender, args[1]);
        } else if (args[0].equalsIgnoreCase("load")) {
            load(sender, args[1]);
        } else if (args[0].equalsIgnoreCase("execute")) {
            int pointID = Integer.parseInt(args[1]);
            ArrayList<String> argsList = new ArrayList<String>(Arrays.asList(args));
            argsList.remove(0);
            argsList.remove(0);

            String command = "";
            for (String s : argsList)
                command += s + " ";

            Tween.getWaypoints().get(pointID).executables.add(command);
            sender.addChatMessage(new ChatComponentText("Successfully added command to camera location: " + pointID));
        } else if (args[0].equalsIgnoreCase("net")) {
            if (args[1].equalsIgnoreCase("save")) {
                NetHandler.wrapper.sendToServer(new MsgPathToServer(savePath(), args[2]));
            } else if (args[1].equalsIgnoreCase("load")) {
                NetHandler.wrapper.sendToServer(new MsgPathRequest(args[2]));
            }
        }
    }

    public static String savePath() {
        JsonObject master = new JsonObject();
        JsonArray array = new JsonArray();
        ArrayList<TweenPosition> way = Tween.getWaypoints();
        for (int i = 0; i < way.size(); i++) {
            array.add(way.get(i).toJSON(i));
        }
        master.add("waypoints", array);

        return master.toString();
    }

    void save(ICommandSender sender, String name) {
        String path = savePath();
        ca.mkdir();

        File save = new File(ca, name + fileExtension);
        try {
            FileWriter writer = new FileWriter(save);
            writer.write(path);
            writer.close();

            sender.addChatMessage(new ChatComponentText("Saved Camera Path to JSON: " + name + fileExtension));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static ArrayList<TweenPosition> loadPath(String json) {
        JsonElement element = new JsonParser().parse(json);
        JsonObject obj = element.getAsJsonObject();
        JsonArray array = obj.get("waypoints").getAsJsonArray();

        ArrayList<TweenPosition> positions = new ArrayList<TweenPosition>();
        for (int i = 0; i < array.size(); i++) {
            JsonObject object = array.get(i).getAsJsonObject();
            int id = object.get("id").getAsInt();
            positions.add(i, TweenPosition.fromJSON(object));
        }

        return positions;
    }

    void load(ICommandSender sender, String name) {
        File save = new File(ca, name + fileExtension);

        try {
            BufferedReader reader = new BufferedReader(new FileReader(save));
            String nextLine;
            String jsonString = "";
            while ((nextLine = reader.readLine()) != null) {
                jsonString += nextLine;
            }

            Tween.setWaypoints(loadPath(jsonString));

            reader.close();

            sender.addChatMessage(new ChatComponentText("Loaded Camera Path from JSON: " + name + fileExtension));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        if (args.length == 1)
            return getListOfStringsMatchingLastWord(args, "add", "start", "stop", "execute", "remove", "clear", "save", "load", "net");
        if (args.length == 2 && args[0].equalsIgnoreCase("net"))
            return getListOfStringsMatchingLastWord(args, "save", "load");
        else return null;
    }
}