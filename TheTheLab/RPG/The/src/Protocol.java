import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;

public class Protocol {
    public static String makeUpdateProtocol(List<Session> sessions) {
        JsonArray jsonArray = new JsonArray();
        Gson gson = new GsonBuilder().create();
        JsonObject jsonObject = new JsonObject();

        for (Session s : sessions) {
            Session.UserInfo u = s.getUserInfo();
            jsonArray.add(gson.toJson(u));
        }

        jsonObject.addProperty("type", "Update");
        jsonObject.add("users", jsonArray);
        return jsonObject.toString().replace("\\", "").replace("[\"", "[").replace("\"]", "]");
    }

    public static String makeHitProtocol(String user, String victim, int damage) {
        Gson gson = new GsonBuilder().create();
        Hit hit = new Hit();
        hit.from = user;
        hit.to = victim;
        hit.damage = damage;
        return gson.toJson(hit, Hit.class);
    }

    public static String makeKillProtocol(String user, String victim) {
        Gson gson = new GsonBuilder().create();
        Kill kill = new Kill();
        kill.from = user;
        kill.to = victim;
        return gson.toJson(kill, Kill.class);
    }

    public static String makeItemCreateProtocol(int index) {
        Gson gson = new GsonBuilder().create();
        ItemCreate itemCreate = new ItemCreate();
        itemCreate.index = index;
        return gson.toJson(itemCreate, ItemCreate.class);
    }

    public static String makeMapProtocol(int[] map) {
        Gson gson = new GsonBuilder().create();
        Map m = new Map();
        m.map = map;
        return gson.toJson(m, Map.class);
    }

    public static String makeItemConsumeProtocol(String user, int index) {
        Gson gson = new GsonBuilder().create();
        ItemConsume itemConsume = new ItemConsume();
        itemConsume.user = user;
        itemConsume.index = index;
        return gson.toJson(itemConsume, ItemConsume.class);
    }

    static class Join {
        String user;
    }

    static class Move {
        String direction;

    }

    static class Hit {
        String from;
        String to;
        int damage;
    }

    static class Kill {
        String from;
        String to;
    }

    static class ItemCreate {
        int index;
    }

    static class ItemConsume {
        String user;
        int index;
    }

    static class Map {
        int[] map;
    }
}
